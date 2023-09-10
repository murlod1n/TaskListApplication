package com.todoapp.ui.screens.addtaskscreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.todoapp.domain.usecases.projects.GetAllProjectsUseCase
import com.todoapp.domain.usecases.projects.InsertProjectUseCase
import com.todoapp.domain.usecases.tasks.GetAllTasksUseCase
import com.todoapp.domain.usecases.tasks.InsertTaskUseCase
import com.todoapp.ui.mappers.toProject
import com.todoapp.ui.mappers.toProjectUI
import com.todoapp.ui.mappers.toTask
import com.todoapp.ui.mappers.toTaskUI
import com.todoapp.ui.models.ProjectUI
import com.todoapp.ui.models.TaskUI
import com.todoapp.ui.navigation.AppNavigator
import com.todoapp.ui.navigation.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    private val savedStateHandle: SavedStateHandle,
    private val insertTaskUseCase: InsertTaskUseCase,
    private val insertProjectUseCase: InsertProjectUseCase,
    private val getAllProjectsUseCase: GetAllProjectsUseCase,
    private val getAllTasksUseCase: GetAllTasksUseCase
) : ViewModel() {


    private val _stateFlow: MutableStateFlow<AddTaskState> = MutableStateFlow(AddTaskState())
    val stateFlow: StateFlow<AddTaskState> = _stateFlow.asStateFlow()

    private val _projectsListState: MutableStateFlow<List<ProjectUI>> =
        MutableStateFlow(emptyList())
    val projectsListState: StateFlow<List<ProjectUI>> = _projectsListState.asStateFlow()

    private val _showDialogStateFlow: MutableStateFlow<AddTaskShowDialogState> =
        MutableStateFlow(AddTaskShowDialogState())
    val showDialogStateFlow: StateFlow<AddTaskShowDialogState> = _showDialogStateFlow.asStateFlow()


    init {
        viewModelScope.launch {
            savedStateHandle.get<String>(Destination.TASK_ID_KEY)?.let { id ->
                getAllTasksUseCase().collect {
                    it.map { task ->
                        if (task.taskId == id.toLong()) task.toTaskUI().apply {
                            _stateFlow.value = AddTaskState(
                                taskId = taskId,
                                taskProjectId = taskProjectId,
                                taskColor = taskColor,
                                dateValue = taskDate,
                                timeValue = taskTime,
                                titleValue = taskTitle,
                                descriptionValue = taskDescription
                            )
                        }
                    }
                }
            }
            getAllProjectsUseCase().collect {
                _projectsListState.value = it.map { project -> project.toProjectUI() }
            }
            if (_projectsListState.value.isNotEmpty()) if (_projectsListState.value[0].projectId == _stateFlow.value.taskProjectId) _stateFlow.value =
                _stateFlow.value.copy(
                    taskProjectId = _projectsListState.value[0].projectId,
                    taskColor = _projectsListState.value[0].projectColor
                )
        }
    }

    fun changeDateValue(date: Long) {
        _stateFlow.value = _stateFlow.value.copy(dateValue = date)
    }

    fun changeTimeValue(time: Long) {
        _stateFlow.value = _stateFlow.value.copy(timeValue = time)
    }

    fun changeTitleValue(title: String) {
        _stateFlow.value = _stateFlow.value.copy(titleValue = title)
    }

    fun changeProject(projectId: Long, projectColor: Long) {
        _stateFlow.value =
            _stateFlow.value.copy(taskProjectId = projectId, taskColor = projectColor)
    }

    fun changeDescriptionValue(desc: String) {
        _stateFlow.value = _stateFlow.value.copy(descriptionValue = desc)
    }

    fun changeShowDatePickerDialog() {
        _showDialogStateFlow.value =
            _showDialogStateFlow.value.copy(showDatePickerDialog = !_showDialogStateFlow.value.showDatePickerDialog)
    }

    fun changeShowTimePickerDialog() {
        _showDialogStateFlow.value =
            _showDialogStateFlow.value.copy(showTimePickerDialog = !_showDialogStateFlow.value.showTimePickerDialog)
    }

    fun insertProject(projectUI: ProjectUI) {
        viewModelScope.launch {
            insertProjectUseCase(projectUI.toProject())
            getAllProjectsUseCase().collect {
                _projectsListState.value = it.map { project -> project.toProjectUI() }
            }
        }
    }

    fun navigateBack() {
        appNavigator.tryNavigateBack()
    }

    fun saveTask() {
        viewModelScope.launch {
            insertTaskUseCase(
                TaskUI(
                    taskId = _stateFlow.value.taskId,
                    taskProjectId = _stateFlow.value.taskProjectId,
                    taskTitle = _stateFlow.value.titleValue,
                    taskDescription = _stateFlow.value.descriptionValue,
                    taskColor = _stateFlow.value.taskColor,
                    isChecked = false,
                    taskDate = _stateFlow.value.dateValue,
                    taskTime = _stateFlow.value.timeValue,
                    isNotification = false
                ).toTask()
            )
        }
        _stateFlow.value = AddTaskState()
        appNavigator.tryNavigateBack()
    }
}
package com.todoapp.ui.screens.tasklistscreen.tasklistwithproject

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.todoapp.domain.usecases.projects.GetProjectByIdUseCase
import com.todoapp.domain.usecases.projects.InsertProjectUseCase
import com.todoapp.domain.usecases.tasks.DeleteTaskUseCase
import com.todoapp.domain.usecases.tasks.GetTasksWithProjectUseCase
import com.todoapp.domain.usecases.tasks.UpdateTaskUseCase
import com.todoapp.ui.mappers.toProject
import com.todoapp.ui.mappers.toProjectUI
import com.todoapp.ui.mappers.toTask
import com.todoapp.ui.mappers.toTaskUI
import com.todoapp.ui.models.ProjectUI
import com.todoapp.ui.models.TaskUI
import com.todoapp.ui.navigation.AppNavigator
import com.todoapp.ui.navigation.Destination
import com.todoapp.ui.utils.longDateToString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import javax.inject.Inject

@HiltViewModel
class TaskListWithProjectViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getTasksWithProjectUseCase: GetTasksWithProjectUseCase,
    private val getProjectByIdUseCase: GetProjectByIdUseCase,
    private val insertProjectUseCase: InsertProjectUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val appNavigator: AppNavigator
) : ViewModel() {

    private val _stateFlow: MutableStateFlow<TaskListWithProjectState> = MutableStateFlow(
        TaskListWithProjectState(project = ProjectUI(0, "", 0))
    )

    val stateFlow: StateFlow<TaskListWithProjectState> = _stateFlow.asStateFlow()


    fun insertProject(projectUI: ProjectUI) {
        viewModelScope.launch {
            insertProjectUseCase(projectUI.toProject())
            savedStateHandle.get<String>(Destination.PROJECT_ID_KEY)?.let { id ->
                getTasksWithProjectUseCase(id.toLong())
                    .combine(getProjectByIdUseCase(id.toLong())) { tasksList, project ->
                        _stateFlow.value = TaskListWithProjectState(
                            tasksList = sortTasksByDate(tasksList.map { it.toTaskUI() }),
                            project = project.toProjectUI()
                        )
                    }.collect()
            }
        }
    }

    fun updateTask(task: TaskUI) {
        viewModelScope.launch {
            updateTaskUseCase(task.toTask())
            savedStateHandle.get<String>(Destination.PROJECT_ID_KEY)?.let { id ->
                getTasksWithProjectUseCase(id.toLong())
                    .combine(getProjectByIdUseCase(id.toLong())) { tasksList, project ->
                        _stateFlow.value = TaskListWithProjectState(
                            tasksList = sortTasksByDate(tasksList.map { it.toTaskUI() }),
                            project = project.toProjectUI()
                        )
                    }.collect()
            }
        }
    }

    fun deleteTask(task: TaskUI) {
        viewModelScope.launch {
            deleteTaskUseCase(task.toTask())
            savedStateHandle.get<String>(Destination.PROJECT_ID_KEY)?.let { id ->
                getTasksWithProjectUseCase(id.toLong())
                    .combine(getProjectByIdUseCase(id.toLong())) { tasksList, project ->
                        _stateFlow.value = TaskListWithProjectState(
                            tasksList = sortTasksByDate(tasksList.map { it.toTaskUI() }),
                            project = project.toProjectUI()
                        )
                    }.collect()
            }
        }
    }


    fun getDateFromDataBase() = viewModelScope.launch {
        viewModelScope.launch {
            savedStateHandle.get<String>(Destination.PROJECT_ID_KEY)?.let { id ->
                getTasksWithProjectUseCase(id.toLong())
                    .combine(getProjectByIdUseCase(id.toLong())) { tasksList, project ->
                        _stateFlow.value = TaskListWithProjectState(
                            tasksList = sortTasksByDate(tasksList.map { it.toTaskUI() }),
                            project = project.toProjectUI()
                        )
                    }.collect()
            }

        }
    }

    private fun sortTasksByDate(tasksList: List<TaskUI>): List<TaskUI> {
        return tasksList.sortedBy {
            SimpleDateFormat("hh:mm a dd/mm/yyyy").parse(longDateToString(it))
        }
    }

    fun navigateBack() = appNavigator.tryNavigateBack()

    fun navigateToTaskScreen(id: Long) {
        appNavigator.tryNavigateTo(
            Destination.TaskScreen(id),
            isSingleTop = true
        )
    }

}
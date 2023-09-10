package com.todoapp.ui.screens.tasklistscreen.tasklistwithcalendar


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.todoapp.domain.usecases.tasks.DeleteTaskUseCase
import com.todoapp.domain.usecases.tasks.GetAllTasksUseCase
import com.todoapp.domain.usecases.tasks.UpdateTaskUseCase
import com.todoapp.ui.mappers.toTask
import com.todoapp.ui.mappers.toTaskUI
import com.todoapp.ui.models.TaskUI
import com.todoapp.ui.navigation.AppNavigator
import com.todoapp.ui.navigation.Destination
import com.todoapp.ui.utils.longDateToString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import javax.inject.Inject


@HiltViewModel
class TaskListWithCalendarViewModel @Inject constructor(
    private val getAllTasksUseCase: GetAllTasksUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val appNavigator: AppNavigator
):  ViewModel()  {

    private val _stateFlow: MutableStateFlow<TaskListWithCalendarState> = MutableStateFlow(
        TaskListWithCalendarState()
    )

    val stateFlow: StateFlow<TaskListWithCalendarState> = _stateFlow.asStateFlow()

    init {
        getAllTasks()
    }

    fun updateTask(task: TaskUI) {
        viewModelScope.launch {
            updateTaskUseCase(task.toTask())
            getAllTasksUseCase.invoke().collect {
                _stateFlow.value = TaskListWithCalendarState(sortTasksByDate(it.map { task -> task.toTaskUI() }))
            }
        }
    }

    fun deleteTask(task: TaskUI) {
        viewModelScope.launch {
            deleteTaskUseCase(task.toTask())
            getAllTasksUseCase.invoke().collect {
                _stateFlow.value = TaskListWithCalendarState(sortTasksByDate(it.map { task -> task.toTaskUI() }))
            }
        }
    }

    fun getAllTasks() {
        viewModelScope.launch {
            getAllTasksUseCase.invoke().collect {
                _stateFlow.value = TaskListWithCalendarState(sortTasksByDate(it.map { task -> task.toTaskUI() }))
            }
        }
    }

    fun navigateBack() {
        appNavigator.tryNavigateBack()
    }

    fun navigateToTaskScreen(id: Long) {
        appNavigator.tryNavigateTo(
            Destination.TaskScreen(id),
            isSingleTop = true
        )
    }

    private fun sortTasksByDate(tasksList: List<TaskUI>) : List<TaskUI> {
        return tasksList.sortedBy {
            SimpleDateFormat("hh:mm a dd/mm/yyyy").parse(longDateToString(it))
        }
    }

}
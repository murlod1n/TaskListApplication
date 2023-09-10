package com.todoapp.ui.screens.tasklistscreen.tasklistwithcalendar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.todoapp.ui.models.TaskUI


class TaskListWithCalendarCoordinator(
    private val viewModel: TaskListWithCalendarViewModel
) {

    val stateFlow = viewModel.stateFlow

    fun navigateBack() = viewModel.navigateBack()

    fun getAllTasks() = viewModel.getAllTasks()

    fun updateTask(taskUI: TaskUI) = viewModel.updateTask(taskUI)
    fun deleteTask(taskUI: TaskUI) = viewModel.deleteTask(taskUI)

    fun navigateToTaskScreen(taskId: Long) = viewModel.navigateToTaskScreen(taskId)

}

@Composable
fun rememberTaskListWithCalendarCoordinator(
    viewModel: TaskListWithCalendarViewModel = hiltViewModel()
): TaskListWithCalendarCoordinator =
    remember(viewModel) { TaskListWithCalendarCoordinator(viewModel = viewModel) }

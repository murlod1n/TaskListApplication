package com.todoapp.ui.screens.tasklistscreen.tasklistwithproject

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.todoapp.ui.models.ProjectUI
import com.todoapp.ui.models.TaskUI

class TaskListWithProjectCoordinator(
    private val viewModel: TaskListWithProjectViewModel
) {

    val stateFLow = viewModel.stateFlow

    fun getDataFromDataBase() = viewModel.getDateFromDataBase()
    fun insertProject(projectUI: ProjectUI) = viewModel.insertProject(projectUI = projectUI)
    fun updateTask(taskUI: TaskUI) = viewModel.updateTask(taskUI)
    fun deleteTask(taskUI: TaskUI) = viewModel.deleteTask(taskUI)
    fun navigateBack() = viewModel.navigateBack()
    fun navigateToTaskScreen(taskId: Long) = viewModel.navigateToTaskScreen(taskId)

}


@Composable
fun rememberTaskListWithProjectCoordinator(
    viewModel: TaskListWithProjectViewModel = hiltViewModel()
): TaskListWithProjectCoordinator =
    remember(viewModel) { TaskListWithProjectCoordinator(viewModel = viewModel) }
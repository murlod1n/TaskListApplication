package com.todoapp.ui.screens.homescreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.todoapp.ui.models.ProjectUI
import com.todoapp.ui.models.TaskUI

class HomeCoordinator(
    val viewModel: HomeViewModel,
) {

    val stateFlow = viewModel.stateFlow

    fun navigateToAddTaskScreen() = viewModel.navigateToAddTaskScreen()
    fun navigateToTaskListWithProjectScreen(projectId: Long) = viewModel.navigateToTaskListWithProjectScreen(projectId = projectId)
    fun navigateToTaskScreen(id: Long) = viewModel.navigateToTaskScreen(id)

    fun getDataFromDataBase() = viewModel.getDataFromDataBase()
    fun deleteTask(task: TaskUI) = viewModel.deleteTask(task)
    fun updateTask(task: TaskUI) = viewModel.updateTask(task)
    fun deleteProject(project: ProjectUI) = viewModel.deleteProject(project)
    fun insertProject(projectUI: ProjectUI) = viewModel.insertProject(projectUI)


}

@Composable
fun rememberHomeCoordinator(
    viewModel: HomeViewModel = hiltViewModel()
): HomeCoordinator = remember(viewModel) { HomeCoordinator(viewModel = viewModel) }

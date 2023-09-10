package com.todoapp.ui.screens.homescreen

import com.todoapp.ui.models.ProjectUI
import com.todoapp.ui.models.TaskUI

data class HomeState(
    val projectsList: List<ProjectUI> = emptyList(),
    val tasksList: List<TaskUI> = emptyList()
)

data class HomeContract(
    val navigateToTaskListWithProjectScreen: (Long) -> Unit,
    val navigateToTaskScreen: (Long) -> Unit,
    val navigateToAddTaskScreen: () -> Unit,
    val insertProject: (ProjectUI) -> Unit,
    val deleteTask: (TaskUI) -> Unit,
    val updateTask: (TaskUI) -> Unit,
    val deleteProject: (ProjectUI) -> Unit,
    val getDataFromDataBase: () -> Unit
)
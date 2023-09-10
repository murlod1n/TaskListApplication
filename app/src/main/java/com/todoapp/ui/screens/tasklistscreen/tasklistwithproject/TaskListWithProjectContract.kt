package com.todoapp.ui.screens.tasklistscreen.tasklistwithproject

import com.todoapp.ui.models.ProjectUI
import com.todoapp.ui.models.TaskUI

data class TaskListWithProjectState(
    val tasksList: List<TaskUI> = emptyList(),
    val project: ProjectUI
)

data class TaskListWithProjectActions(
    val getDataFromDataBase: () -> Unit,
    val insertProject: (ProjectUI) -> Unit,
    val updateTask: (TaskUI) -> Unit,
    val deleteTask: (TaskUI) -> Unit,
    val navigateBack: () -> Unit,
    val navigateToTaskScreen: (Long) -> Unit
)
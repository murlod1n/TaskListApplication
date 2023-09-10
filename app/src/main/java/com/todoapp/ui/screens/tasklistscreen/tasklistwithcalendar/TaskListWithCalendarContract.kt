package com.todoapp.ui.screens.tasklistscreen.tasklistwithcalendar

import com.todoapp.ui.models.TaskUI


data class TaskListWithCalendarState(
    val tasksList: List<TaskUI> = emptyList()
)

data class TaskListWithCalendarActions(
    val updateTask: (TaskUI) -> Unit,
    val deleteTask: (TaskUI) -> Unit,
    val navigateBack: () -> Unit,
    val navigateToTaskScreen: (Long) -> Unit,
    val getAllTasks: () -> Unit
)


package com.todoapp.ui.mappers

import com.todoapp.data.mappers.toTask
import com.todoapp.domain.models.Task
import com.todoapp.ui.models.TaskUI
import com.todoapp.ui.navigation.Destination

fun Task.toTaskUI() : TaskUI = TaskUI(
    taskId = this.taskId,
    taskProjectId = this.taskProjectId,
    taskTitle = this.taskTitle,
    taskDescription = this.taskDescription,
    taskColor = this.taskColor,
    isChecked = this.isChecked,
    taskDate = this.taskDate,
    taskTime = this.taskTime,
    isNotification = this.isNotification
)

fun TaskUI.toTask() : Task = Task(
    taskId = this.taskId,
    taskProjectId = this.taskProjectId,
    taskTitle = this.taskTitle,
    taskDescription = this.taskDescription,
    taskColor = this.taskColor,
    isChecked = this.isChecked,
    taskDate = this.taskDate,
    taskTime = this.taskTime,
    isNotification = this.isNotification
)
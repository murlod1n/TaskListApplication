package com.todoapp.data.mappers

import com.todoapp.data.storage.models.TaskEntity
import com.todoapp.domain.models.Task

fun TaskEntity.toTask() : Task = Task(
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


fun Task.toTaskEntity() : TaskEntity = TaskEntity(
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
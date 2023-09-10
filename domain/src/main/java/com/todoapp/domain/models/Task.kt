package com.todoapp.domain.models

data class Task(
    val taskId: Long,
    val taskProjectId: Long,
    val taskTitle: String,
    val taskDescription: String,
    val taskColor: Long,
    val isChecked: Boolean,
    val taskDate: Long,
    val taskTime: Long,
    val isNotification: Boolean
)
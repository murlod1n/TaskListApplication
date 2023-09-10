package com.todoapp.ui.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


data class TaskUI(
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
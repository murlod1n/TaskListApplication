package com.todoapp.data.storage.models

import android.app.ActivityManager.TaskDescription
import android.app.Notification
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "task_id") val taskId: Long,
    @ColumnInfo(name = "task_project_id") val taskProjectId: Long,
    @ColumnInfo(name = "task_title") val taskTitle: String,
    @ColumnInfo(name = "task_desc") val taskDescription: String,
    @ColumnInfo(name = "task_color") val taskColor: Long,
    @ColumnInfo(name = "is_checked") val isChecked: Boolean,
    @ColumnInfo(name = "task_date") val taskDate: Long,
    @ColumnInfo(name = "task_time") val taskTime: Long,
    @ColumnInfo(name = "is_notification") val isNotification: Boolean
)
package com.todoapp.data.storage.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "projects")
data class ProjectEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "project_id") val projectId: Long,
    @ColumnInfo(name = "project_title") val projectTitle: String,
    @ColumnInfo(name = "project_color") val projectColor: Long
)
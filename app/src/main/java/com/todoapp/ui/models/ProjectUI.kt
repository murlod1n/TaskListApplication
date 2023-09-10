package com.todoapp.ui.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


data class ProjectUI(
    val projectId: Long,
    val projectTitle: String,
    val projectColor: Long
)
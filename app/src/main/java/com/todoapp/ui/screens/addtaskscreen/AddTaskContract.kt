package com.todoapp.ui.screens.addtaskscreen

import com.todoapp.ui.models.ProjectUI



data class AddTaskState(
    val taskId: Long = 0,
    val taskProjectId: Long = 0,
    val taskColor: Long = 0xFF707070,
    val dateValue: Long = System.currentTimeMillis(),
    val timeValue: Long = System.currentTimeMillis(),
    val titleValue: String = "",
    val descriptionValue: String = "",
)

data class AddTaskShowDialogState(
    val showDatePickerDialog: Boolean = false,
    val showTimePickerDialog: Boolean = false
)


data class AddTaskActions(
    val saveTask: () -> Unit,
    val changeProjectValue: (projectColor: Long, projectId: Long) -> Unit,
    val changeDateValue: (Long) -> Unit,
    val changeTimeValue: (Long) -> Unit,
    val changeTitleValue: (String) -> Unit,
    val changeDescriptionValue: (String) -> Unit,
    val changeShowDatePickerDialog: () -> Unit,
    val changeShowTimePickerDialog: () -> Unit,
    val insertProject: (ProjectUI) -> Unit,
    val navigateBack: () -> Unit
)
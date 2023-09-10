package com.todoapp.ui.screens.addtaskscreen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import java.text.SimpleDateFormat
import java.util.Date


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskRoute(
    coordinator: AddTaskCoordinator = rememberAddTaskCoordinator()
) {

    val uiState = coordinator.stateFlow.collectAsState().value
    val showDialogStateFlow = coordinator.showDialogStateFlow.collectAsState().value
    val projectList = coordinator.projectsList.collectAsState().value
    val actions = rememberAddTaskActions(coordinator = coordinator)

    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = uiState.dateValue)
    val timePickerState = rememberTimePickerState(
        initialHour = SimpleDateFormat("hh").format(Date(uiState.timeValue)).toInt(),
        initialMinute = SimpleDateFormat("mm").format(Date(uiState.timeValue)).toInt(),
    )

    var titleValidation by rememberSaveable { mutableStateOf("") }
    var showSheet by remember { mutableStateOf(false) }



    AddTaskScreen(
        projectsList = projectList,
        descValue = uiState.descriptionValue,
        titleValue = uiState.titleValue,
        projectId = uiState.taskProjectId,
        datePickerState = datePickerState,
        timePickerState = timePickerState,
        showDatePickerDialog = showDialogStateFlow.showDatePickerDialog,
        showTimePickerDialog = showDialogStateFlow.showTimePickerDialog,
        actions = actions,
        showSheet = showSheet,
        setShowSheet = { showSheet = !showSheet },
        titleValidation = titleValidation,
        changeTitleValidation = { titleValidation = it }
    )

}


@Composable
fun rememberAddTaskActions(
    coordinator: AddTaskCoordinator
): AddTaskActions =
    remember(coordinator) {
        AddTaskActions(
            saveTask = coordinator::saveTask,
            changeDateValue = coordinator::changeDateValue,
            changeTimeValue = coordinator::changeTimeValue,
            changeTitleValue = coordinator::changeTitleValue,
            changeDescriptionValue = coordinator::changeDescriptionValue,
            changeShowDatePickerDialog = coordinator::changeShowDatePickerDialog,
            changeShowTimePickerDialog = coordinator::changeShowTimePickerDialog,
            changeProjectValue = coordinator::changeProjectValue,
            insertProject = coordinator::insertProject,
            navigateBack = coordinator::navigateBack
        )
    }

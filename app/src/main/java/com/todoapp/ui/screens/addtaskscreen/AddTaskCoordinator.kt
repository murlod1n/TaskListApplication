package com.todoapp.ui.screens.addtaskscreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.todoapp.ui.models.ProjectUI


class AddTaskCoordinator(
    val viewModel: AddTaskViewModel
) {
    val stateFlow = viewModel.stateFlow
    val showDialogStateFlow = viewModel.showDialogStateFlow
    val projectsList = viewModel.projectsListState

    fun navigateBack() = viewModel.navigateBack()

    fun saveTask() = viewModel.saveTask()

    fun insertProject(projectUI: ProjectUI) = viewModel.insertProject(projectUI)

    fun changeShowDatePickerDialog() = viewModel.changeShowDatePickerDialog()
    fun changeShowTimePickerDialog() = viewModel.changeShowTimePickerDialog()

    fun changeDateValue(date: Long) = viewModel.changeDateValue(date)
    fun changeTimeValue(time: Long) = viewModel.changeTimeValue(time)
    fun changeTitleValue(title: String) = viewModel.changeTitleValue(title)
    fun changeDescriptionValue(desc: String) = viewModel.changeDescriptionValue(desc)
    fun changeProjectValue(projectId: Long, projectColor: Long) =
        viewModel.changeProject(projectId = projectId, projectColor = projectColor)

}


@Composable
fun rememberAddTaskCoordinator(
    viewModel: AddTaskViewModel = hiltViewModel()
): AddTaskCoordinator = remember(viewModel) { AddTaskCoordinator(viewModel = viewModel) }

package com.todoapp.ui.screens.tasklistscreen.tasklistwithproject

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager

@Composable
fun TaskListWithProjectRoute(
    coordinator: TaskListWithProjectCoordinator = rememberTaskListWithProjectCoordinator()
) {

    val uiState = coordinator.stateFLow.collectAsState()
    val actions = rememberTaskListWithProjectActions(coordinator = coordinator)

    var showSheet by remember { mutableStateOf(false) }
    var showSearchField by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    var searchValue by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current


    TaskListWithProjectScreen(
        uiState = uiState.value,
        actions = actions,
        showSearchField = showSearchField,
        changeShowSearchField = { showSearchField = !showSearchField },
        focusRequester = focusRequester,
        searchValue = searchValue,
        changeSearchValue = { searchValue = it },
        focusManagerClear = { focusManager.clearFocus() },
        showSheet = showSheet,
        changeShowSheetValue = { showSheet = !showSheet },
    )

}


@Composable
fun rememberTaskListWithProjectActions(coordinator: TaskListWithProjectCoordinator): TaskListWithProjectActions =
    remember(coordinator) {
        TaskListWithProjectActions(
            getDataFromDataBase = coordinator::getDataFromDataBase,
            insertProject = coordinator::insertProject,
            updateTask = coordinator::updateTask,
            deleteTask = coordinator::deleteTask,
            navigateBack = coordinator::navigateBack,
            navigateToTaskScreen = coordinator::navigateToTaskScreen
        )
    }

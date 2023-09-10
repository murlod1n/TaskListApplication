package com.todoapp.ui.screens.homescreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember


@Composable
fun HomeRoute(
    coordinator: HomeCoordinator = rememberHomeCoordinator()
) {

    val uiState = coordinator.stateFlow.collectAsState()

    val actions = rememberHomeActions(coordinator = coordinator)

    HomeScreen(uiState = uiState.value, actions = actions)

}


@Composable
fun rememberHomeActions(coordinator: HomeCoordinator): HomeContract =
    remember(coordinator) {
        HomeContract(
            navigateToTaskListWithProjectScreen = coordinator::navigateToTaskListWithProjectScreen,
            insertProject = coordinator::insertProject,
            navigateToTaskScreen = coordinator::navigateToTaskScreen,
            getDataFromDataBase = coordinator::getDataFromDataBase,
            deleteTask = coordinator::deleteTask,
            deleteProject = coordinator::deleteProject,
            updateTask = coordinator::updateTask,
            navigateToAddTaskScreen = coordinator::navigateToAddTaskScreen
        )
    }

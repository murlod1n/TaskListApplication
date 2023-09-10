package com.todoapp.ui.screens.tasklistscreen.tasklistwithcalendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import java.time.LocalDate


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskListWithCalendarRoute(
    coordinator: TaskListWithCalendarCoordinator = rememberTaskListWithCalendarCoordinator()
) {

    val uiState = coordinator.stateFlow.collectAsState()
    val actions = rememberTaskListWithCalendarActions(coordinator = coordinator)


    var showSearchField by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    var searchValue by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current

    var showMenu by remember { mutableStateOf(false) }

    val monthsList = listOf(
        "JANUARY",
        "FEBRUARY",
        "MARCH",
        "APRIL",
        "MAY",
        "JUNE",
        "JULY",
        "AUGUST",
        "SEPTEMBER",
        "OCTOBER",
        "NOVEMBER",
        "DECEMBER"
    )
    var selectedMonth by rememberSaveable {
        mutableIntStateOf(monthsList.indexOf(LocalDate.now().month.toString()))
    }
    val lazyCalendarListState = rememberLazyListState(LocalDate.now().dayOfMonth - 1)
    var selectedDay by remember { mutableIntStateOf(LocalDate.now().dayOfMonth - 1) }


    TaskListWithCalendarScreen(
        uiState = uiState.value,
        actions = actions,
        showSearchField = showSearchField,
        changeShowSearchField = { showSearchField = !showSearchField },
        focusRequester = focusRequester,
        searchValue = searchValue,
        changeSearchValue = { searchValue = it },
        focusManagerClear = { focusManager.clearFocus() },
        showMenu = showMenu,
        changeShowMenuValue = { showMenu = !showMenu },
        monthsList = monthsList,
        selectedMonth = selectedMonth,
        changeSelectedMonth = { selectedMonth = it },
        lazyCalendarListState = lazyCalendarListState,
        selectedDay = selectedDay,
        changeSelectedDay = { selectedDay = it },
    )
}


@Composable
fun rememberTaskListWithCalendarActions(coordinator: TaskListWithCalendarCoordinator): TaskListWithCalendarActions =
    remember(coordinator) {
        TaskListWithCalendarActions(
            navigateBack = coordinator::navigateBack,
            updateTask = coordinator::updateTask,
            deleteTask = coordinator::deleteTask,
            navigateToTaskScreen = coordinator::navigateToTaskScreen,
            getAllTasks = coordinator::getAllTasks
        )
    }

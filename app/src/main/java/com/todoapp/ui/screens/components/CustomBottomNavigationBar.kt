package com.todoapp.ui.screens.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.todoapp.ui.navigation.Destination
import com.todoapp.ui.screens.mainscreen.MainViewModel


sealed class BottomBarItem(
    val icon: ImageVector, val contentDescription: String, val route: String
) {
    object Home : BottomBarItem(
        icon = Icons.Outlined.Home,
        contentDescription = "Home",
        route = Destination.HomeScreen.fullRoute
    )

    object TaskListWithCalendar : BottomBarItem(
        icon = Icons.Filled.DateRange,
        contentDescription = "Calendar",
        route = Destination.TaskListWithCalendarScreen.fullRoute
    )
}


@Composable
fun CustomBottomNavigationBar(viewModel: MainViewModel) {

    val selectedItem = viewModel.selectedBottomBarItem.collectAsState().value

    BottomAppBar(
        containerColor = Color(0, 0, 0, 15),
        actions = {
            NavigationBarItem(selected = selectedItem == BottomBarItem.Home.route, onClick = {
                if (selectedItem != BottomBarItem.Home.route) viewModel.navigateBack()
            }, icon = {
                Icon(
                    if (selectedItem == BottomBarItem.Home.route) Icons.Filled.Home else Icons.Outlined.Home,
                    contentDescription = BottomBarItem.Home.contentDescription,
                )
            })
            NavigationBarItem(selected = selectedItem == BottomBarItem.TaskListWithCalendar.route,
                onClick = {
                    viewModel.navigateToTaskListWithCalendarScreen()
                },
                icon = {
                    Icon(
                        if (selectedItem == BottomBarItem.TaskListWithCalendar.route) Icons.Filled.DateRange else Icons.Outlined.DateRange,
                        contentDescription = BottomBarItem.TaskListWithCalendar.contentDescription,
                    )
                })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.navigateToAddTaskScreen()
                },
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Icon(Icons.Filled.Add, "Add task", tint = MaterialTheme.colorScheme.primary)
            }
        }
    )
}
package com.todoapp.ui.screens.mainscreen

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.todoapp.ui.navigation.Destination
import com.todoapp.ui.navigation.NavHostWrapper
import com.todoapp.ui.navigation.NavigationIntent
import com.todoapp.ui.navigation.composable
import com.todoapp.ui.screens.addtaskscreen.AddTaskRoute
import com.todoapp.ui.screens.components.CustomBottomNavigationBar
import com.todoapp.ui.screens.homescreen.HomeRoute
import com.todoapp.ui.screens.tasklistscreen.tasklistwithcalendar.TaskListWithCalendarRoute
import com.todoapp.ui.screens.tasklistscreen.tasklistwithproject.TaskListWithProjectRoute
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(
    mainViewModel: MainViewModel = hiltViewModel(),
) {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    var bottomBarState by rememberSaveable { mutableStateOf(true) }

    when (val route = navBackStackEntry?.destination?.route) {
        Destination.HomeScreen.fullRoute -> {
            bottomBarState = true
            mainViewModel.setSelectedBottomBarItem(route)
        }
        Destination.AddTaskScreen.fullRoute -> {
            bottomBarState = false
            mainViewModel.setSelectedBottomBarItem(route)
        }
        Destination.TaskListWithCalendarScreen.fullRoute -> {
            bottomBarState = true
            mainViewModel.setSelectedBottomBarItem(route)
        }
        Destination.TaskListWithProjectScreen.fullRoute -> {
            bottomBarState = true
            mainViewModel.setSelectedBottomBarItem(route)
        }
        Destination.TaskScreen.fullRoute -> {
            bottomBarState = false
            mainViewModel.setSelectedBottomBarItem(route)
        }
    }


    NavigationEffects(
        navigationChannel = mainViewModel.navigationChannel,
        navHostController = navController
    )


    Scaffold(
        bottomBar = { if(bottomBarState) CustomBottomNavigationBar(viewModel = mainViewModel) },
        floatingActionButtonPosition = FabPosition.End
    ) { padding ->
        Surface(modifier = Modifier.fillMaxSize().padding(padding),) {
            NavHostWrapper(
                navController = navController,
                startDestination = Destination.HomeScreen
            ) {
                composable(destination = Destination.HomeScreen) { HomeRoute() }
                composable(destination = Destination.AddTaskScreen) { AddTaskRoute() }
                composable(destination = Destination.TaskScreen) { AddTaskRoute() }
                composable(destination = Destination.TaskListWithCalendarScreen) { TaskListWithCalendarRoute() }
                composable(destination = Destination.TaskListWithProjectScreen) { TaskListWithProjectRoute() }
            }
        }
    }

}

@Composable
fun NavigationEffects(
    navigationChannel: Channel<NavigationIntent>,
    navHostController: NavHostController
) {

    val activity = (LocalContext.current as? Activity)

    LaunchedEffect(activity, navHostController, navigationChannel) {
        navigationChannel.receiveAsFlow().collect { intent ->
            if (activity?.isFinishing == true) return@collect
            when (intent) {
                is NavigationIntent.NavigateBack -> {
                    if (intent.route != null) navHostController.popBackStack(intent.route, intent.inclusive)
                    else navHostController.popBackStack()
                }
                is NavigationIntent.NavigateTo -> {
                    navHostController.navigate(intent.route) {
                        launchSingleTop = intent.isSingleTop
                        intent.popUpToRoute?.let { popUpToRoute ->
                            popUpTo(popUpToRoute) { inclusive = intent.inclusive }
                        }
                    }
                }
            }
        }
    }
}
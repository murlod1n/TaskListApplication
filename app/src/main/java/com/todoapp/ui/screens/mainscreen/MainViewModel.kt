package com.todoapp.ui.screens.mainscreen


import androidx.lifecycle.ViewModel
import com.todoapp.ui.navigation.AppNavigator
import com.todoapp.ui.navigation.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appNavigator: AppNavigator
) :   ViewModel() {

    val navigationChannel = appNavigator.navigationChannel

    private val _selectedBottomBarItem: MutableStateFlow<String> = MutableStateFlow(Destination.HomeScreen.fullRoute)
    val selectedBottomBarItem: StateFlow<String> = _selectedBottomBarItem.asStateFlow()

    fun setSelectedBottomBarItem(route: String) {
        _selectedBottomBarItem.value = route
    }

    fun navigateToAddTaskScreen() {
       appNavigator.tryNavigateTo(Destination.AddTaskScreen(), isSingleTop = true)
    }

    fun navigateBack() {
        appNavigator.tryNavigateBack()
    }

    fun navigateToTaskListWithCalendarScreen() {
        appNavigator.tryNavigateTo(Destination.TaskListWithCalendarScreen(), isSingleTop = true, inclusive = true)
    }

}
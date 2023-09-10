package com.todoapp.ui.screens.homescreen


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.todoapp.domain.usecases.projects.DeleteProjectUseCase
import com.todoapp.domain.usecases.projects.GetAllProjectsUseCase
import com.todoapp.domain.usecases.projects.InsertProjectUseCase
import com.todoapp.domain.usecases.tasks.DeleteTaskUseCase
import com.todoapp.domain.usecases.tasks.GetAllTasksUseCase
import com.todoapp.domain.usecases.tasks.UpdateTaskUseCase
import com.todoapp.domain.usecases.tasks.UpdateTasksWithoutProjectUseCase
import com.todoapp.ui.mappers.toProject
import com.todoapp.ui.mappers.toProjectUI
import com.todoapp.ui.mappers.toTask
import com.todoapp.ui.mappers.toTaskUI
import com.todoapp.ui.models.ProjectUI
import com.todoapp.ui.models.TaskUI
import com.todoapp.ui.navigation.AppNavigator
import com.todoapp.ui.navigation.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    private val getAllProjectsUseCase: GetAllProjectsUseCase,
    private val getAllTasksUseCase: GetAllTasksUseCase,
    private val insertProjectUseCase: InsertProjectUseCase,
    private val deleteProjectUseCase: DeleteProjectUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val updateTasksWithoutProjectUseCase: UpdateTasksWithoutProjectUseCase
) : ViewModel() {


    private val _stateFlow: MutableStateFlow<HomeState> = MutableStateFlow(HomeState())

    val stateFlow: StateFlow<HomeState> = _stateFlow.asStateFlow()


    fun getDataFromDataBase() {
        viewModelScope.launch {
            getAllProjectsUseCase().combine(getAllTasksUseCase()) { projects, tasks ->
                _stateFlow.value = _stateFlow.value.copy(
                    tasksList = tasks.map { task -> task.toTaskUI() },
                    projectsList = projects.map { project -> project.toProjectUI() }
                )
            }.collect()
        }
    }

    fun navigateToAddTaskScreen() {
        appNavigator.tryNavigateTo(
            Destination.AddTaskScreen(),
            isSingleTop = true
        )
    }

    fun navigateToTaskScreen(id: Long) {
        appNavigator.tryNavigateTo(
            Destination.TaskScreen(id),
            isSingleTop = true
        )
    }

    fun navigateToTaskListWithProjectScreen(projectId: Long) {
        appNavigator.tryNavigateTo(
            Destination.TaskListWithProjectScreen(projectId),
            isSingleTop = true
        )
    }

    fun updateTask(task: TaskUI) {
        viewModelScope.launch {
            updateTaskUseCase(task.toTask())
            getAllTasksUseCase().collect { tasks ->
                _stateFlow.value = _stateFlow.value.copy(
                    tasksList = tasks.map { task -> task.toTaskUI() }
                )
            }
        }
    }

    fun deleteTask(task: TaskUI) {
        viewModelScope.launch {
            deleteTaskUseCase(task.toTask())
            getAllTasksUseCase().collect { tasks ->
                _stateFlow.value = _stateFlow.value.copy(
                    tasksList = tasks.map { task -> task.toTaskUI() }
                )
            }
        }
    }

    fun deleteProject(projectUI: ProjectUI) {
        viewModelScope.launch {
            deleteProjectUseCase(projectUI.toProject())
            getAllProjectsUseCase().collect { projects ->
                _stateFlow.value = _stateFlow.value.copy(
                    projectsList = projects.map { project -> project.toProjectUI() }
                )
            }
            updateTasksWithoutProjectUseCase(projectId = projectUI.projectId)
            getAllTasksUseCase().collect { tasks ->
                _stateFlow.value = _stateFlow.value.copy(
                    tasksList = tasks.map { task -> task.toTaskUI() }
                )
            }
        }
    }

    fun insertProject(projectUI: ProjectUI) {
        viewModelScope.launch {
            insertProjectUseCase(projectUI.toProject())
            getAllProjectsUseCase().collect { projects ->
                _stateFlow.value = _stateFlow.value.copy(
                    projectsList = projects.map { project -> project.toProjectUI() }
                )
            }
        }
    }
}

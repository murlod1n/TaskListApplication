package com.todoapp.ui.screens.homescreen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.todoapp.ui.models.TaskUI
import com.todoapp.ui.screens.components.CustomBottomSheet
import com.todoapp.ui.screens.components.TaskList
import com.todoapp.ui.screens.homescreen.components.ProjectsSection
import com.todoapp.ui.screens.homescreen.components.StatisticCard
import com.todoapp.ui.utils.longDateToString
import java.text.SimpleDateFormat


@Composable
fun HomeScreen(
    uiState: HomeState,
    actions: HomeContract
) {

    LaunchedEffect(null) {
        actions.getDataFromDataBase()
    }

    var showSheet by remember { mutableStateOf(false) }

    if (showSheet) {
        CustomBottomSheet(
            onDismiss = { showSheet = false },
            insertProject = actions.insertProject
        )
    }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        StatisticCard(tasksList = uiState.tasksList)
        ProjectsSection(
            projectsList = uiState.projectsList,
            tasksList = uiState.tasksList,
            navigateToTaskListWithProject = actions.navigateToTaskListWithProjectScreen,
            showBottomSheet = { showSheet = true },
            insertProject = actions.insertProject,
            deleteProject = actions.deleteProject
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 16.dp, end = 16.dp, bottom = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Today", style = MaterialTheme.typography.titleLarge)
            IconButton(
                modifier = Modifier.size(32.dp),
                onClick = actions.navigateToAddTaskScreen
            ) {
                Icon(Icons.Filled.Add, null)
            }
        }
        TaskList(
            tasksList = getSortedAndFilteredTasksByDate(uiState.tasksList),
            navigateToTaskScreen = actions.navigateToTaskScreen,
            deleteTask = actions.deleteTask,
            updateTask = actions.updateTask
        )
    }
}

private fun getSortedAndFilteredTasksByDate(tasksList: List<TaskUI>) : List<TaskUI> {
    return tasksList.sortedByDescending {
        SimpleDateFormat("hh:mm a dd/mm/yyyy").parse(longDateToString(it))
    }.filter {
        SimpleDateFormat("d").format(it.taskDate).toInt() == SimpleDateFormat("d").format(System.currentTimeMillis()).toInt()}
}
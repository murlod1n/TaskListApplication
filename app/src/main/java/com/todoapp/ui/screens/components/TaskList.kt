package com.todoapp.ui.screens.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.todoapp.ui.models.TaskUI


@Composable
fun TaskList(
    modifier: Modifier = Modifier,
    tasksList: List<TaskUI> = emptyList(),
    navigateToTaskScreen: (Long) -> Unit = {},
    deleteTask: (TaskUI) -> Unit = {},
    updateTask: (TaskUI) -> Unit = {}
) {
    Column(modifier = modifier.padding(bottom = 16.dp, top = 0.dp, start = 16.dp, end = 16.dp)) {
        if(tasksList.isEmpty()) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "Empty",
                style = MaterialTheme.typography.titleLarge
            )
        }
        tasksList.forEach { task ->
            key(task.taskId) {
                TaskCard(
                    task = task,
                    navigateToTaskScreen = navigateToTaskScreen,
                    deleteTask = deleteTask,
                    updateTask = updateTask
                )
            }
        }
    }
}
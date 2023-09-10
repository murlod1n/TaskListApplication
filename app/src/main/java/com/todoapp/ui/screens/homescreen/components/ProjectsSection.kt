package com.todoapp.ui.screens.homescreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.todoapp.ui.models.ProjectUI
import com.todoapp.ui.models.TaskUI

@Composable
fun ProjectsSection(
    modifier: Modifier = Modifier,
    projectsList: List<ProjectUI>,
    tasksList: List<TaskUI>,
    navigateToTaskListWithProject: (Long) -> Unit,
    showBottomSheet: () -> Unit,
    insertProject: (ProjectUI) -> Unit,
    deleteProject: (ProjectUI) -> Unit
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 16.dp, end = 16.dp, bottom = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Projects",
                style = MaterialTheme.typography.titleLarge
            )
            IconButton(
                modifier = Modifier.size(32.dp),
                onClick = { showBottomSheet() }
            ) {
                Icon(Icons.Filled.Add, null)
            }
        }
        LazyRow {
            items(projectsList.size) { index ->
                key(projectsList[index].projectId) {
                    ProjectCard(
                        project = projectsList[index],
                        tasksWithProject = tasksList.filter { it.taskProjectId == projectsList[index].projectId },
                        navigateToTaskListWithProject = navigateToTaskListWithProject,
                        insertProject = insertProject,
                        deleteProject = deleteProject
                    )
                }
            }
        }
    }
}
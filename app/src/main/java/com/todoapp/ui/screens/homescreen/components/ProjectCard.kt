package com.todoapp.ui.screens.homescreen.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.todoapp.ui.models.ProjectUI
import com.todoapp.ui.models.TaskUI
import com.todoapp.ui.screens.components.CustomBottomSheet
import com.todoapp.ui.screens.components.CustomCircularProgressBar

@Composable
fun ProjectCard(
    modifier: Modifier = Modifier,
    navigateToTaskListWithProject: (Long) -> Unit,
    project: ProjectUI,
    tasksWithProject: List<TaskUI>,
    insertProject: (ProjectUI) -> Unit,
    deleteProject: (ProjectUI) -> Unit
) {

    var showMenu by remember { mutableStateOf(false) }
    var showSheet by remember { mutableStateOf(false) }

    if (showSheet) {
        CustomBottomSheet(
            onDismiss = { showSheet = false },
            insertProject = { insertProject(it) },
            title = project.projectTitle,
            color = project.projectColor,
            projectId = project.projectId
        )
    }

    Card(
        modifier = modifier.padding(start = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .clickable { navigateToTaskListWithProject(project.projectId) }
                .width(IntrinsicSize.Max)
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CustomCircularProgressBar(
                    percentage = if (tasksWithProject.isNotEmpty())
                        (tasksWithProject.filter { it.isChecked }.size.toFloat() / tasksWithProject.size.toFloat()) else 0f,
                    number = 100,
                    radius = 20.dp,
                    textColor = MaterialTheme.colorScheme.inverseSurface,
                    color = Color(project.projectColor),
                    showPercents = false
                )
                Row(
                    modifier = Modifier.padding(top = 10.dp, end = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Canvas(
                        modifier = Modifier.size(10.dp),
                        onDraw = { drawCircle(color = Color(project.projectColor)) }
                    )
                    IconButton(
                        modifier = modifier.size(20.dp),
                        onClick = { showMenu = true }
                    ) {
                        Icon(Icons.Filled.MoreVert, null)
                    }
                    DropdownMenu(
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.medium)
                            .background(MaterialTheme.colorScheme.surface)
                            .border(
                                1.dp,
                                MaterialTheme.colorScheme.outline,
                                MaterialTheme.shapes.medium
                            ),
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(text = "Edit") },
                            onClick = {
                                showMenu = false
                                showSheet = true
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = "Delete") },
                            onClick = {
                                showMenu = false
                                deleteProject(project)
                            }
                        )
                    }
                }
            }
            Column {
                Text(
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    text = project.projectTitle,
                    color = MaterialTheme.colorScheme.inverseSurface
                )
                Text(
                    style = MaterialTheme.typography.labelSmall,
                    text = "${tasksWithProject.size} Tasks",
                    color = MaterialTheme.colorScheme.outline
                )
            }
            Row(
                modifier = modifier.fillMaxWidth().padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = modifier
                        .padding(end = 5.dp)
                        .clip(MaterialTheme.shapes.small)
                        .background(color = Color(0.0f, 1.0f, 0.0f, 0.086f))
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = modifier.padding(0.dp),
                        style = MaterialTheme.typography.bodySmall,
                        text = "${tasksWithProject.filter { it.isChecked }.size} Completed",
                        color = MaterialTheme.colorScheme.inverseSurface
                    )
                }
                Column(
                    modifier = modifier
                        .clip(MaterialTheme.shapes.small)
                        .background(color = Color(1.0f, 0.0f, 0.0f, 0.078f))
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        style = MaterialTheme.typography.bodySmall,
                        text = "${tasksWithProject.filter { !it.isChecked }.size} Left",
                        color = MaterialTheme.colorScheme.inverseSurface
                    )
                }
            }
        }
    }
}
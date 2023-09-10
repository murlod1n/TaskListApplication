package com.todoapp.ui.screens.components


import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.todoapp.ui.models.TaskUI
import java.text.SimpleDateFormat
import java.util.Date


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskCard(
    task: TaskUI,
    navigateToTaskScreen: (Long) -> Unit,
    deleteTask: (TaskUI) -> Unit,
    updateTask: (TaskUI) -> Unit
) {

    var visible by remember { mutableStateOf(true) }

    val dismissState = rememberDismissState(
        confirmValueChange = {
            if(it == DismissValue.DismissedToStart) {
                visible = false
            }
            true
        },

    )

    val animatedAlpha by animateFloatAsState(
        targetValue = if (visible) 1.0f else .1f,
        label = "alpha",
        finishedListener = { deleteTask(task) }
    )

    SwipeToDismiss(
        modifier = Modifier.graphicsLayer { alpha = animatedAlpha },
        state = dismissState,
        directions = setOf(DismissDirection.EndToStart),
        background = {
            Box(
                Modifier
                    .padding(vertical = 6.dp)
                    .fillMaxSize()
                    .clip(MaterialTheme.shapes.small)
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(horizontal = 16.dp)
                    .padding(start = 40.dp)
            ) {
                Icon(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    imageVector = Icons.Filled.Delete,
                    tint = MaterialTheme.colorScheme.surface,
                    contentDescription = "Delete"
                )
            }
        },
        dismissContent = {
            Card(
                modifier = Modifier.padding(vertical = 6.dp),
                colors = CardDefaults.cardColors(Color.White),
                shape = MaterialTheme.shapes.small,
                elevation = CardDefaults.cardElevation(8.dp),
                onClick = { navigateToTaskScreen(task.taskId) }
            ) {
                Row(
                    modifier = Modifier
                        .background(if (task.isChecked) MaterialTheme.colorScheme.inverseOnSurface else MaterialTheme.colorScheme.surface)
                        .padding(8.dp)
                        .fillMaxWidth()
                        .height(IntrinsicSize.Max),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Divider(
                        modifier = Modifier
                            .padding(end = 16.dp, start = 8.dp)
                            .fillMaxHeight()
                            .width(width = 4.dp)
                            .clip(MaterialTheme.shapes.extraSmall),
                        color = if (task.isChecked) Color.LightGray else Color(task.taskColor)
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            text = task.taskTitle,
                            color = if (task.isChecked) Color.LightGray else MaterialTheme.colorScheme.inverseSurface
                        )
                        Text(
                            modifier = Modifier.padding(top = 8.dp),
                            style = MaterialTheme.typography.labelSmall,
                            text = "${SimpleDateFormat("hh:mm a").format(Date(task.taskTime))} ${SimpleDateFormat("d MMMM").format(Date(task.taskDate))}",
                            color = if (task.isChecked) Color.LightGray else MaterialTheme.colorScheme.outline
                        )
                    }
                    Checkbox(
                        modifier = Modifier,
                        checked = task.isChecked,
                        onCheckedChange = { updateTask(task.copy(isChecked = it)) },
                    )
                }
            }
        }
    )
}
package com.todoapp.ui.screens.homescreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.todoapp.ui.models.TaskUI
import com.todoapp.ui.screens.components.CustomCircularProgressBar

@Composable
fun StatisticCard(
    modifier: Modifier = Modifier,
    tasksList: List<TaskUI>,
) {
    Surface(
        shape = RoundedCornerShape(bottomEnd = 30.dp, bottomStart = 30.dp),
        shadowElevation = 10.dp,
        modifier = Modifier
            .drawWithContent {
                clipRect(
                    left = 0f,
                    top = 0f,
                    right = size.width,
                    bottom = size.height + 30.dp.toPx()
                ) {
                    this@drawWithContent.drawContent()
                }
            },
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp, 0.dp, 16.dp, 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Statistics",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.inverseSurface
                )
            }
            CustomCircularProgressBar(
                percentage = if (tasksList.isNotEmpty())
                        (tasksList.filter { it.isChecked }.size.toFloat() / tasksList.size.toFloat()) else 0f,
                number = 100,
                strokeWidth = 18.dp,
                radius = 90.dp,
                fontSize = 50.sp,
                textColor = Color.Gray,
                subTitle = "Completed"
            )
            Row(
                modifier = Modifier
                    .padding(top = 40.dp, bottom = 10.dp)
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Column(modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "All tasks",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        text = tasksList.size.toString(),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.inverseSurface
                    )
                }
                Divider(
                    modifier = Modifier.fillMaxHeight().width(width = 2.dp),
                    color = MaterialTheme.colorScheme.outline
                )
                Column(modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Completed",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        text = tasksList.filter { it.isChecked }.size.toString(),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.inverseSurface
                    )
                }
            }
        }
    }
}
package com.todoapp.ui.screens.tasklistscreen.tasklistwithproject

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.unit.dp
import com.todoapp.ui.screens.components.CustomBottomSheet
import com.todoapp.ui.screens.components.TaskList


@Composable
fun TaskListWithProjectScreen(
    uiState: TaskListWithProjectState,
    actions: TaskListWithProjectActions,
    showSearchField: Boolean,
    changeShowSearchField: () -> Unit,
    focusRequester: FocusRequester,
    searchValue: String,
    changeSearchValue: (String) -> Unit,
    focusManagerClear: () -> Unit,
    showSheet: Boolean,
    changeShowSheetValue: () -> Unit,
) {

    if (showSheet) {
        CustomBottomSheet(
            onDismiss = { changeShowSheetValue() },
            insertProject = actions.insertProject,
            title = uiState.project.projectTitle,
            color = uiState.project.projectColor,
            projectId = uiState.project.projectId
        )
    }

    LaunchedEffect(null) {
        actions.getDataFromDataBase()
    }


    Column(modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant)) {
        Surface(
            shape = RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp),
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
                }

        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if(!showSearchField) {
                        IconButton(onClick = actions.navigateBack) {
                            Icon(Icons.Filled.KeyboardArrowLeft, null, Modifier.size(48.dp))
                        }
                    }

                    Row(
                        modifier = if(showSearchField) Modifier.padding(start = 8.dp) else Modifier,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextField(
                            modifier = if (showSearchField) Modifier
                                .weight(4f)
                                .focusRequester(focusRequester)
                                .clip(MaterialTheme.shapes.medium)
                                .animateContentSize()
                            else Modifier
                                .width(0.dp)
                                .focusRequester(focusRequester)
                                .animateContentSize(),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colorScheme.inverseSurface,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                focusedTextColor = MaterialTheme.colorScheme.onSecondary
                            ),
                            keyboardActions = KeyboardActions(),
                            value = searchValue,
                            onValueChange = { changeSearchValue(it) },
                        )

                        IconButton(
                            onClick = {
                                changeShowSearchField()
                                if(showSearchField) focusRequester.requestFocus()
                                else focusManagerClear()
                            }
                        ) {
                            Icon(
                                imageVector = if(showSearchField) Icons.Filled.Close else Icons.Filled.Search,
                                contentDescription = "search icon",
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                }
                Column(modifier = Modifier.padding(16.dp, 0.dp, 16.dp, 16.dp)) {
                    Text(
                        text = "Your progress",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = uiState.project.projectTitle,
                                style = MaterialTheme.typography.titleLarge
                            )
                            IconButton(onClick = { changeShowSheetValue() }) {
                                Icon(
                                    imageVector = Icons.Outlined.Edit,
                                    contentDescription = "Edit",
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                        Text(
                            text = "${uiState.tasksList.filter { it.isChecked }.size}/${uiState.tasksList.size}",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth(),
                        color = Color(uiState.project.projectColor),
                        progress = uiState.tasksList.filter { it.isChecked }.size.toFloat() / uiState.tasksList.size.toFloat(),
                        strokeCap = StrokeCap.Round
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Column(Modifier.verticalScroll(rememberScrollState())){
            TaskList(
                tasksList = uiState.tasksList.filter {
                    if(searchValue.isNotEmpty()) searchValue.lowercase() in it.taskTitle.lowercase() else true
                },
                updateTask = actions.updateTask,
                deleteTask = actions.deleteTask,
                navigateToTaskScreen = actions.navigateToTaskScreen
            )
        }
    }
}
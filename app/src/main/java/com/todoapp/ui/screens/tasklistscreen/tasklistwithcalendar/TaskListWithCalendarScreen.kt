package com.todoapp.ui.screens.tasklistscreen.tasklistwithcalendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.unit.dp
import com.todoapp.ui.screens.components.TaskList
import com.todoapp.ui.screens.tasklistscreen.components.CalendarItemCard
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskListWithCalendarScreen(
    uiState: TaskListWithCalendarState,
    actions: TaskListWithCalendarActions,
    showSearchField: Boolean,
    changeShowSearchField: () -> Unit,
    focusRequester: FocusRequester,
    searchValue: String,
    changeSearchValue: (String) -> Unit,
    focusManagerClear: () -> Unit,
    showMenu: Boolean,
    changeShowMenuValue: () -> Unit,
    monthsList: List<String>,
    selectedMonth: Int,
    changeSelectedMonth: (Int) -> Unit,
    lazyCalendarListState: LazyListState,
    selectedDay: Int,
    changeSelectedDay: (Int) -> Unit
) {

    LaunchedEffect(null) {
        actions.getAllTasks()
    }


    Column(modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant)) {
        Surface(
            shape = RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp),
            shadowElevation = 10.dp,
            modifier = Modifier.drawWithContent {
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
                        .padding(horizontal = 8.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (!showSearchField) {
                        IconButton(onClick = { actions.navigateBack() }) {
                            Icon(Icons.Filled.KeyboardArrowLeft, null, Modifier.size(32.dp))
                        }
                    }
                    if (!showSearchField) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = monthsList[selectedMonth],
                                style = MaterialTheme.typography.headlineLarge
                            )
                            DropdownMenu(
                                modifier = Modifier
                                    .height(150.dp)
                                    .clip(MaterialTheme.shapes.medium)
                                    .background(MaterialTheme.colorScheme.surface)
                                    .border(
                                        1.dp,
                                        MaterialTheme.colorScheme.outline,
                                        MaterialTheme.shapes.medium
                                    ),
                                expanded = showMenu,
                                onDismissRequest = { changeShowMenuValue() }
                            ) {
                                monthsList.forEachIndexed { index, item ->
                                    DropdownMenuItem(
                                        text = { Text(text = item) },
                                        onClick = {
                                            changeSelectedMonth(index)
                                            changeShowMenuValue()
                                        }
                                    )
                                }
                            }
                            IconButton(onClick = { changeShowMenuValue() }) {
                                Icon(Icons.Filled.KeyboardArrowDown, null, Modifier.size(32.dp))
                            }
                        }
                    }
                    Row(
                        modifier = if (showSearchField) Modifier.padding(start = 8.dp) else Modifier,
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
                            value = searchValue,
                            onValueChange = { changeSearchValue(it) },
                        )
                        IconButton(
                            modifier = Modifier,
                            onClick = {
                                changeShowSearchField()
                                if (showSearchField) focusRequester.requestFocus()
                                else focusManagerClear()
                            }) {
                            Icon(
                                if (showSearchField) Icons.Filled.Close else Icons.Filled.Search,
                                null,
                                Modifier.size(32.dp)
                            )
                        }
                    }
                }
                LazyRow(
                    state = lazyCalendarListState,
                    contentPadding = PaddingValues(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    items(
                        LocalDate.of(LocalDate.now().year, selectedMonth + 1, 1)
                            .month.length(LocalDate.now().year / 4 == 0)
                    ) { index ->
                        CalendarItemCard(
                            isSelected = selectedDay == index,
                            day = index + 1,
                            onClick = { changeSelectedDay(index) },
                            month = selectedMonth + 1,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Column(Modifier.verticalScroll(rememberScrollState())) {
            TaskList(
                tasksList = uiState.tasksList.filter {
                    selectedDay + 1 == SimpleDateFormat("d").format(Date(it.taskDate)).toInt()
                            && selectedMonth + 1 == SimpleDateFormat("M").format(Date(it.taskDate)).toInt()
                            && if (searchValue.isNotEmpty()) searchValue.lowercase() in it.taskTitle.lowercase() else true
                },
                updateTask = actions.updateTask,
                deleteTask = actions.deleteTask,
                navigateToTaskScreen = actions.navigateToTaskScreen
            )
        }
    }
}
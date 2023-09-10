package com.todoapp.ui.screens.addtaskscreen


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.todoapp.R
import com.todoapp.ui.models.ProjectUI
import com.todoapp.ui.screens.addtaskscreen.components.SuggestionChipLayout
import com.todoapp.ui.screens.addtaskscreen.components.TimePickerDialog
import com.todoapp.ui.screens.components.CustomBottomSheet
import com.todoapp.ui.screens.components.CustomTextField
import java.text.SimpleDateFormat


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(
    projectsList: List<ProjectUI>,
    descValue: String,
    titleValue: String,
    projectId: Long,
    datePickerState: DatePickerState,
    timePickerState: TimePickerState,
    showDatePickerDialog: Boolean,
    showTimePickerDialog: Boolean,
    actions: AddTaskActions,
    titleValidation: String,
    showSheet: Boolean,
    setShowSheet: () -> Unit,
    changeTitleValidation: (String) -> Unit
) {

    if (showSheet) {
        CustomBottomSheet(
            onDismiss = setShowSheet,
            insertProject = actions.insertProject
        )
    }

    Column(verticalArrangement = Arrangement.SpaceBetween) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = actions.navigateBack) {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowLeft,
                        contentDescription = "back",
                        modifier = Modifier.size(32.dp)
                    )
                }
                Row {
                    IconButton(onClick = actions.changeShowDatePickerDialog){
                        Icon(
                            imageVector = Icons.Outlined.DateRange,
                            contentDescription = "show date picker",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    IconButton(onClick = actions.changeShowTimePickerDialog) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_more_time_24),
                            contentDescription = "show time picker",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Outlined.Notifications,
                            contentDescription = "Notifications",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }

            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                SuggestionChipLayout(
                    projectsList = projectsList,
                    changeSelectItem = actions.changeProjectValue,
                    selectedChip = projectId,
                    setShowSheet = setShowSheet
                )
                if (showDatePickerDialog) {
                    DatePickerDialog(
                        onDismissRequest = actions.changeShowDatePickerDialog,
                        confirmButton = {
                            Button(
                                onClick = {
                                    datePickerState.selectedDateMillis?.let {
                                        actions.changeDateValue(it)
                                    }
                                    actions.changeShowDatePickerDialog()
                                }
                            ) {
                                Text(text = "Confirm")
                            }
                        }
                    ) {
                        DatePicker(state = datePickerState)
                    }
                }

                if (showTimePickerDialog) {
                    TimePickerDialog(
                        onCancel = actions.changeShowTimePickerDialog,
                        onConfirm = {
                            actions.changeTimeValue(
                                SimpleDateFormat("hh:mm").parse("${timePickerState.hour}:${timePickerState.minute}").time
                            )
                            actions.changeShowTimePickerDialog()
                        }
                    ) {
                        TimeInput(state = timePickerState)
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                CustomTextField(
                    value = titleValue,
                    onValueChange = {
                        changeTitleValidation("")
                        actions.changeTitleValue(it)
                    },
                    textStyle = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.inverseSurface),
                    placeholderText = "Title",
                    error = titleValidation
                )
                Spacer(modifier = Modifier.height(10.dp))
                CustomTextField(
                    value = descValue,
                    onValueChange = actions.changeDescriptionValue,
                    textStyle = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.inverseSurface),
                    placeholderText = "Description",
                    minLines = 10,
                )
            }
        }
        Column {
            Button(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                shape = MaterialTheme.shapes.small,
                onClick = {
                    if (titleValue.isNotEmpty()) actions.saveTask()
                    else changeTitleValidation("This field cannot be empty")
                }
            ) {
                Text(text = "Save")
            }
        }
    }
}



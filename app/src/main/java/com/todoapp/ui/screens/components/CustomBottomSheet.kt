package com.todoapp.ui.screens.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.todoapp.ui.models.ProjectUI


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomBottomSheet(
    onDismiss: () -> Unit,
    insertProject: (ProjectUI) -> Unit,
    title: String = "",
    color: Long = 0xFF9CCC65,
    projectId: Long = 0,
) {

    val modalBottomSheetState = rememberModalBottomSheetState()

    var projectTitle by remember { mutableStateOf(title) }
    var projectColor by remember { mutableLongStateOf(color) }

    val colorList = listOf(
        0xFF9CCC65,
        0xFF65CCB4,
        0xFF2D55CE,
        0xFFA95BD3,
        0xFFBD2A6A,
    )

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        Column(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 62.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(bottom = 16.dp),
                text = "Add new project",
                style = MaterialTheme.typography.titleLarge
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large,
                value = projectTitle,
                onValueChange = { projectTitle = it },
                label = { Text(text = "Enter a project name") },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                )
            )
            Text(
                modifier = Modifier.padding(top = 26.dp),
                text = "Choose project color",
                style = MaterialTheme.typography.bodyLarge
            )
            LazyRow(modifier = Modifier.padding(vertical = 16.dp)) {
                items(colorList.size) { index ->
                    ElevatedFilterChip(
                        modifier = Modifier.padding(horizontal = 8.dp).size(64.dp),
                        colors = FilterChipDefaults.elevatedFilterChipColors(
                            containerColor = Color(colorList[index]),
                            selectedContainerColor = Color(colorList[index]),
                        ),
                        selected = projectColor == colorList[index],
                        onClick = { projectColor = colorList[index] },
                        border = FilterChipDefaults.filterChipBorder(
                            selectedBorderWidth = 1.dp,
                            selectedBorderColor = MaterialTheme.colorScheme.outline
                        ),
                        label = {
                            if (projectColor == colorList[index])
                                Box(Modifier.size(32.dp)) {
                                    Icon(
                                        imageVector = Icons.Filled.Check,
                                        contentDescription = "check",
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }
                        }
                    )
                }
            }
            Row(modifier = Modifier.padding(top = 16.dp).fillMaxWidth()) {
                Button(
                    modifier = Modifier.weight(1f),
                    shape = MaterialTheme.shapes.medium,
                    onClick = { onDismiss() }
                ) {
                    Text(text = "Cancel")
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    modifier = Modifier.weight(1f),
                    shape = MaterialTheme.shapes.medium,
                    onClick = {
                        insertProject(
                            ProjectUI(
                                projectId = projectId,
                                projectTitle = projectTitle,
                                projectColor = projectColor
                            )
                        )
                        onDismiss()
                    }
                ) {
                    Text(text = "Save")
                }
            }
        }
    }
}
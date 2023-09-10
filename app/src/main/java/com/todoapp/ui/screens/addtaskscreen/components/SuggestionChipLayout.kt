package com.todoapp.ui.screens.addtaskscreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.todoapp.ui.models.ProjectUI


@Composable
fun SuggestionChipLayout(
    projectsList: List<ProjectUI>,
    changeSelectItem: (Long, Long) -> Unit,
    selectedChip: Long,
    setShowSheet: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            item {
                IconButton(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(32.dp),
                    colors = IconButtonDefaults.iconButtonColors(MaterialTheme.colorScheme.primary),
                    onClick = { setShowSheet() }
                ) {
                    Icon(Icons.Filled.Add, null, tint = MaterialTheme.colorScheme.surface)
                }
            }
            items(
                projectsList.size
            ) { index ->
                SuggestionChipEachRow(
                    project = projectsList[index],
                    selected = projectsList[index].projectId == selectedChip,
                    onChipChange = changeSelectItem
                )
            }

        }
    }

}


@Composable
fun SuggestionChipEachRow(
    project: ProjectUI,
    selected: Boolean,
    onChipChange: (Long, Long) -> Unit
) {
    SuggestionChip(
        onClick = {
            if (!selected) onChipChange(project.projectId, project.projectColor)
            else onChipChange(0, 0)
        }, label = {
            Text(
                text = project.projectTitle,
                color = if (selected) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.primary
            )
        },
        modifier = Modifier.padding(end = 8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = SuggestionChipDefaults.suggestionChipColors(
            containerColor = if (selected) MaterialTheme.colorScheme.primary else Color.Transparent
        ),
        border = SuggestionChipDefaults.suggestionChipBorder(
            borderWidth = 1.dp,
            borderColor = if (selected) Color.Transparent else MaterialTheme.colorScheme.primary
        )
    )
}
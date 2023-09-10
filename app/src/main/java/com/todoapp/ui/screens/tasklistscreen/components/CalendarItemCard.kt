package com.todoapp.ui.screens.tasklistscreen.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarItemCard(
    isSelected: Boolean,
    day: Int,
    onClick: () -> Unit,
    month: Int
) {

    val dayName = LocalDate.of(LocalDate.now().year, month, day)
        .dayOfWeek.toString().slice(0..2)

    Column(
        Modifier
            .padding(8.dp)
            .width(60.dp)
            .border(0.dp, Color.Transparent, MaterialTheme.shapes.extraLarge)
            .clip(shape = MaterialTheme.shapes.medium)
            .background(if (isSelected) MaterialTheme.colorScheme.primary else Color(0, 0, 0, 8))
            .border(1.dp, MaterialTheme.colorScheme.primary, shape = MaterialTheme.shapes.medium)
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = dayName,
            style = MaterialTheme.typography.bodySmall,
            color = if (isSelected) Color.White else MaterialTheme.colorScheme.outline
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = day.toString(),
            style = MaterialTheme.typography.bodyLarge,
            color = if (isSelected) Color.White else MaterialTheme.colorScheme.inverseSurface
        )
    }

}
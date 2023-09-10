package com.todoapp.ui.screens.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle


@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    error: String = "",
    placeholderText: String = "Placeholder"
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        readOnly = readOnly,
        textStyle = textStyle,
        modifier = modifier,
        enabled = enabled,
        singleLine = singleLine,
        minLines = minLines,
        maxLines = maxLines
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Box {
                if (value.isEmpty()) {
                    Text(
                        text = placeholderText,
                        style = textStyle,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
                it()
            }
            if (error.isNotEmpty()) {
                Text(text = error, color = MaterialTheme.colorScheme.errorContainer)
            }
        }
    }
}
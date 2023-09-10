package com.todoapp.ui.screens.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomCircularProgressBar(
    percentage: Float,
    number: Int,
    fontSize: TextUnit = 10.sp,
    radius: Dp = 50.dp,
    color: Color = MaterialTheme.colorScheme.primary,
    secondColor: Color = MaterialTheme.colorScheme.outline,
    strokeWidth: Dp = 5.dp,
    animDuration: Int = 1000,
    animDelay: Int = 0,
    showPercents: Boolean = true,
    textColor: Color = Color.Black,
    subTitle: String = "",
) {

    val percentageCheckedTasks: Float = if (percentage.isNaN()) 0f else percentage
    var animationPlayed by remember { mutableStateOf(false) }

    val curPercentage = animateFloatAsState(
        targetValue = if (animationPlayed) percentageCheckedTasks else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = animDelay
        ),
        label = ""
    )

    val percentageText = (curPercentage.value * number).toInt().toString()

    LaunchedEffect(key1 = true) { animationPlayed = true }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.padding(10.dp).size(radius * 2f)
    ) {
        Canvas(modifier = Modifier.size(radius * 2f)) {
            drawCircle(
                color = secondColor,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = 360 * curPercentage.value,
                useCenter = false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = if (showPercents) "$percentageText%" else percentageText,
                color = textColor,
                style = MaterialTheme.typography.displayMedium,
                fontSize = fontSize
            )
            if (subTitle.isNotEmpty())
                Text(text = subTitle, style = MaterialTheme.typography.displayMedium, fontSize = 12.sp, color = Color.Gray)
        }
    }
}
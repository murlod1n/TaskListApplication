package com.todoapp.ui.utils

import com.todoapp.ui.models.TaskUI
import java.text.SimpleDateFormat
import java.util.Date


fun longDateToString(taskUI: TaskUI): String {
    val time = SimpleDateFormat("hh:mm a").format(Date(taskUI.taskTime))
    val date = SimpleDateFormat("dd/mm/yyyy").format(Date(taskUI.taskDate))
    return "$time $date"
}
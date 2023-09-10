package com.todoapp.ui.navigation



sealed class Destination(protected val route: String, vararg params: String) {
    val fullRoute: String = if (params.isEmpty()) route else {
        val builder = StringBuilder(route)
        params.forEach { builder.append("/{${it}}") }
        builder.toString()
    }

    sealed class NoArgumentsDestination(route: String) : Destination(route) {
        operator fun invoke(): String = route
    }

    object HomeScreen : NoArgumentsDestination("home")

    object TaskListWithCalendarScreen : NoArgumentsDestination("task_list_with_calendar")

    object AddTaskScreen : NoArgumentsDestination("add_task")

    object TaskScreen : Destination("task", TASK_ID_KEY) {
        operator fun invoke(taskId: Long) : String = route.appendParams(
            TASK_ID_KEY to taskId
        )
    }

    object TaskListWithProjectScreen: Destination("task_list_screen_with_project", PROJECT_ID_KEY) {

        operator fun invoke(projectId: Long) : String = route.appendParams(
            PROJECT_ID_KEY to projectId
        )
    }

    companion object {
        const val PROJECT_ID_KEY = "firstName"
        const val TASK_ID_KEY = "taskId"
    }

}

internal fun String.appendParams(vararg params: Pair<String, Any?>): String {
    val builder = StringBuilder(this)

    params.forEach {
        it.second?.toString()?.let { arg ->
            builder.append("/$arg")
        }
    }

    return builder.toString()
}


package com.todoapp.domain.repositories

import com.todoapp.domain.models.Task
import kotlinx.coroutines.flow.Flow

interface TasksRepository {

    fun getAllTasks() : Flow<List<Task>>

    fun getTasksWithProject(projectId: Long) : Flow<List<Task>>

    suspend fun updateTasksWithoutProject(projectId: Long)

    suspend fun updateTasksColorByProject(projectId: Long)

    suspend fun insertTask(task: Task)

    suspend fun updateTask(task: Task)

    suspend fun deleteTask(task: Task)

}
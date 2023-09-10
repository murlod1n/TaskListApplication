package com.todoapp.data.repositories

import com.todoapp.data.mappers.toTask
import com.todoapp.data.mappers.toTaskEntity
import com.todoapp.data.storage.dao.TaskDao
import com.todoapp.domain.models.Task
import com.todoapp.domain.repositories.TasksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TasksRepositoryImpl @Inject constructor(private val taskDao: TaskDao) : TasksRepository {

    override fun getAllTasks(): Flow<List<Task>> = flow {
        emit(taskDao.getAllTasks().map { it.toTask() })
    }

    override fun getTasksWithProject(projectId: Long): Flow<List<Task>> = flow {
        emit(taskDao.getTasksWithProject(projectId).map { it.toTask() })
    }

    override suspend fun updateTasksWithoutProject(projectId: Long) {
        taskDao.updateTasksWithoutProject(projectId)
    }

    override suspend fun updateTasksColorByProject(projectId: Long) {
        taskDao.updateTasksColorByProject(projectId)
    }

    override suspend fun insertTask(task: Task) = taskDao.insertTask(task.toTaskEntity())

    override suspend fun updateTask(task: Task) = taskDao.updateTask(task.toTaskEntity())

    override suspend fun deleteTask(task: Task) = taskDao.deleteTask(task.toTaskEntity())

}
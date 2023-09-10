package com.todoapp.domain.usecases.tasks

import com.todoapp.domain.models.Task
import com.todoapp.domain.repositories.TasksRepository
import kotlinx.coroutines.flow.Flow

class GetTasksWithProjectUseCase(
    private val tasksRepository: TasksRepository
) {

    operator fun invoke(projectId: Long): Flow<List<Task>> = tasksRepository.getTasksWithProject(projectId)

}
package com.todoapp.domain.usecases.tasks

import com.todoapp.domain.repositories.TasksRepository

class UpdateTasksWithoutProjectUseCase(private val tasksRepository: TasksRepository) {

    suspend operator fun invoke(projectId: Long) = tasksRepository.updateTasksWithoutProject(projectId)

}
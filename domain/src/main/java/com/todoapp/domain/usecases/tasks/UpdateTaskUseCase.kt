package com.todoapp.domain.usecases.tasks

import com.todoapp.domain.models.Task
import com.todoapp.domain.repositories.TasksRepository

class UpdateTaskUseCase(private val tasksRepository: TasksRepository) {

    suspend operator fun invoke(task: Task) = tasksRepository.updateTask(task = task)

}
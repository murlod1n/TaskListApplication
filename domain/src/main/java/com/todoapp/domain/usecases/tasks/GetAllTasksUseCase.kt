package com.todoapp.domain.usecases.tasks

import com.todoapp.domain.models.Task
import com.todoapp.domain.repositories.TasksRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllTasksUseCase(private val tasksRepository: TasksRepository) {

    operator fun invoke() : Flow<List<Task>> = tasksRepository.getAllTasks()

}
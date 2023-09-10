package com.todoapp.domain.usecases.projects

import com.todoapp.domain.models.Task
import com.todoapp.domain.repositories.ProjectsRepository

class GetAllProjectsUseCase(private val projectsRepository: ProjectsRepository) {

    operator fun invoke() = projectsRepository.getAllProjects()

}
package com.todoapp.domain.usecases.projects

import com.todoapp.domain.models.Project
import com.todoapp.domain.repositories.ProjectsRepository

class DeleteProjectUseCase(private val projectsRepository: ProjectsRepository) {

    suspend operator fun invoke(project: Project) = projectsRepository.deleteProject(project)

}
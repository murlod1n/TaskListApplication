package com.todoapp.domain.usecases.projects

import com.todoapp.domain.models.Project
import com.todoapp.domain.repositories.ProjectsRepository
import kotlinx.coroutines.flow.Flow

class GetProjectByIdUseCase (
    private val projectsRepository: ProjectsRepository
) {

    operator fun invoke(projectId: Long): Flow<Project> = projectsRepository.getProjectById(projectId)

}
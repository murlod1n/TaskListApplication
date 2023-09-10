package com.todoapp.domain.usecases.projects

import com.todoapp.domain.models.Project
import com.todoapp.domain.repositories.ProjectsRepository
import com.todoapp.domain.repositories.TasksRepository

class InsertProjectUseCase(
    private val projectsRepository: ProjectsRepository,
    private val tasksRepository: TasksRepository
) {

    suspend operator fun invoke(project: Project) {
        projectsRepository.insertProject(project)

        if (project.projectId.toInt() != 0) {
            tasksRepository.updateTasksColorByProject(project.projectId)
        }

    }

}
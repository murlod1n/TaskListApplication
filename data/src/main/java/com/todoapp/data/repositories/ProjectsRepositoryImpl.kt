package com.todoapp.data.repositories

import com.todoapp.data.mappers.toProject
import com.todoapp.data.mappers.toProjectEntity
import com.todoapp.data.storage.dao.ProjectDao
import com.todoapp.domain.models.Project
import com.todoapp.domain.repositories.ProjectsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProjectsRepositoryImpl(private val projectDao: ProjectDao) : ProjectsRepository {

    override fun getAllProjects(): Flow<List<Project>> = flow {
        emit(projectDao.getAllProjects().map { it.toProject() })
    }

    override fun getProjectById(projectId: Long): Flow<Project> = flow {
        emit(projectDao.getProjectById(projectId).toProject())
    }

    override suspend fun insertProject(project: Project) = projectDao.insertProject(project.toProjectEntity())

    override suspend fun deleteProject(project: Project) = projectDao.deleteProject(project.toProjectEntity() )
}
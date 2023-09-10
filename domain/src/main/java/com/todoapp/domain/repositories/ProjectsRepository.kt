package com.todoapp.domain.repositories

import com.todoapp.domain.models.Project
import kotlinx.coroutines.flow.Flow


interface ProjectsRepository {

    fun getAllProjects(): Flow<List<Project>>

    fun getProjectById(projectId: Long) : Flow<Project>

    suspend fun insertProject(project: Project)

    suspend fun deleteProject(project: Project)

}
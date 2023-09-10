package com.todoapp.di

import com.todoapp.domain.repositories.ProjectsRepository
import com.todoapp.domain.repositories.TasksRepository
import com.todoapp.domain.usecases.projects.DeleteProjectUseCase
import com.todoapp.domain.usecases.projects.GetAllProjectsUseCase
import com.todoapp.domain.usecases.projects.GetProjectByIdUseCase
import com.todoapp.domain.usecases.projects.InsertProjectUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ProjectsUseCasesModule {

    @Singleton
    @Provides
    fun provideGetAllProjectsUseCase(projectsRepository: ProjectsRepository): GetAllProjectsUseCase =
        GetAllProjectsUseCase(projectsRepository)

    @Singleton
    @Provides
    fun provideInsertProjectUseCase(
        projectsRepository: ProjectsRepository,
        tasksRepository: TasksRepository
    ): InsertProjectUseCase =
        InsertProjectUseCase(projectsRepository, tasksRepository)


    @Singleton
    @Provides
    fun provideDeleteProjectUseCase(projectsRepository: ProjectsRepository): DeleteProjectUseCase =
        DeleteProjectUseCase(projectsRepository)

    @Singleton
    @Provides
    fun provideGetProjectByIdUseCase(projectsRepository: ProjectsRepository): GetProjectByIdUseCase =
        GetProjectByIdUseCase(projectsRepository)

}
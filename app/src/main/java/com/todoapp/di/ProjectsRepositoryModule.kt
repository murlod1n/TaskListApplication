package com.todoapp.di

import com.todoapp.data.repositories.ProjectsRepositoryImpl
import com.todoapp.data.storage.dao.ProjectDao
import com.todoapp.domain.repositories.ProjectsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ProjectsRepositoryModule {

    @Singleton
    @Provides
    fun provideProjectsRepository(projectDao: ProjectDao) : ProjectsRepository =
        ProjectsRepositoryImpl(projectDao)

}
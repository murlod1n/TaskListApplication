package com.todoapp.di

import com.todoapp.data.repositories.TasksRepositoryImpl
import com.todoapp.data.storage.dao.TaskDao
import com.todoapp.domain.repositories.TasksRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TasksRepositoryModule {

    @Singleton
    @Provides
    fun provideTasksRepository(taskDao: TaskDao) : TasksRepository =
        TasksRepositoryImpl(taskDao)

}
package com.todoapp.di

import com.todoapp.domain.repositories.TasksRepository
import com.todoapp.domain.usecases.tasks.DeleteTaskUseCase
import com.todoapp.domain.usecases.tasks.GetAllTasksUseCase
import com.todoapp.domain.usecases.tasks.GetTasksWithProjectUseCase
import com.todoapp.domain.usecases.tasks.InsertTaskUseCase
import com.todoapp.domain.usecases.tasks.UpdateTaskUseCase
import com.todoapp.domain.usecases.tasks.UpdateTasksWithoutProjectUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class TasksUseCasesModule {

    @Singleton
    @Provides
    fun provideGetAllTasksUseCase(tasksRepository: TasksRepository) : GetAllTasksUseCase =
        GetAllTasksUseCase(tasksRepository)

    @Singleton
    @Provides
    fun provideInsertTaskUseCase(tasksRepository: TasksRepository) : InsertTaskUseCase =
        InsertTaskUseCase(tasksRepository)

    @Singleton
    @Provides
    fun provideUpdateTaskUseCase(tasksRepository: TasksRepository) : UpdateTaskUseCase =
        UpdateTaskUseCase(tasksRepository)

    @Singleton
    @Provides
    fun provideDeleteTaskUseCase(tasksRepository: TasksRepository) : DeleteTaskUseCase =
        DeleteTaskUseCase(tasksRepository)

    @Singleton
    @Provides
    fun provideGetTasksWithProjectUseCase(tasksRepository: TasksRepository) : GetTasksWithProjectUseCase =
        GetTasksWithProjectUseCase(tasksRepository)

    @Singleton
    @Provides
    fun provideUpdateTasksWithoutProjectUseCase(tasksRepository: TasksRepository) : UpdateTasksWithoutProjectUseCase =
        UpdateTasksWithoutProjectUseCase(tasksRepository)

}
package com.todoapp.di

import android.app.Application
import com.todoapp.data.storage.dao.ProjectDao
import com.todoapp.data.storage.dao.TaskDao
import com.todoapp.data.storage.database.TodoAppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Singleton
    @Provides
    fun provideTodoAppDataBase(app: Application) : TodoAppDataBase = TodoAppDataBase.getDataBase(context = app)

    @Singleton
    @Provides
    fun provideTaskDao(todoAppDataBase: TodoAppDataBase) : TaskDao = todoAppDataBase.getTaskDao()

    @Singleton
    @Provides
    fun provideProjectDao(todoAppDataBase: TodoAppDataBase) : ProjectDao = todoAppDataBase.getProjectDao()

}
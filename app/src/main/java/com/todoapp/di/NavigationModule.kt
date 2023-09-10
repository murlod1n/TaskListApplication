package com.todoapp.di

import com.todoapp.ui.navigation.AppNavigator
import com.todoapp.ui.navigation.AppNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NavigationModule {

    @Singleton
    @Binds
    fun bindAppNavigator(appNavigatorImpl: AppNavigatorImpl): AppNavigator

}
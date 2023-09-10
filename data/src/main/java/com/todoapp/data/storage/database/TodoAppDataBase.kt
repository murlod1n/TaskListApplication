package com.todoapp.data.storage.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.todoapp.data.storage.dao.ProjectDao
import com.todoapp.data.storage.dao.TaskDao
import com.todoapp.data.storage.models.ProjectEntity
import com.todoapp.data.storage.models.TaskEntity

@Database(entities = [TaskEntity::class, ProjectEntity::class], version = 2)
abstract class TodoAppDataBase : RoomDatabase() {

    abstract fun getTaskDao() : TaskDao
    abstract fun getProjectDao() : ProjectDao

    companion object {

        private var INSTANCE : TodoAppDataBase? = null

        fun getDataBase(context: Context) : TodoAppDataBase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context, TodoAppDataBase::class.java, "todo_app_database.db")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as TodoAppDataBase
        }

    }

}
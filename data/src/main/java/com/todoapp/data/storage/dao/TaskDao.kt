package com.todoapp.data.storage.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.todoapp.data.storage.models.TaskEntity

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks")
    suspend fun getAllTasks() : List<TaskEntity>

    @Query("SELECT * FROM tasks WHERE task_project_id = :projectId")
    suspend fun getTasksWithProject(projectId: Long) : List<TaskEntity>

    @Query("UPDATE tasks SET task_project_id = 0, task_color = '0xFF707070' WHERE task_project_id = :projectId")
    suspend fun updateTasksWithoutProject(projectId: Long)

    @Query("UPDATE tasks SET task_color = (SELECT project_color FROM projects WHERE project_id = :projectId) WHERE task_project_id = :projectId")
    suspend fun updateTasksColorByProject(projectId: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)

}
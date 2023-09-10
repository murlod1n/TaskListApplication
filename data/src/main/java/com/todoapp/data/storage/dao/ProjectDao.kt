package com.todoapp.data.storage.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.todoapp.data.storage.models.ProjectEntity

@Dao
interface ProjectDao {

    @Query("SELECT * FROM projects")
    suspend fun getAllProjects(): List<ProjectEntity>

    @Query("SELECT * FROM projects WHERE project_id = :projectId")
    suspend fun getProjectById(projectId: Long): ProjectEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProject(projectEntity: ProjectEntity)

    @Delete
    suspend fun deleteProject(projectEntity: ProjectEntity)

}
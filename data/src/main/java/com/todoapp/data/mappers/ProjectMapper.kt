package com.todoapp.data.mappers

import com.todoapp.data.storage.models.ProjectEntity
import com.todoapp.domain.models.Project

fun ProjectEntity.toProject() : Project = Project(
    projectId = this.projectId,
    projectTitle = this.projectTitle,
    projectColor = this.projectColor
)

fun Project.toProjectEntity() : ProjectEntity = ProjectEntity(
    projectId = this.projectId,
    projectTitle = this.projectTitle,
    projectColor = this.projectColor
)
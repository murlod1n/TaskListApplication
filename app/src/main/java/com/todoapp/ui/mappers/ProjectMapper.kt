package com.todoapp.ui.mappers

import com.todoapp.domain.models.Project
import com.todoapp.ui.models.ProjectUI

fun Project.toProjectUI() : ProjectUI = ProjectUI(
    projectId = this.projectId,
    projectTitle = this.projectTitle,
    projectColor = this.projectColor
)

fun ProjectUI.toProject() : Project = Project(
    projectId = this.projectId,
    projectTitle = this.projectTitle,
    projectColor = this.projectColor
)
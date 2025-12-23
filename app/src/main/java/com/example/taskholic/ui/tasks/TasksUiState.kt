package com.example.taskholic.ui.tasks

import com.example.taskholic.data.local.entity.TaskEntity

data class TasksUiState(
    val listName: String = "",
    val tasks: List<TaskEntity> = emptyList()
)

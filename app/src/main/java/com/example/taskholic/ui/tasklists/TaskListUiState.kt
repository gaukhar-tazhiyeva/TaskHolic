package com.example.taskholic.ui.tasklists

import com.example.taskholic.data.local.entity.TaskListEntity

data class TaskListsUiState(
    val lists: List<TaskListEntity> = emptyList(),
    val isLoading: Boolean = false
)

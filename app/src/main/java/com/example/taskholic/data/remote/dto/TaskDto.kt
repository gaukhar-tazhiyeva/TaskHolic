package com.example.taskholic.data.remote.dto

import com.example.taskholic.data.local.entity.TaskEntity

data class TaskDto(
    val id: String,
    val title: String,
    val isCompleted: Boolean,
    val taskListId: Long,
    val updatedAt: Long
)

package com.example.taskholic.data.remote.dto

data class TaskDto(
    val id: String = "",
    val title: String = "",
    val description: String? = null,
    val isCompleted: Boolean = false,
    val taskListRemoteId: String = "",
    val updatedAt: Long = 0,
    val isDeleted: Boolean = false
)
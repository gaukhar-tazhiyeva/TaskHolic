package com.example.taskholic.data.remote.mappers

import com.example.taskholic.data.local.entity.TaskEntity
import com.example.taskholic.data.local.entity.TaskListEntity
import com.example.taskholic.data.remote.dto.TaskDto
import com.example.taskholic.data.remote.dto.TaskListDto
import java.util.UUID

fun TaskEntity.toDto(remoteId: String): TaskDto = TaskDto(
    id = remoteId,
    title = title,
    isCompleted = isCompleted,
    taskListId = taskListId,
    updatedAt = System.currentTimeMillis()
)

fun TaskListEntity.toDto(remoteId: String): TaskListDto = TaskListDto(
    id = remoteId,
    name = name,
    updatedAt = System.currentTimeMillis()
)

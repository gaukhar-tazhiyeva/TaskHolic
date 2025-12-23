package com.example.taskholic.data.remote.mappers

import com.example.taskholic.data.local.entity.TaskListEntity
import com.example.taskholic.data.remote.dto.TaskListDto

fun TaskListEntity.toDto(): TaskListDto =
    TaskListDto(
        id = this.listId.toString(),
        name = this.name,
        updatedAt = this.createdAt
    )

fun TaskListDto.toEntity(): TaskListEntity =
    TaskListEntity(
        listId = this.id.toLongOrNull() ?: 0L,
        name = this.name,
        createdAt = this.updatedAt
    )

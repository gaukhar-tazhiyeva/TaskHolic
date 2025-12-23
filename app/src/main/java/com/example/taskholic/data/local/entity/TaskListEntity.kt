package com.example.taskholic.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_lists")
data class TaskListEntity(
    @PrimaryKey(autoGenerate = true)
    val listId: Long = 0L,
    val name: String,
    val createdAt: Long,
    val updatedAt: Long = System.currentTimeMillis(),
    val remoteId: String? = null
)


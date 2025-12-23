package com.example.taskholic.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "tasks",
    foreignKeys = [
        ForeignKey(
            entity = TaskListEntity::class,
            parentColumns = ["listId"],
            childColumns = ["taskListId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("taskListId")]
)
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val taskId: Long = 0L,
    val title: String,
    val isCompleted: Boolean,
    val taskListId: Long,
    val createdAt: Long,
    val updatedAt: Long = System.currentTimeMillis(), // для конфликта last-write-wins
    val remoteId: String? = null // для связи с Firebase
)

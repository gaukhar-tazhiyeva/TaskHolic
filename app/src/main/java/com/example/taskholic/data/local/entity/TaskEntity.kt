package com.example.taskholic.data.local.entity

import androidx.room.Entity
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
    val taskId: Long = 0,

    val remoteId: String? = null,  // Firebase ID
    val title: String,
    val description: String? = null,
    val isCompleted: Boolean = false,
    val taskListId: Long,

    val updatedAt: Long = System.currentTimeMillis(),

    val isDeleted: Boolean = false,   // для soft delete

    val createdAt: Long = System.currentTimeMillis()
)



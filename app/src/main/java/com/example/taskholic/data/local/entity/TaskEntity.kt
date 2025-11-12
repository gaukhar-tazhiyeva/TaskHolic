package com.example.taskholic.data.local.entity

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
    val title: String,
    val isCompleted: Boolean = false,
    val taskListId: Long,
    val createdAt: Long = System.currentTimeMillis()
)


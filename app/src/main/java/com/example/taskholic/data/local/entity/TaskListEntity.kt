package com.example.taskholic.data.local.entity

@Entity(tableName = "task_lists")
data class TaskListEntity(
    @PrimaryKey(autoGenerate = true)
    val listId: Long = 0,
    val name: String,
    val createdAt: Long = System.currentTimeMillis()
)
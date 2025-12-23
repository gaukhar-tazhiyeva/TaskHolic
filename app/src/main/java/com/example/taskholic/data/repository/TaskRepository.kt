package com.example.taskholic.data.repository

import com.example.taskholic.data.local.dao.TaskDao
import com.example.taskholic.data.local.entity.TaskEntity

class TaskRepository(private val dao: TaskDao) {

    suspend fun getTasksByListId(listId: Long): List<TaskEntity> {
        return dao.getTasksByListId(listId)
    }

    suspend fun updateTask(task: TaskEntity) {
        dao.update(task)
    }

    suspend fun insertTask(task: TaskEntity) {
        dao.insert(task)
    }

    suspend fun deleteTask(task: TaskEntity) {
        dao.delete(task)
    }

}

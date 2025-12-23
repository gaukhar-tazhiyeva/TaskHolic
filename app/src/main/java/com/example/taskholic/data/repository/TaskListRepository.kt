package com.example.taskholic.data.repository

import com.example.taskholic.data.local.dao.TaskListDao
import com.example.taskholic.data.local.entity.TaskListEntity
import kotlinx.coroutines.flow.Flow

class TaskListRepository(
    private val dao: TaskListDao
) {
    suspend fun createTaskList(name: String): Long {
        return dao.insert(
            TaskListEntity(
                name = name,
                createdAt = System.currentTimeMillis()
            )
        )
    }

    suspend fun getListName(listId: Long): String {
        return dao.getListNameById(listId)
    }

    suspend fun updateTaskList(taskList: TaskListEntity) {
        dao.update(taskList)
    }

    suspend fun deleteTaskList(taskList: TaskListEntity) {
        dao.delete(taskList)
    }


    fun observeTaskLists() = dao.observeAll()
}

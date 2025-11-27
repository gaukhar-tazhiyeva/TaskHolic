package com.example.taskholic.data.repository

import com.example.taskholic.data.local.dao.TaskDao
import com.example.taskholic.data.local.dao.TaskListDao
import com.example.taskholic.data.local.entity.TaskEntity
import com.example.taskholic.data.local.entity.TaskListEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskRepository @Inject constructor(
    private val taskListDao: TaskListDao,
    private val taskDao: TaskDao
) {

    // Task Lists
    fun getAllLists(): Flow<List<TaskListEntity>> = taskListDao.getAllLists()

    suspend fun insertList(list: TaskListEntity) = taskListDao.insertList(list)

    suspend fun deleteList(list: TaskListEntity) = taskListDao.deleteList(list)

    // Tasks
    fun getTasksByListId(listId: Long): Flow<List<TaskEntity>> = taskDao.getTasksByListId(listId)

    suspend fun insertTask(task: TaskEntity) = taskDao.insertTask(task)

    suspend fun updateTask(task: TaskEntity) = taskDao.updateTask(task)

    suspend fun deleteTask(task: TaskEntity) = taskDao.deleteTask(task)

}


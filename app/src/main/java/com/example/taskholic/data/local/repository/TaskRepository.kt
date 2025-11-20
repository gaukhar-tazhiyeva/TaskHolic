package com.example.taskholic.data.local.repository

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

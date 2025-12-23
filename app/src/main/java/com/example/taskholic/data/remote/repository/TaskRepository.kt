package com.example.taskholic.data.remote.repository

import com.example.taskholic.data.local.dao.TaskDao
import com.example.taskholic.data.local.dao.TaskListDao
import com.example.taskholic.data.local.entity.TaskEntity
import com.example.taskholic.data.local.entity.TaskListEntity
import com.example.taskholic.data.remote.api.FirebaseApiService
import com.example.taskholic.data.remote.dto.TaskDto
import com.example.taskholic.data.remote.dto.TaskListDto
import com.example.taskholic.data.remote.mappers.toDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

class TaskRepository(
    private val taskDao: TaskDao,
    private val taskListDao: TaskListDao,
    private val api: FirebaseApiService
) {

    // ===========================
    // Локальные CRUD
    // ===========================
    suspend fun insertTask(task: TaskEntity) {
        // Генерируем remoteId, если его нет
        val remoteId = task.remoteId ?: UUID.randomUUID().toString()
        val taskWithRemote = task.copy(remoteId = remoteId)

        // Сохраняем в локальную БД
        taskDao.insert(taskWithRemote)

        // Пушим на Firebase
        val dto = taskWithRemote.toDto(remoteId)
        api.putTask(remoteId, dto)
    }

    suspend fun updateTask(task: TaskEntity) {
        // Используем существующий remoteId, если нет — генерируем
        val remoteId = task.remoteId ?: UUID.randomUUID().toString()
        val updatedTask = task.copy(remoteId = remoteId)

        // Обновляем локально
        taskDao.update(updatedTask)

        // Обновляем на Firebase
        val dto = updatedTask.toDto(remoteId)
        api.putTask(remoteId, dto)
    }

    suspend fun deleteTask(task: TaskEntity) {
        // Firebase
        task.remoteId?.let { api.deleteTask(it) }

        // Локально
        taskDao.delete(task)
    }



    suspend fun getTasksByListId(listId: Long) = taskDao.getTasksByListId(listId)
    fun observeTasks(listId: Long) = taskDao.observeByList(listId)

    // ===========================
    // TaskList CRUD
    // ===========================
    suspend fun insertTaskList(taskList: TaskListEntity) {
        val localId = taskListDao.insert(taskList)
        val remoteId = UUID.randomUUID().toString()
        val dto = taskList.toDto(remoteId)
        api.putTaskList(remoteId, dto)
        taskListDao.update(taskList.copy(remoteId = remoteId))
    }

    suspend fun updateTaskList(taskList: TaskListEntity) {
        taskListDao.update(taskList)
        taskList.remoteId?.let { remoteId ->
            val dto = taskList.toDto(remoteId)
            api.putTaskList(remoteId, dto)
        }
    }

    suspend fun deleteTaskList(taskList: TaskListEntity) {
        taskListDao.delete(taskList)
        taskList.remoteId?.let { api.deleteTaskList(it) }
    }

    fun observeTaskLists() = taskListDao.observeAll()
}


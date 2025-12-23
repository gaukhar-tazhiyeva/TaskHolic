package com.example.taskholic.ui.tasks

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskholic.data.local.database.AppDatabase
import com.example.taskholic.data.local.entity.TaskEntity
import com.example.taskholic.data.remote.repository.TaskRepository
import com.example.taskholic.data.repository.TaskListRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TasksViewModel(
    private val taskRepository: TaskRepository,
    private val taskListsRepository: TaskListRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TasksUiState())
    val uiState: StateFlow<TasksUiState> = _uiState

    private var currentListId: Long? = null

    fun load(listId: Long) {
        currentListId = listId

        viewModelScope.launch {
            val tasks = taskRepository.getTasksByListId(listId)
            val listName = taskListsRepository.getListName(listId)

            _uiState.value = TasksUiState(
                listName = listName,
                tasks = tasks
            )
        }
    }



    fun addTask(listId: Long, title: String) {
        viewModelScope.launch {
            val newTask = TaskEntity(
                title = title,
                isCompleted = false,
                taskListId = listId,
                createdAt = System.currentTimeMillis()
            )
            taskRepository.insertTask(newTask)
            load(listId) // обновляем список после добавления
        }
    }

    fun updateTaskTitle(task: TaskEntity, newTitle: String) {
        viewModelScope.launch {
            taskRepository.updateTask(task.copy(title = newTitle))
            load(task.taskListId)
        }
    }

    fun deleteTask(task: TaskEntity) {
        viewModelScope.launch {
            taskRepository.deleteTask(task)
            load(task.taskListId)
        }
    }

    fun toggleTask(task: TaskEntity) {
        viewModelScope.launch {
            taskRepository.updateTask(
                task.copy(isCompleted = !task.isCompleted)
            )
            load(task.taskListId)
        }
    }


}

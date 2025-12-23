package com.example.taskholic.ui.tasklists

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskholic.data.local.database.AppDatabase
import com.example.taskholic.data.local.entity.TaskListEntity
import com.example.taskholic.data.repository.TaskListRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TaskListsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TaskListRepository

    private val _uiState = MutableStateFlow(TaskListsUiState())
    val uiState: StateFlow<TaskListsUiState> = _uiState.asStateFlow()

    init {
        val db = AppDatabase.getInstance(application)
        repository = TaskListRepository(db.taskListDao())

        observeTaskLists()
    }

    private fun observeTaskLists() {
        viewModelScope.launch {
            repository.observeTaskLists().collect { lists ->
                _uiState.value = TaskListsUiState(
                    lists = lists,
                    isLoading = false
                )
            }
        }
    }

    fun addTaskList(
        name: String,
        onCreated: (Long) -> Unit
    ) {
        viewModelScope.launch {
            val id = repository.createTaskList(name)
            onCreated(id)
        }
    }

    fun updateTaskList(taskList: TaskListEntity, newName: String) {
        viewModelScope.launch {
            repository.updateTaskList(taskList.copy(name = newName))
        }
    }

    fun deleteTaskList(taskList: TaskListEntity) {
        viewModelScope.launch {
            repository.deleteTaskList(taskList)
        }
    }



}

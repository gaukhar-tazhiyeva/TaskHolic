package com.example.taskholic.ui.tasks

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.taskholic.data.local.entity.TaskEntity
import com.example.taskholic.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val repo: TaskRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val listId: Long = checkNotNull(savedStateHandle["listId"])
    val tasks = repo.getTasksByListId(listId).asLiveData()

    fun addTask(title: String) = viewModelScope.launch {
        val now = System.currentTimeMillis()
        repo.insertTask(
            TaskEntity(
                taskId = 0L,
                title = title.trim(),
                isCompleted = false,
                taskListId = listId,
                createdAt = now
            )
        )
    }

    fun toggleCompleted(task: TaskEntity, checked: Boolean) = viewModelScope.launch {
        repo.updateTask(task.copy(isCompleted = checked))
    }

    fun deleteTask(task: TaskEntity) = viewModelScope.launch {
        repo.deleteTask(task)
    }
}

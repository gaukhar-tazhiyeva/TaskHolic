package com.example.taskholic.ui.tasklists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.taskholic.data.local.entity.TaskListEntity
import com.example.taskholic.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskListsViewModel @Inject constructor(
    private val repo: TaskRepository
) : ViewModel() {

    val allLists = repo.getAllLists().asLiveData()

    fun addList(name: String) = viewModelScope.launch {
        val now = System.currentTimeMillis()
        repo.insertList(
            TaskListEntity(
                listId = 0L,
                name = name.trim(),
                createdAt = now
            )
        )
    }

    fun deleteList(list: TaskListEntity) = viewModelScope.launch {
        repo.deleteList(list)
    }
}

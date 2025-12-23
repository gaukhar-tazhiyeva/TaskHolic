package com.example.taskholic.ui.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.taskholic.data.repository.TaskListRepository
import com.example.taskholic.data.remote.repository.TaskRepository



class TasksViewModelFactory(
    private val taskRepository: TaskRepository,
    private val taskListsRepository: TaskListRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TasksViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TasksViewModel(
                taskRepository,
                taskListsRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

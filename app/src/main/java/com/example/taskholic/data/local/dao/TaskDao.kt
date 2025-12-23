package com.example.taskholic.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.taskholic.data.local.entity.TaskEntity
import kotlinx.coroutines.flow.Flow
import androidx.room.*


@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks WHERE taskListId = :listId ORDER BY createdAt DESC")
    fun observeByList(listId: Long): Flow<List<TaskEntity>>

    @Insert
    suspend fun insert(task: TaskEntity)

    @Query("UPDATE tasks SET isCompleted = :completed WHERE taskId = :taskId")
    suspend fun markCompleted(taskId: Long, completed: Boolean)

    @Query("SELECT * FROM tasks WHERE taskListId = :listId")
    suspend fun getTasksByListId(listId: Long): List<TaskEntity>

    @Update
    suspend fun update(task: TaskEntity)

    @Delete
    suspend fun delete(task: TaskEntity)
}

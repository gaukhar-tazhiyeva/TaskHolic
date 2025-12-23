package com.example.taskholic.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.taskholic.data.local.entity.TaskListEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskListDao {

    @Query("SELECT * FROM task_lists ORDER BY createdAt DESC")
    fun observeAll(): Flow<List<TaskListEntity>>

    @Insert
    suspend fun insert(taskList: TaskListEntity): Long

    @Query("SELECT name FROM task_lists WHERE listId = :id")
    suspend fun getListNameById(id: Long): String

    @Update
    suspend fun update(taskList: TaskListEntity)

    @Delete
    suspend fun delete(taskList: TaskListEntity)

}

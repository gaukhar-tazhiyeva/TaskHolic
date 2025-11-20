package com.example.taskholic.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.taskholic.data.local.entity.TaskListEntity

@Dao
interface TaskListDao {
    @Query("SELECT * FROM task_lists")
    fun getAllLists(): Flow<List<TaskListEntity>>

    @Insert
    suspend fun insertList(list: TaskListEntity): Long

    @Delete
    suspend fun deleteList(list: TaskListEntity)
}

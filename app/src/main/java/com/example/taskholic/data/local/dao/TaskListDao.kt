package com.example.taskholic.data.local.dao

@Dao
interface TaskListDao {
    @Query("SELECT * FROM task_lists")
    fun getAllLists(): Flow<List<TaskListEntity>>

    @Insert
    suspend fun insertList(list: TaskListEntity): Long

    @Delete
    suspend fun deleteList(list: TaskListEntity)
}

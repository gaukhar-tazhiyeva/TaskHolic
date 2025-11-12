package com.example.taskholic.data.local.dao

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks WHERE taskListId = :listId")
    fun getTasksByListId(listId: Long): Flow<List<TaskEntity>>

    @Insert
    suspend fun insertTask(task: TaskEntity): Long

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)
}

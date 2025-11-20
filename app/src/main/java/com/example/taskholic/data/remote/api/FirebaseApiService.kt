package com.example.taskholic.data.remote.api

import com.example.taskholic.data.remote.dto.TaskDto
import com.example.taskholic.data.remote.dto.TaskListDto

interface FirebaseApiService {

    // TaskLists
    @GET("taskLists.json")
    suspend fun getAllTaskLists(): Map<String, TaskListDto>

    @PUT("taskLists/{id}.json")
    suspend fun putTaskList(
        @Path("id") id: String,
        @Body dto: TaskListDto
    ): TaskListDto

    @PATCH("taskLists/{id}.json")
    suspend fun patchTaskList(
        @Path("id") id: String,
        @Body fields: Map<String, Any>
    )

    @DELETE("taskLists/{id}.json")
    suspend fun deleteTaskList(@Path("id") id: String)


    // Tasks
    @GET("tasks.json")
    suspend fun getAllTasks(): Map<String, TaskDto>

    @PUT("tasks/{id}.json")
    suspend fun putTask(
        @Path("id") id: String,
        @Body dto: TaskDto
    ): TaskDto

    @PATCH("tasks/{id}.json")
    suspend fun patchTask(
        @Path("id") id: String,
        @Body fields: Map<String, Any>
    ): TaskDto

    @DELETE("tasks/{id}.json")
    suspend fun deleteTask(@Path("id") id: String)
}

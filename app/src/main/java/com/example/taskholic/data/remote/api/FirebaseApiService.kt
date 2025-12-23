package com.example.taskholic.data.remote.api

import com.example.taskholic.data.remote.dto.TaskDto
import com.example.taskholic.data.remote.dto.TaskListDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.PUT
import retrofit2.http.Path

interface FirebaseApiService {

    @GET("tasks.json")
    suspend fun getAllTasks(): Map<String, TaskDto>

    @PATCH("tasks/{id}.json")
    suspend fun putTask(
        @Path("id") id: String,
        @Body task: TaskDto
    )

    @DELETE("tasks/{id}.json")
    suspend fun deleteTask(
        @Path("id") id: String
    )

    @GET("taskLists.json")
    suspend fun getAllTaskLists(): Map<String, TaskListDto>

    @PUT("taskLists/{id}.json")
    suspend fun putTaskList(@Path("id") id: String, @Body taskList: TaskListDto): TaskListDto

    @DELETE("taskLists/{id}.json")
    suspend fun deleteTaskList(@Path("id") id: String)
}

package com.example.taskholic.data.repository

import com.example.taskholic.data.local.dao.TaskDao
import com.example.taskholic.data.local.dao.TaskListDao
import com.example.taskholic.data.remote.api.FirebaseApiService
import com.example.taskholic.data.remote.network.NetworkChecker
import javax.inject.Inject

class FirebaseRepository @Inject constructor(
    private val api: FirebaseApiService,
    private val taskDao: TaskDao,
    private val listDao: TaskListDao,
    private val networkChecker: NetworkChecker
) {

    suspend fun pullFromFirebase() {
        //implement fetch from firebase
    }

    suspend fun pushToFirebase() {
        //implement upload from firebase
    }

    suspend fun sync() {
        if (!networkChecker.isOnline()) return

        pullFromFirebase()
        pushToFirebase()
    }
}

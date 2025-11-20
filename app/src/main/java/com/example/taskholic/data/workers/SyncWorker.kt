package com.example.taskholic.data.workers

import android.content.Context
import android.content.SyncResult
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.taskholic.data.sync.SyncManager

class SyncWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val syncManager: SyncManager
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return when (syncManager.performSync()) {
            is SyncResult.Success -> Result.success()
            is SyncResult.Error -> Result.retry()
        }
    }
}

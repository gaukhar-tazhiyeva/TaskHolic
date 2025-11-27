package com.example.taskholic

import android.app.Application
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.taskholic.data.sync.SyncWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit   // ← добавили ЭТОТ импорт

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startSyncWorker()
    }

    private fun startSyncWorker() {
        val request =
            PeriodicWorkRequestBuilder<SyncWorker>(15, TimeUnit.MINUTES)
                .build()

        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork(
                "sync_worker",
                ExistingPeriodicWorkPolicy.KEEP,
                request
            )
    }
}

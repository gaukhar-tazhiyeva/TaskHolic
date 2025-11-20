package com.example.taskholic.data.sync

import android.content.SyncResult
import com.example.taskholic.data.repository.FirebaseRepository
import javax.inject.Inject

class SyncManager @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {

    suspend fun performSync(): SyncResult {
        return try {
            firebaseRepository.sync()
            SyncResult.Success
        } catch (e: Exception) {
            SyncResult.Error(e)
        }
    }
}

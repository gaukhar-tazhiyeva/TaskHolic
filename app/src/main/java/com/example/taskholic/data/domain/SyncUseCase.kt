package com.example.taskholic.data.domain

import com.example.taskholic.data.sync.SyncManager
import javax.inject.Inject

class SyncUseCase @Inject constructor(
    private val syncManager: SyncManager
) {
    suspend operator fun invoke() = syncManager.performSync()
}

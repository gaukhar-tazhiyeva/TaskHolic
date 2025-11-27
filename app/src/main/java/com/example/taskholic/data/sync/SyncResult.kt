package com.example.taskholic.data.sync

sealed class SyncResult {
    data object Success : SyncResult()
    data class Error(val exception: Throwable) : SyncResult()
}

package com.example.taskholic.data.repository

import com.example.taskholic.data.local.dao.PomodoroDao
import com.example.taskholic.data.local.entity.PomodoroSessionEntity
import kotlinx.coroutines.flow.Flow

class PomodoroRepository(
    private val dao: PomodoroDao
) {

    suspend fun startSession(
        startAt: Long,
        durationMinutes: Int,
        associatedTaskId: Long?
    ): Long {
        return dao.insertSession(
            PomodoroSessionEntity(
                startAt = startAt,
                durationMinutes = durationMinutes,
                associatedTaskId = associatedTaskId
            )
        )
    }

    fun observeSessionsLast24h(): Flow<List<PomodoroSessionEntity>> {
        val from = System.currentTimeMillis() - 24 * 60 * 60 * 1000
        return dao.observeSessionsFrom(from)
    }

    suspend fun getTotalMinutesLast24h(): Int {
        val from = System.currentTimeMillis() - 24 * 60 * 60 * 1000
        return dao.getTotalMinutesFrom(from) ?: 0
    }
}

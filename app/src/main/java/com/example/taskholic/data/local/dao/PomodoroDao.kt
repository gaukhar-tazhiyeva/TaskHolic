package com.example.taskholic.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.taskholic.data.local.entity.PomodoroSessionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PomodoroDao {

    @Insert
    suspend fun insertSession(session: PomodoroSessionEntity): Long

    @Query("""
        SELECT * FROM pomodoro_sessions
        WHERE startAt >= :from
        ORDER BY startAt DESC
    """)
    fun observeSessionsFrom(from: Long): Flow<List<PomodoroSessionEntity>>

    @Query("""
        SELECT SUM(durationMinutes) FROM pomodoro_sessions
        WHERE startAt >= :from
    """)
    suspend fun getTotalMinutesFrom(from: Long): Int?
}

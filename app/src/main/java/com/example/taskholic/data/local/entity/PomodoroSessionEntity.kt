package com.example.taskholic.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pomodoro_sessions")
data class PomodoroSessionEntity(
    @PrimaryKey(autoGenerate = true) val sessionId: Long = 0,
    val startAt: Long,
    val durationMinutes: Int,
    val associatedTaskId: Long?,
    val createdAt: Long = System.currentTimeMillis()
)

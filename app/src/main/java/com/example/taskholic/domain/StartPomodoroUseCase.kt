package com.example.taskholic.domain.usecase

import com.example.taskholic.data.repository.PomodoroRepository

class StartPomodoroUseCase(
    private val pomodoroRepository: PomodoroRepository
) {
    suspend fun execute(durationMinutes: Int, taskId: Long?) {
        pomodoroRepository.startSession(
            startAt = System.currentTimeMillis(),
            durationMinutes = durationMinutes,
            associatedTaskId = taskId
        )
    }
}

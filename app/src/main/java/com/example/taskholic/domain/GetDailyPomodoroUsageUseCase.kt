package com.example.taskholic.domain.usecase

import com.example.taskholic.data.repository.PomodoroRepository

class GetDailyPomodoroUsageUseCase(
    private val pomodoroRepository: PomodoroRepository
) {
    suspend fun execute(): Int {
        return pomodoroRepository.getTotalMinutesLast24h()
    }
}

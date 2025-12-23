package com.example.taskholic.ui.pomodoro

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.currentCoroutineContext

interface StartPomodoroUseCase {
    suspend fun execute(durationMinutes: Int, taskId: Long?): Long
}

interface GetDailyPomodoroUsageUseCase {
    suspend fun execute(): Int
}

data class TimerState(
    val remainingMillis: Long = 0L,
    val isRunning: Boolean = false,
    val isFinished: Boolean = false
)

class PomodoroViewModel(
    private val startPomodoroUseCase: StartPomodoroUseCase,
    private val getDailyPomodoroUsageUseCase: GetDailyPomodoroUsageUseCase
) : ViewModel() {

    var onPhaseFinished: ((isPomodoroPhase: Boolean) -> Unit)? = null

    private val _timerState = MutableStateFlow(TimerState())
    val timerState: StateFlow<TimerState> = _timerState.asStateFlow()

    private val _todayMinutes = MutableStateFlow(0)
    val todayMinutes: StateFlow<Int> = _todayMinutes.asStateFlow()

    private var timerJob: Job? = null

    // Новые параметры для Pomodoro циклов
    private var pomodoroMinutes = 25
    private var breakMinutes = 5
    private var repeatCount = 4
    private var longBreakMinutes = 15

    private var currentCycle = 0
    private var isPomodoroPhase = true

    init {
        loadTodayStats()
    }

    fun startTimer(
        pomodoro: Int,
        breakTime: Int,
        repeat: Int,
        longBreak: Int
    ) {
        timerJob?.cancel()

        pomodoroMinutes = pomodoro
        breakMinutes = breakTime
        repeatCount = repeat
        longBreakMinutes = longBreak

        currentCycle = 0
        isPomodoroPhase = true

        startNextPhase()
    }

    private fun startNextPhase() {
        val duration = if (isPomodoroPhase) pomodoroMinutes else {
            if (currentCycle == repeatCount) longBreakMinutes else breakMinutes
        }

        val totalMillis = duration * 60_000L
        _timerState.value = TimerState(
            remainingMillis = totalMillis,
            isRunning = true,
            isFinished = false
        )

        timerJob = viewModelScope.launch {
            var remaining = totalMillis
            while (remaining > 0 && isActive) {
                delay(1000L)
                remaining -= 1000L
                _timerState.value = _timerState.value.copy(remainingMillis = remaining)
            }

            if (isActive) {
                onPhaseFinished()
            }
        }
    }

    private fun onPhaseFinished() {
        if (isPomodoroPhase) {
            currentCycle++
        }

        isPomodoroPhase = !isPomodoroPhase

        onPhaseFinished?.invoke(isPomodoroPhase)

        // Если все циклы Pomodoro пройдены и long break завершен, останавливаем таймер
        if (!isPomodoroPhase && currentCycle > repeatCount) {
            _timerState.value = _timerState.value.copy(isRunning = false, isFinished = true)
            return
        }

        startNextPhase()
    }

    fun pauseTimer() {
        timerJob?.cancel()
        timerJob = null
        _timerState.value = _timerState.value.copy(isRunning = false)
    }

    fun resetTimer() {
        timerJob?.cancel()
        timerJob = null
        _timerState.value = TimerState()
        currentCycle = 0
        isPomodoroPhase = true
    }

    private fun loadTodayStats() {
        viewModelScope.launch {
            _todayMinutes.value = getDailyPomodoroUsageUseCase.execute()
        }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}

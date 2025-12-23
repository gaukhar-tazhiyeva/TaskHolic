package com.example.taskholic.ui.pomodoro

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.taskholic.R
import kotlinx.coroutines.launch

class PomodoroFragment : Fragment(R.layout.fragment_pomodoro) {

    private val viewModel = PomodoroViewModel(
        startPomodoroUseCase = object : StartPomodoroUseCase {
            override suspend fun execute(durationMinutes: Int, taskId: Long?) = 0L
        },
        getDailyPomodoroUsageUseCase = object : GetDailyPomodoroUsageUseCase {
            override suspend fun execute() = 0
        }
    )

    private val REQUEST_CODE_POST_NOTIFICATIONS = 1001

    private val prefs by lazy {
        requireContext().getSharedPreferences("pomodoro_prefs", Context.MODE_PRIVATE)
    }

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    REQUEST_CODE_POST_NOTIFICATIONS
                )
            }
        }
    }

    private fun saveSettings(pomodoro: Int, breakTime: Int, repeat: Int, longBreak: Int) {
        prefs.edit()
            .putInt("pomodoro_minutes", pomodoro)
            .putInt("break_minutes", breakTime)
            .putInt("repeat_count", repeat)
            .putInt("long_break_minutes", longBreak)
            .apply()
    }

    private fun loadSettings(
        etPomodoro: EditText,
        etBreak: EditText,
        etRepeat: EditText,
        etLongBreak: EditText
    ) {
        val pomodoro = prefs.getInt("pomodoro_minutes", 25)
        val breakTime = prefs.getInt("break_minutes", 5)
        val repeat = prefs.getInt("repeat_count", 4)
        val longBreak = prefs.getInt("long_break_minutes", 15)

        etPomodoro.setText(pomodoro.toString())
        etBreak.setText(breakTime.toString())
        etRepeat.setText(repeat.toString())
        etLongBreak.setText(longBreak.toString())
    }

    private fun showNotification(title: String, text: String) {
        val channelId = "pomodoro_channel"
        val notificationManager =
            requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Pomodoro Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = "Pomodoro timer notifications"
            channel.enableLights(true)
            channel.enableVibration(true)
            notificationManager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(requireContext(), channelId)
            .setSmallIcon(R.drawable.ic_timer)
            .setContentTitle(title)
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
            .setAutoCancel(true)

        notificationManager.notify(System.currentTimeMillis().toInt(), builder.build())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkNotificationPermission()

        val tvTimer = view.findViewById<TextView>(R.id.tv_timer)

        val btnReset = view.findViewById<AppCompatImageButton>(R.id.btn_reset)

        val etPomodoro = view.findViewById<EditText>(R.id.et_pomodoro_minutes)
        val etBreak = view.findViewById<EditText>(R.id.et_break_minutes)
        val etRepeat = view.findViewById<EditText>(R.id.et_repeat)
        val etLongBreak = view.findViewById<EditText>(R.id.et_long_break)

        val btnStartPause = view.findViewById<AppCompatImageButton>(R.id.btn_start_pause)
        val tvStartPauseLabel = view.findViewById<TextView>(R.id.tv_start_pause_label)

        val btnBack = view.findViewById<ImageButton>(R.id.btn_back)
        btnBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        loadSettings(etPomodoro, etBreak, etRepeat, etLongBreak)

        // Подписка на таймер
        lifecycleScope.launch {
            viewModel.timerState.collect { state ->
                val minutes = state.remainingMillis / 1000 / 60
                val seconds = (state.remainingMillis / 1000) % 60
                tvTimer.text = "%02d:%02d".format(minutes, seconds)

                if (state.isRunning) {
                    btnStartPause.setImageResource(R.drawable.ic_pause)
                    tvStartPauseLabel.text = "Pause"
                } else {
                    btnStartPause.setImageResource(R.drawable.ic_play_arrow)
                    tvStartPauseLabel.text = "Start"
                }
            }
        }

        btnStartPause.setOnClickListener {
            val timerState = viewModel.timerState.value
            if (timerState.isRunning) {
                viewModel.pauseTimer()
            } else {
                val pomodoroMinutes = etPomodoro.text.toString().toIntOrNull() ?: 25
                val breakMinutes = etBreak.text.toString().toIntOrNull() ?: 5
                val repeatCount = etRepeat.text.toString().toIntOrNull() ?: 4
                val longBreakMinutes = etLongBreak.text.toString().toIntOrNull() ?: 15

                saveSettings(pomodoroMinutes, breakMinutes, repeatCount, longBreakMinutes)

                viewModel.startTimer(pomodoroMinutes, breakMinutes, repeatCount, longBreakMinutes)
            }
        }

        btnReset.setOnClickListener { viewModel.resetTimer() }
    }
}

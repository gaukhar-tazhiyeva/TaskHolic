package com.example.taskholic.ui.pomodoro

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.AudioAttributes
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import com.example.taskholic.R

object PomodoroNotificationManager {

    private const val CHANNEL_ID = "pomodoro_channel"
    private const val CHANNEL_NAME = "Pomodoro Notifications"

    fun showNotification(context: Context, message: String) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)

        // Создаем канал заново только если его нет
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val existingChannel = manager.getNotificationChannel(CHANNEL_ID)
            if (existingChannel == null) {
                val attributes = AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build()

                val channel = NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = "Pomodoro timer notifications"
                    enableLights(true)
                    enableVibration(true)
                    setSound(alarmSound, attributes)
                }
                manager.createNotificationChannel(channel)
            }
        }

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("Pomodoro Timer")
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_timer)
            .setAutoCancel(true)
            .setSound(alarmSound)
            .build()

        manager.notify(System.currentTimeMillis().toInt(), notification)
    }
}

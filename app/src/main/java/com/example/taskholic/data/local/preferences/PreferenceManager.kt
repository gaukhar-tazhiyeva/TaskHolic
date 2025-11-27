package com.example.taskholic.data.local.preferences

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceManager @Inject constructor(
    @ApplicationContext context: Context
) {

    private val prefs = context.getSharedPreferences("taskholic_prefs", Context.MODE_PRIVATE)

    private companion object {
        const val KEY_THEME = "theme"
        const val KEY_FOCUS_TIME = "pomodoro_focus_time"
        const val KEY_BREAK_TIME = "pomodoro_break_time"

        const val DEFAULT_FOCUS_MIN = 25
        const val DEFAULT_BREAK_MIN = 5
        const val DEFAULT_THEME = "system"
    }

    fun getTheme(): String =
        prefs.getString(KEY_THEME, DEFAULT_THEME) ?: DEFAULT_THEME

    fun setTheme(theme: String) {
        prefs.edit().putString(KEY_THEME, theme).apply()
    }

    fun getFocusDuration(): Int =
        prefs.getInt(KEY_FOCUS_TIME, DEFAULT_FOCUS_MIN)

    fun setFocusDuration(minutes: Int) {
        prefs.edit().putInt(KEY_FOCUS_TIME, minutes).apply()
    }

    fun getBreakDuration(): Int =
        prefs.getInt(KEY_BREAK_TIME, DEFAULT_BREAK_MIN)

    fun setBreakDuration(minutes: Int) {
        prefs.edit().putInt(KEY_BREAK_TIME, minutes).apply()
    }
}

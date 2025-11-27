package com.example.taskholic.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskholic.data.local.preferences.PreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val prefs: PreferenceManager
) : ViewModel() {

    private val _focusMinutes = MutableLiveData<Int>()
    val focusMinutes: LiveData<Int> = _focusMinutes

    private val _breakMinutes = MutableLiveData<Int>()
    val breakMinutes: LiveData<Int> = _breakMinutes

    private val _theme = MutableLiveData<String>()
    val theme: LiveData<String> = _theme

    fun loadSettings() {
        // можно и без корутин, но пусть будет единый стиль
        viewModelScope.launch(Dispatchers.IO) {
            val focus = prefs.getFocusDuration()
            val brk = prefs.getBreakDuration()
            val themeValue = prefs.getTheme()

            _focusMinutes.postValue(focus)
            _breakMinutes.postValue(brk)
            _theme.postValue(themeValue)
        }
    }

    fun saveFocusMinutes(minutes: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            prefs.setFocusDuration(minutes)
            _focusMinutes.postValue(minutes)
        }
    }

    fun saveBreakMinutes(minutes: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            prefs.setBreakDuration(minutes)
            _breakMinutes.postValue(minutes)
        }
    }

    fun saveTheme(theme: String) {
        viewModelScope.launch(Dispatchers.IO) {
            prefs.setTheme(theme)
            _theme.postValue(theme)
        }
    }
}

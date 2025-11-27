package com.example.taskholic.ui.quotes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskholic.data.repository.QuoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuoteViewModel @Inject constructor(
    private val quoteRepository: QuoteRepository
) : ViewModel() {

    private val _quoteText = MutableLiveData<String>()
    val quoteText: LiveData<String> = _quoteText

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun fetchQuote() {
        _isLoading.value = true
        _error.value = null

        viewModelScope.launch {
            try {
                val response = quoteRepository.getDailyQuote()
                val authorPart = response.author?.let { " — $it" } ?: ""
                _quoteText.value = response.quote + authorPart
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to load quote"
            } finally {
                _isLoading.value = false
            }
        }
    }
}

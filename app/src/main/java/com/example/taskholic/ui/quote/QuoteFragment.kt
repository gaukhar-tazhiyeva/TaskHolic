package com.example.taskholic.ui.quote

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.taskholic.R
import com.example.taskholic.data.local.entity.QuoteEntity
import com.example.taskholic.data.remote.repository.QuoteRepository
import com.example.taskholic.data.local.database.AppDatabase
import com.example.taskholic.data.remote.api.QuotesApiService
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.navigation.fragment.findNavController
import android.widget.ImageView

class QuoteFragment : Fragment(R.layout.fragment_motivation_quote) {

    private lateinit var tvQuoteText: TextView
    private lateinit var tvQuoteAuthor: TextView
    private lateinit var btnGetQuote: Button
    private lateinit var btnBack: ImageView // <-- кнопка назад

    private val database by lazy { AppDatabase.getInstance(requireContext()) }
    private val quoteDao by lazy { database.quoteDao() }

    private val api: QuotesApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://motivational-spark-api.vercel.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuotesApiService::class.java)
    }

    private val repository by lazy { QuoteRepository(api, quoteDao) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvQuoteText = view.findViewById(R.id.tvQuoteText)
        tvQuoteAuthor = view.findViewById(R.id.tvQuoteAuthor)
        btnGetQuote = view.findViewById(R.id.btnGetQuote)
        btnBack = view.findViewById(R.id.btnBack) // <-- инициализация

        // Показываем последнюю цитату при старте
        lifecycleScope.launch {
            val lastQuote = repository.getQuoteOfDay()
            updateQuoteUI(lastQuote)
        }

        btnGetQuote.setOnClickListener {
            lifecycleScope.launch {
                val newQuote = repository.getQuoteOfDay()
                updateQuoteUI(newQuote)
            }
        }

        // Логика кнопки назад
        btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun updateQuoteUI(quote: QuoteEntity) {
        tvQuoteText.text = quote.text
        tvQuoteAuthor.text = quote.author?.let { "- $it" } ?: ""
    }
}

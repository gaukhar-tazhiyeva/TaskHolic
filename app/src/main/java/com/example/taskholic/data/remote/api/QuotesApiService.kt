package com.example.taskholic.data.remote.api

import com.example.taskholic.data.remote.dto.QuoteDto
import retrofit2.http.GET

interface QuotesApiService {
    @GET("api/quotes/random")
    suspend fun getRandomQuote(): QuoteDto
}

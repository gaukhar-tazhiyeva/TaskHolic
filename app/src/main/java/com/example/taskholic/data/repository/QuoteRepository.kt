package com.example.taskholic.data.repository

import com.example.taskholic.data.remote.api.QuotesApiService
import com.example.taskholic.data.remote.dto.QuoteResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuoteRepository @Inject constructor() {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://freepublicapis.vercel.app/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api: QuotesApiService = retrofit.create(QuotesApiService::class.java)

    suspend fun getDailyQuote(): QuoteResponse {
        return api.getQuote()
    }
}

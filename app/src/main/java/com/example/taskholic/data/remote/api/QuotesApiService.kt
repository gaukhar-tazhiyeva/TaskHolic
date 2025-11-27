package com.example.taskholic.data.remote.api

import com.example.taskholic.data.remote.dto.QuoteResponse
import retrofit2.http.GET

interface QuotesApiService {

    @GET("https://freepublicapis.vercel.app/quotes-api")
    suspend fun getQuote(): QuoteResponse

}

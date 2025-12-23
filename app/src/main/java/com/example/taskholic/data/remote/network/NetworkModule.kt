package com.example.taskholic.data.remote.network

import com.example.taskholic.data.remote.api.FirebaseApiService
import com.example.taskholic.data.remote.api.QuotesApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {

    private const val FIREBASE_BASE_URL = "https://taskholic-2-default-rtdb.firebaseio.com/"
    private const val QUOTES_BASE_URL = "https://motivational-spark-api.vercel.app/api/"

    val firebaseApi: FirebaseApiService by lazy {
        Retrofit.Builder()
            .baseUrl(FIREBASE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FirebaseApiService::class.java)
    }

    val quotesApi: QuotesApiService by lazy {
        Retrofit.Builder()
            .baseUrl(QUOTES_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuotesApiService::class.java)
    }
}

package com.example.taskholic.data.remote.network

import com.example.taskholic.data.remote.api.FirebaseApiService
import com.example.taskholic.data.remote.api.QuotesApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val FIREBASE_BASE_URL =
        "https://<your-project-id>.firebaseio.com/"

    private const val QUOTES_BASE_URL =
        "https://freepublicapis.vercel.app/"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideFirebaseRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(FIREBASE_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideQuoteRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(QUOTES_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideFirebaseApi(retrofit: Retrofit): FirebaseApiService =
        retrofit.create(FirebaseApiService::class.java)

    @Provides
    @Singleton
    fun provideQuotesApi(retrofit: Retrofit): QuotesApiService =
        retrofit.create(QuotesApiService::class.java)
}

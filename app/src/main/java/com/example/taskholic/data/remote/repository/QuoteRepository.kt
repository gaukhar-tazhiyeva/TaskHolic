package com.example.taskholic.data.remote.repository

import com.example.taskholic.data.local.dao.QuoteDao
import com.example.taskholic.data.local.entity.QuoteEntity
import com.example.taskholic.data.remote.api.QuotesApiService
import com.example.taskholic.data.remote.mappers.toEntity

class QuoteRepository(
    private val api: QuotesApiService,
    private val dao: QuoteDao
) {

    suspend fun getQuoteOfDay(): QuoteEntity {
        return try {
            val quoteDto = api.getRandomQuote()
            val entity = quoteDto.toEntity()
            dao.insertOrReplaceQuote(entity)
            entity
        } catch (e: Exception) {
            dao.getLastQuote() ?: QuoteEntity(0, "No quote", null, System.currentTimeMillis())
        }
    }
}

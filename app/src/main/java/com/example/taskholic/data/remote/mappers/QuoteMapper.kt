package com.example.taskholic.data.remote.mappers

import com.example.taskholic.data.local.entity.QuoteEntity
import com.example.taskholic.data.remote.dto.QuoteDto

fun QuoteDto.toEntity(fetchedAt: Long = System.currentTimeMillis()): QuoteEntity =
    QuoteEntity(
        quoteId = 0L,
        text = this.text,
        author = this.author,
        fetchedAt = fetchedAt
    )

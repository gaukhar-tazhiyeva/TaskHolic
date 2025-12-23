package com.example.taskholic.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quotes")
data class QuoteEntity(
    @PrimaryKey(autoGenerate = true)
    val quoteId: Long = 0L,
    val text: String,
    val author: String? = null,
    val fetchedAt: Long
)

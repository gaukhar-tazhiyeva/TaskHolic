package com.example.taskholic.data.remote.dto

import com.google.gson.annotations.SerializedName

data class QuoteDto(
    @SerializedName("quote")
    val text: String,
    val author: String?
)

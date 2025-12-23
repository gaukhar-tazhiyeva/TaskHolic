package com.example.taskholic.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.taskholic.data.local.entity.QuoteEntity

@Dao
interface QuoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplaceQuote(quote: QuoteEntity)

    @Query("SELECT * FROM quotes ORDER BY fetchedAt DESC LIMIT 1")
    suspend fun getLastQuote(): QuoteEntity?
}

package com.example.taskholic.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.taskholic.data.local.dao.TaskDao
import com.example.taskholic.data.local.dao.TaskListDao
import com.example.taskholic.data.local.entity.TaskEntity
import com.example.taskholic.data.local.entity.TaskListEntity
import com.example.taskholic.data.local.dao.QuoteDao
import com.example.taskholic.data.local.entity.QuoteEntity

@Database(
    entities = [TaskListEntity::class, TaskEntity::class, QuoteEntity::class], // добавляем QuoteEntity
    version = 3, // увеличиваем версию, если добавляем новую таблицу
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun taskListDao(): TaskListDao
    abstract fun taskDao(): TaskDao
    abstract fun quoteDao(): QuoteDao // <-- вот это нужно добавить

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "taskholic_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}

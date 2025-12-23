package com.example.taskholic.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.taskholic.data.local.dao.TaskDao
import com.example.taskholic.data.local.dao.TaskListDao
import com.example.taskholic.data.local.entity.TaskEntity
import com.example.taskholic.data.local.entity.TaskListEntity

@Database(
    entities = [TaskListEntity::class, TaskEntity::class],
    version = 2, // увеличиваем версию при изменении схемы
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun taskListDao(): TaskListDao
    abstract fun taskDao(): TaskDao

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
                    .fallbackToDestructiveMigration() // удаляет старую БД при несоответствии схемы
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}

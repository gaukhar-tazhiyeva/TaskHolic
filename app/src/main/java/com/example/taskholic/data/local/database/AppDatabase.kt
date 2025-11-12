package com.example.taskholic.data.local.database

@Database(entities = [TaskListEntity::class, TaskEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskListDao(): TaskListDao
    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "todo_db"
                ).build().also { INSTANCE = it }
            }
    }
}

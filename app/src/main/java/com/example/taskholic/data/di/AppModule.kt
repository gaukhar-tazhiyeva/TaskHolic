package com.example.taskholic.data.di

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    fun provideTaskListDao(db: AppDatabase): TaskListDao = db.taskListDao()

    @Provides
    fun provideTaskDao(db: AppDatabase): TaskDao = db.taskDao()

    @Provides
    @Singleton
    fun provideTaskRepository(taskListDao: TaskListDao, taskDao: TaskDao): TaskRepository {
        return TaskRepository(taskListDao, taskDao)
    }
}

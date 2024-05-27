package com.example.swing.core.database.di

import android.content.Context
import androidx.room.Room
import com.example.swing.core.database.SwDatabase
import com.example.swing.core.database.dao.RecentSearchQueryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providesSwDatabase(
        @ApplicationContext context: Context,
    ): SwDatabase = Room.databaseBuilder(
        context,
        SwDatabase::class.java,
        "sw-database",
    ).build()

    @Provides
    fun providesRecentSearchQueryDao(
        database: SwDatabase,
    ): RecentSearchQueryDao = database.getRecentSearchQueryDao

}
package com.example.swing.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.swing.core.database.dao.RecentSearchQueryDao
import com.example.swing.core.database.entity.RecentSearchQueryEntity
import com.example.swing.core.database.model.InstantConverter

@Database(
    entities = [
        RecentSearchQueryEntity::class
    ],
    version = 1
)
@TypeConverters(
    InstantConverter::class,
)
abstract class SwDatabase : RoomDatabase() {
    abstract val getRecentSearchQueryDao : RecentSearchQueryDao
}
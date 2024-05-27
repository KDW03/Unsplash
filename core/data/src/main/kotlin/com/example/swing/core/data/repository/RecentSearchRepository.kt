package com.example.swing.core.data.repository

import com.example.swing.core.model.RecentSearchQuery
import kotlinx.coroutines.flow.Flow

interface RecentSearchRepository {

    fun getRecentSearchQueries(limit: Int): Flow<List<RecentSearchQuery>>

    suspend fun insertOrReplaceRecentSearch(searchQuery: String)

    suspend fun clearRecentSearches()
}
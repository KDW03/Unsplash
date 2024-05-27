
package com.example.swing.core.data.mapper

import com.example.swing.core.database.entity.RecentSearchQueryEntity
import com.example.swing.core.model.RecentSearchQuery

fun RecentSearchQueryEntity.asExternalModel() = RecentSearchQuery(
    query = query,
    queriedDate = queriedDate,
)

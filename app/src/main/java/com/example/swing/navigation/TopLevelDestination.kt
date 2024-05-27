package com.example.swing.navigation

import com.example.swing.feature.gallery.R as galleryR
import com.example.swing.feature.favorite.R as favoriteR

enum class TopLevelDestination(
    val titleTextId: Int
) {

    GALLERY(
        titleTextId = galleryR.string.gallery
    ),

    Favorite(
        titleTextId = favoriteR.string.favorite
    )

}

package com.example.swing.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.swing.ui.core.icon.SwIcons
import com.example.swing.feature.gallery.R as galleryR
import com.example.swing.feature.favorite.R as favoriteR

enum class TopLevelDestination(
    val titleTextId: Int,
    val bottomIcon: ImageVector,
    ) {

    GALLERY(
        titleTextId = galleryR.string.gallery,
        bottomIcon = SwIcons.Gallery.imageVector
    ),

    Favorite(
        titleTextId = favoriteR.string.favorite,
        bottomIcon = SwIcons.Favorite.imageVector
    )

}

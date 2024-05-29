package com.example.swing.ui.core.icon

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.PhotoLibrary
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.swing.core.ui.R
import com.example.swing.ui.core.icon.Icon.*

object SwIcons {
    val Settings = ImageVectorIcon(Icons.Outlined.Settings)

    val Gallery = ImageVectorIcon(Icons.Outlined.PhotoLibrary)
    val Favorite = ImageVectorIcon(Icons.Outlined.Favorite)

    val Search = ImageVectorIcon(Icons.Outlined.Search)
    val ArrowBack = ImageVectorIcon(Icons.Rounded.ArrowBack)
    val Clear = ImageVectorIcon(Icons.Rounded.Clear)
    val like = ImageVectorIcon(Icons.Default.Favorite)
    val unLike = ImageVectorIcon(Icons.Default.FavoriteBorder)

    val Palette = DrawableResourceIcon(R.drawable.ic_palette)
    val DarkMode = DrawableResourceIcon(R.drawable.ic_dark_mode)
}


sealed interface Icon {
    data class ImageVectorIcon(val imageVector : ImageVector) : Icon
    data class DrawableResourceIcon(@DrawableRes val resourceId: Int) : Icon
}
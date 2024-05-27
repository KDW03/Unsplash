package com.example.swing.feature.gallery.navigation

import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.swing.feature.gallery.GalleryRoute

const val galleryNavigationRoute = "gallery_route"

fun NavController.navigateToGallery(navOptions: NavOptions? = null) {
    this.navigate(galleryNavigationRoute, navOptions)
}

fun NavGraphBuilder.galleryScreen(
    onPhotoClick: (String) -> Unit,
    isScroll: MutableState<Boolean>,
    nestedGraphs: () -> Unit
) {
    composable(
        route = galleryNavigationRoute
    ) {
        GalleryRoute(
            onPhotoClick = onPhotoClick,
            isScroll = isScroll
        )
    }
    nestedGraphs()
}

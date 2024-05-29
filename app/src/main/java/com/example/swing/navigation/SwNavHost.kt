package com.example.swing.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.example.swing.feature.favorite.navigation.favoriteScreen
import com.example.swing.feature.gallery.navigation.galleryNavigationRoute
import com.example.swing.feature.gallery.navigation.galleryScreen
import com.example.swing.feature.navigation.searchScreen
import com.example.swing.feature.settings.navigation.settingsScreen
import com.example.swing.ui.SwAppState

@Composable
fun SwNavHost(
    appState: SwAppState,
    modifier: Modifier = Modifier,
    startDestination: String = galleryNavigationRoute,
    isScroll: MutableState<Boolean>,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        galleryScreen(
            isScroll = isScroll
        )

        favoriteScreen(
            isScroll = isScroll
        )

        searchScreen(
            onBackClick = navController::popBackStack,
        )

        settingsScreen(
            onBackClick = navController::popBackStack
        )
    }
}
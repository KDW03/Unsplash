package com.example.swing.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.swing.core.data.util.NetworkMonitor
import com.example.swing.feature.facvorite.navigation.favoriteNavigationRoute
import com.example.swing.feature.gallery.navigation.galleryNavigationRoute
import com.example.swing.feature.navigation.navigateToSearch
import com.example.swing.feature.settings.navigation.navigateToSettings
import com.example.swing.navigation.TopLevelDestination
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Composable
fun rememberSwAppState(
    networkMonitor: NetworkMonitor,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
): SwAppState {
    return remember(
        navController,
        coroutineScope,
        networkMonitor,
    ) {
        SwAppState(
            navController,
            coroutineScope,
            networkMonitor,
        )
    }
}


@Stable
class SwAppState(
    val navController: NavHostController,
    coroutineScope: CoroutineScope,
    networkMonitor: NetworkMonitor,
) {
    private val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            galleryNavigationRoute -> TopLevelDestination.GALLERY
            favoriteNavigationRoute -> TopLevelDestination.Favorite
            else -> null
        }

    val isOffline = networkMonitor.isOnline
        .map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false,
        )

    fun navigateToSearch() {
        navController.navigateToSearch()
    }

    fun navigateToSetting() {
        navController.navigateToSettings()
    }
}
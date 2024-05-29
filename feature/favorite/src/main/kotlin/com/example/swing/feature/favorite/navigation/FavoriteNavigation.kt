package com.example.swing.feature.favorite.navigation

import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.swing.feature.favorite.FavoriteRoute

const val favoriteNavigationRoute = "favorite_route"


fun NavController.navigateToFavorite(navOptions: NavOptions? = null) {
    this.navigate(favoriteNavigationRoute, navOptions)
}

fun NavGraphBuilder.favoriteScreen(
    isScroll: MutableState<Boolean>,
) {
    composable(
        route = favoriteNavigationRoute
    ) {
        FavoriteRoute(
            isScroll = isScroll
        )
    }
}

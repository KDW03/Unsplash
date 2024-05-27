package com.example.swing.feature.gallery.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.swing.feature.gallery.DetailPhotoRoute
import com.example.swing.feature.gallery.DetailPhotoViewModel

fun NavController.navigateToDetailPhoto(photoId: String) {
    this.navigate("Photo_route/$photoId") {
        launchSingleTop = true
    }
}


fun NavGraphBuilder.detailPhotoScreen() {
    composable(
        "Photo_route/{photoId}",
        arguments = listOf(navArgument("photoId") { type = NavType.IntType })
    ) { backStackEntry ->
        val viewModel: DetailPhotoViewModel = hiltViewModel(backStackEntry)
        val photoId = backStackEntry.arguments?.getString("photoId") ?: ""
        viewModel.setPhotoId(photoId)
        DetailPhotoRoute(viewModel = viewModel)
    }
}
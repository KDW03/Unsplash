package com.example.swing.feature.gallery

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems

@Composable
internal fun GalleryRoute(
    modifier: Modifier = Modifier,
    viewModel: GalleryViewModel = hiltViewModel(),
    onPhotoClick: (String) -> Unit,
    isScroll: MutableState<Boolean>
) {
    val pagingPhotos = viewModel.photosFlow.collectAsLazyPagingItems()
    val orientation = LocalConfiguration.current.orientation


}

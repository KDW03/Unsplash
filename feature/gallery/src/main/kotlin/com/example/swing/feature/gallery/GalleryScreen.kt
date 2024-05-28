package com.example.swing.feature.gallery

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.swing.core.model.Photo
import com.example.swing.ui.core.componenet.PhotoItem
import com.example.swing.ui.core.componenet.calculateColumns

@Composable
internal fun GalleryRoute(
    modifier: Modifier = Modifier,
    viewModel: GalleryViewModel = hiltViewModel(),
    onPhotoClick: (String) -> Unit,
    isScroll: MutableState<Boolean>
) {

    val pagingPhotos = viewModel.photosFlow.collectAsLazyPagingItems()
    val orientation = LocalConfiguration.current.orientation

    GalleryScreen(
        modifier = modifier,
        pagingPhotos = pagingPhotos,
        onPhotoClick = onPhotoClick,
        orientation = orientation,
        isScroll = isScroll,
        onLikeClick = viewModel::updatePhotoLiked
    )
}

@Composable
internal fun GalleryScreen(
    modifier: Modifier,
    pagingPhotos: LazyPagingItems<Photo>,
    onPhotoClick: (String) -> Unit,
    orientation: Int,
    isScroll: MutableState<Boolean>,
    onLikeClick: (String, Boolean) -> Unit,
) {
    Box(
        modifier
            .fillMaxSize()
            .padding(PaddingValues().calculateTopPadding())
    ) {
        val screenWidthDp = LocalConfiguration.current.orientation
        val columns = calculateColumns(screenWidthDp, orientation)
        val listState = rememberLazyGridState()

        LaunchedEffect(listState.isScrollInProgress) {
            isScroll.value = listState.isScrollInProgress
        }

        LazyVerticalGrid(
            state = listState,
            columns = GridCells.Fixed(columns),
            contentPadding = PaddingValues(top = 64.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
            modifier = Modifier
        ) {
            items(
                pagingPhotos.itemCount
            ) { index ->
                pagingPhotos[index]?.let { photo ->
                    PhotoItem(
                        photo = photo,
                        onPhotoClick = onPhotoClick,
                        screenWidthDp = screenWidthDp,
                        modifier = Modifier.padding(8.dp),
                        onLikeClick = onLikeClick
                    )
                }
            }
        }
    }
}



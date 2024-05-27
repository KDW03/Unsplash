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
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.swing.core.model.Photo
import com.example.swing.ui.core.componenet.PhotoItem

@Composable
internal fun GalleryRoute(
    modifier: Modifier = Modifier,
    viewModel: GalleryViewModel = hiltViewModel(),
    onPhotoClick: (String) -> Unit,
    isScroll: MutableState<Boolean>
) {

    val pagingFeeds = viewModel.photosFlow.collectAsLazyPagingItems()
    val orientation = LocalConfiguration.current.orientation

    GalleryScreen(
        modifier = modifier,
        pagingFeeds = pagingFeeds,
        onFeedClick = onPhotoClick,
        orientation = orientation,
        isScroll = isScroll
    )
}

@Composable
internal fun GalleryScreen(
    modifier: Modifier,
    pagingFeeds: LazyPagingItems<Photo>,
    onFeedClick: (String) -> Unit,
    orientation: Int,
    isScroll: MutableState<Boolean>
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
                pagingFeeds.itemCount
            ) { index ->
                pagingFeeds[index]?.let { photo ->
                    PhotoItem(
                        photo = photo,
                        onPhotoClick = onFeedClick,
                        screenWidthDp = screenWidthDp,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}


private fun calculateColumns(screenWidth: Int, orientation: Int): Int {
    val baseColumnWidth = if (orientation == Configuration.ORIENTATION_LANDSCAPE) 200 else 150
    val columns = (screenWidth / baseColumnWidth).coerceAtLeast(2)
    return columns.coerceAtMost(6)
}

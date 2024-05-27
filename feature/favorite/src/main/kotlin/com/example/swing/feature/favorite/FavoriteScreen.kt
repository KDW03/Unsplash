package com.example.swing.feature.favorite

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.swing.core.model.Photo
import com.example.swing.ui.core.componenet.LoadingContent
import com.example.swing.ui.core.componenet.PhotoItem

@Composable
internal fun FavoriteRoute(
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = hiltViewModel(),
    onPhotoClick: (String) -> Unit,
    isScroll: MutableState<Boolean>
) {

    val favoritePhotos by viewModel.favoritePhotoFlow.collectAsStateWithLifecycle()
    val orientation = LocalConfiguration.current.orientation

    FavoriteScreen(
        modifier = modifier,
        favoriteUiState = favoritePhotos,
        onPhotoClick = onPhotoClick,
        orientation = orientation,
        isScroll = isScroll,
        onLikeClick = viewModel::updatePhotoLiked
    )
}


@Composable
internal fun FavoriteScreen(
    modifier: Modifier,
    favoriteUiState: FavoriteUiState,
    onPhotoClick: (String) -> Unit,
    orientation: Int,
    isScroll: MutableState<Boolean>,
    onLikeClick: (String, Boolean) -> Unit,
) {


    when (favoriteUiState) {
        FavoriteUiState.Loading -> {
            LoadingContent(modifier, R.string.favorite_loading)
        }

        is FavoriteUiState.Success -> {
            FavoriteContent(
                modifier = modifier,
                photos = favoriteUiState.photos,
                onPhotoClick = onPhotoClick,
                orientation = orientation,
                isScroll = isScroll,
                onLikeClick = onLikeClick
            )
        }
    }

}

@Composable
fun FavoriteContent(
    modifier: Modifier,
    photos: List<Photo>,
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
            contentPadding = PaddingValues(top = 80.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
            modifier = Modifier
        ) {
            items(photos, key = { it.id }) { photo ->
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


private fun calculateColumns(screenWidth: Int, orientation: Int): Int {
    val baseColumnWidth = if (orientation == Configuration.ORIENTATION_LANDSCAPE) 200 else 150
    val columns = (screenWidth / baseColumnWidth).coerceAtLeast(2)
    return columns.coerceAtMost(6)
}


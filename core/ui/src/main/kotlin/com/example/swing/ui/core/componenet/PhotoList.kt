package com.example.swing.ui.core.componenet

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.swing.core.model.Photo

@Composable
fun PhotoList(
    modifier: Modifier,
    photos: List<Photo>,
    orientation: Int,
    isScroll: MutableState<Boolean>? = null,
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

        if (isScroll != null) {
            LaunchedEffect(listState.isScrollInProgress) {
                isScroll.value = listState.isScrollInProgress
            }
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
                    screenWidthDp = screenWidthDp,
                    modifier = Modifier.padding(8.dp),
                    onLikeClick = onLikeClick
                )
            }
        }
    }
}

fun calculateColumns(screenWidth: Int, orientation: Int): Int {
    val baseColumnWidth = if (orientation == Configuration.ORIENTATION_LANDSCAPE) 200 else 150
    val columns = (screenWidth / baseColumnWidth).coerceAtLeast(2)
    return columns.coerceAtMost(6)
}

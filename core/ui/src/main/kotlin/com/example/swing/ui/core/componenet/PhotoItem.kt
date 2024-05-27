package com.example.swing.ui.core.componenet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.swing.core.model.Photo

@Composable
fun PhotoItem(
    modifier: Modifier = Modifier,
    onPhotoClick: (String) -> Unit,
    photo: Photo,
    screenWidthDp: Int
) {
    val cardHeight = calculateCardHeight(screenWidthDp)

    Card(
        modifier = modifier
            .requiredHeight(cardHeight)
            .fillMaxWidth()
            .clickable { onPhotoClick(photo.id) },
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            SwAsyncImage(
                imageUrl = photo.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
            )
        }
    }
}

private fun calculateCardHeight(screenWidthDp: Int): Dp {
    return if (screenWidthDp < 600) 200.dp else 250.dp
}




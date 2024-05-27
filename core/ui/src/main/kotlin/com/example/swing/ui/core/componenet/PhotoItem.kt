package com.example.swing.ui.core.componenet

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.swing.core.model.Photo
import com.example.swing.ui.core.icon.SwIcons

@Composable
fun PhotoItem(
    modifier: Modifier = Modifier,
    onPhotoClick: (String) -> Unit,
    onLikeClick: (String, Boolean) -> Unit,
    photo: Photo,
    screenWidthDp: Int
) {
    val cardHeight = calculateCardHeight(screenWidthDp)
    var isLiked by remember { mutableStateOf(photo.isLiked) }

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

            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
            ) {
                IconButton(
                    modifier = Modifier.padding(8.dp),
                    onClick = {
                        isLiked = !isLiked
                        onLikeClick(photo.id, isLiked)
                    }
                ) {
                    LikeAnimation(isLiked)
                }
            }
        }
    }
}

@Composable
fun LikeAnimation(isLiked: Boolean) {
    val scale by animateFloatAsState(
        targetValue = if (isLiked) 1.2f else 1f,
        animationSpec = tween(durationMillis = 500), label = ""
    )

    Box(
        modifier = Modifier
            .size(24.dp)
            .graphicsLayer(scaleX = scale, scaleY = scale)
    ) {
        Icon(
            imageVector = if (isLiked) SwIcons.like.imageVector else SwIcons.unLike.imageVector,
            contentDescription = null,
            tint = if (isLiked) Color.Red else Color.Gray,
        )
    }
}


private fun calculateCardHeight(screenWidthDp: Int): Dp {
    return if (screenWidthDp < 600) 200.dp else 250.dp
}

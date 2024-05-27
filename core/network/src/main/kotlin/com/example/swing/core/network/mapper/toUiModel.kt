package com.example.swing.core.network.mapper

import com.example.swing.core.model.Photo
import com.example.swing.core.network.dto.NetworkPhoto
import com.example.swing.core.network.dto.UnsplashNetworkResponse

fun UnsplashNetworkResponse.toUiModel(): List<Photo> {
    return this.results.map { networkPhoto ->
        Photo(
            id = networkPhoto.id,
            createdAt = networkPhoto.createdAt,
            width = networkPhoto.width,
            height = networkPhoto.height,
            color = networkPhoto.color,
            blurHash = networkPhoto.blurHash,
            description = networkPhoto.description,
            imageUrl = networkPhoto.urls.regular,
            detailUrl = networkPhoto.links.html
        )
    }
}

fun NetworkPhoto.toUiModel(): Photo {
    return Photo(
        id = this.id,
        createdAt = this.createdAt,
        width = this.width,
        height = this.height,
        color = this.color,
        blurHash = this.blurHash,
        description = this.description,
        imageUrl = this.urls.regular,
        detailUrl = this.links.html
    )
}
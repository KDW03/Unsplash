@file:OptIn(ExperimentalSerializationApi::class)

package com.example.swing.core.network.dto

import com.example.swing.core.model.Photo
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames


data class NetworkPagingModel(
    val currentPage: Int,
    val nextPage: Int?,
    val prevPage: Int?,
    val photoList: List<NetworkPhoto>,
)

@Serializable
data class UnsplashNetworkResponse(
    @JsonNames("total") val total: Int,
    @JsonNames("total_pages") val totalPages: Int,
    @JsonNames("results") val results: List<NetworkPhoto>
)

@Serializable
data class NetworkPhoto(
    @JsonNames("id") val id: String,
    @JsonNames("created_at") val createdAt: String,
    @JsonNames("width") val width: Int,
    @JsonNames("height") val height: Int,
    @JsonNames("color") val color: String,
    @JsonNames("blur_hash") val blurHash: String,
    @JsonNames("description") val description: String?,
    @JsonNames("urls") val urls: Urls,
    @JsonNames("links") val links: Links,
)

@Serializable
data class Urls(
    @JsonNames("raw") val raw: String,
    @JsonNames("full") val full: String,
    @JsonNames("regular") val regular: String,
    @JsonNames("small") val small: String,
    @JsonNames("thumb") val thumb: String,
    @JsonNames("small_s3") val smallS3: String
)

@Serializable
data class Links(
    @JsonNames("self") val self: String,
    @JsonNames("html") val html: String,
    @JsonNames("download") val download: String,
    @JsonNames("download_location") val downloadLocation: String
)

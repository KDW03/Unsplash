package com.example.swing.core.model

data class Photo(
    val id: String,
    val createdAt: String,
    val width: Int,
    val height: Int,
    val color: String,
    val blurHash: String,
    val description: String?,
    val imageUrl: String,
    val detailUrl: String,
    val isLiked: Boolean = false
)


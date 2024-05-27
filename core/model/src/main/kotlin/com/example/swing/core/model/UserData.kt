package com.example.swing.core.model

data class UserData(
    val likedPhotosIds: Set<String>,
    val darkThemeConfig: DarkThemeConfig,
    val useDynamicColor: Boolean,
)
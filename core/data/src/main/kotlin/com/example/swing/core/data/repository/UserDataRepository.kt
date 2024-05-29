package com.example.swing.core.data.repository

import com.example.swing.core.model.DarkThemeConfig
import com.example.swing.core.model.UserData
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {
    val userData: Flow<UserData>

    suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig)

    suspend fun setDynamicColorPreference(useDynamicColor: Boolean)

    suspend fun setPhotoLiked(photoId: String, liked: Boolean)

}
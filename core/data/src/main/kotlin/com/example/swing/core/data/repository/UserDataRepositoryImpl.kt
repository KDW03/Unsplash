package com.example.swing.core.data.repository

import com.example.swing.core.datastore.SwPreferencesDataSource
import com.example.swing.core.model.DarkThemeConfig
import com.example.swing.core.model.UserData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    private val psPreferencesDataSource: SwPreferencesDataSource,
) : UserDataRepository {

    override val userData: Flow<UserData> =
        psPreferencesDataSource.userData

    override suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        psPreferencesDataSource.setDarkThemeConfig(darkThemeConfig)
    }

    override suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        psPreferencesDataSource.setDynamicColorPreference(useDynamicColor)
    }

    override suspend fun setPhotoLiked(photoId: String, liked: Boolean) {
        psPreferencesDataSource.setPhotosLiked(photoId, liked)
    }


}

package com.example.swing.core.data.di

import com.example.swing.core.data.repository.CompositePhotosRepository
import com.example.swing.core.data.repository.PhotosRepository
import com.example.swing.core.data.repository.PhotosRepositoryImpl
import com.example.swing.core.data.repository.RecentSearchRepository
import com.example.swing.core.data.repository.RecentSearchRepositoryImpl
import com.example.swing.core.data.repository.UserDataRepository
import com.example.swing.core.data.repository.UserDataRepositoryImpl
import com.example.swing.core.data.repository.UserPhotosRepository
import com.example.swing.core.data.util.NetworkMonitor
import com.example.swing.core.data.util.NetworkMonitorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsUserDataRepository(
        userDataRepository: UserDataRepositoryImpl,
    ): UserDataRepository

    @Binds
    fun bindsNetworkMonitor(
        networkMonitor: NetworkMonitorImpl
    ): NetworkMonitor

    @Binds
    fun bindsPhotosRepository(
        photosRepository: PhotosRepositoryImpl,
    ): PhotosRepository

    @Binds
    fun bindsRecentSearchRepository(
        resourceRepository: RecentSearchRepositoryImpl
    ): RecentSearchRepository

    @Binds
    fun bindsUserPhotosRepository(
        userDataRepository: CompositePhotosRepository,
    ): UserPhotosRepository

}
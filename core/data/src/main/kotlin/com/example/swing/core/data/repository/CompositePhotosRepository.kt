package com.example.swing.core.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.example.swing.core.model.Photo
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CompositePhotosRepository @Inject constructor(
    val photosRepository: PhotosResourceRepository,
    val userDataRepository: UserDataRepository,
) : UserPhotosResourceRepository {
    override fun getAllPhotos(): Flow<PagingData<Photo>> =
        photosRepository.getPhotos().combine(
            userDataRepository.userData
        ) { photos, userData ->
            photos.map { photo -> photo.copy(isLiked = photo.id in userData.likedPhotosIds) }
        }

    override fun getPhotosByQuery(query: String): Flow<PagingData<Photo>> =
        photosRepository.getPhotosByQuery(query).combine(
            userDataRepository.userData
        ) { photos, userData ->
            photos.map { photo -> photo.copy(isLiked = photo.id in userData.likedPhotosIds) }
        }

    override fun getAllLikePhotos(): Flow<List<Photo>> =
        userDataRepository.userData.map { it.likedPhotosIds }
            .distinctUntilChanged()
            .flatMapLatest { likedPhotosIds ->
                if (likedPhotosIds.isEmpty()) {
                    flowOf(emptyList())
                } else {
                    flow {
                        val photos = coroutineScope {
                            likedPhotosIds.map { id ->
                                async {
                                    try {
                                        photosRepository.getPhotoById(id)
                                    } catch (e: Exception) {
                                        null
                                    }
                                }
                            }.awaitAll().filterNotNull()
                        }
                        emit(photos)
                    }
                }
            }

}
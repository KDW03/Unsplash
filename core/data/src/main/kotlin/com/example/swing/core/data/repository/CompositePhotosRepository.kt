package com.example.swing.core.data.repository

import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.swing.core.common.di.ApplicationScope
import com.example.swing.core.model.Photo
import kotlinx.coroutines.CoroutineScope
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
    val photosRepository: PhotosRepository,
    val userDataRepository: UserDataRepository,
    @ApplicationScope val scope: CoroutineScope,
) : UserPhotosRepository {

    val userData = userDataRepository.userData
    override fun getAllPhotos(): Flow<PagingData<Photo>> =
        photosRepository.getPhotos().cachedIn(scope = scope).combine(
            userData
        ) { photos, userData ->
            photos.map { photo -> photo.copy(isLiked = photo.id in userData.likedPhotosIds) }
        }

    override fun getPhotosByQuery(query: String): Flow<PagingData<Photo>> =
        photosRepository.getPhotosByQuery(query).cachedIn(scope= scope).combine(
            userData
        ) { photos, userData ->
            photos.map { photo -> photo.copy(isLiked = photo.id in userData.likedPhotosIds) }
        }

    override fun getAllLikePhotos(): Flow<List<Photo>> =
        userData.map { it.likedPhotosIds }
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
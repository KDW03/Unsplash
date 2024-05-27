package com.example.swing.core.data.repository

import androidx.paging.PagingData
import com.example.swing.core.model.Photo
import kotlinx.coroutines.flow.Flow

interface UserPhotosResourceRepository {

    fun getAllPhotos(): Flow<PagingData<Photo>>

    fun getPhotosByQuery(query: String): Flow<PagingData<Photo>>

    fun getAllLikePhotos(): Flow<List<Photo>>
}
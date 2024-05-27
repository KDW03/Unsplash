package com.example.swing.core.data.repository

import androidx.paging.PagingData
import com.example.swing.core.model.Photo
import kotlinx.coroutines.flow.Flow

interface PhotosResourceRepository {
    fun getPhotosByQuery(query: String, page: Int, perPage: Int): Flow<PagingData<Photo>>
    fun getPhotos(page: Int, perPage: Int): Flow<PagingData<Photo>>
    suspend fun getPhotoById(id: String): Photo
}
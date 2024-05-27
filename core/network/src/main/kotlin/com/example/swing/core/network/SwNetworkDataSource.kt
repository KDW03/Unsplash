package com.example.swing.core.network

import com.example.swing.core.network.dto.NetworkPhoto
import com.example.swing.core.network.dto.UnsplashNetworkResponse

interface SwNetworkDataSource {

    suspend fun getPhotos(page: Int, pagePer: Int): UnsplashNetworkResponse
    suspend fun getPhotoById(id: String): NetworkPhoto
    suspend fun getPhotosByQuery(query: String, page: Int, pagePer: Int): UnsplashNetworkResponse

}
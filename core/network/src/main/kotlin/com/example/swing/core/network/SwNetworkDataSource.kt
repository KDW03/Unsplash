package com.example.swing.core.network

import com.example.swing.core.network.dto.NetworkPagingModel
import com.example.swing.core.network.dto.NetworkPhoto

interface SwNetworkDataSource {

    suspend fun getPhotos(page: Int, pagePer: Int): NetworkPagingModel
    suspend fun getPhotoById(id: String): NetworkPhoto
    suspend fun getPhotosByQuery(query: String, page: Int, pagePer: Int): NetworkPagingModel

}
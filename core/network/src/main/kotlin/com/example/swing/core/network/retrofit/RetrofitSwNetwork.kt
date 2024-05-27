package com.example.swing.core.network.retrofit

import com.example.swing.core.network.SwNetworkDataSource
import com.example.swing.core.network.dto.NetworkPhoto
import com.example.swing.core.network.dto.UnsplashNetworkResponse
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitSwNetwork @Inject constructor(
    networkJson: Json,
    okhttpCallFactory: Call.Factory,
) : SwNetworkDataSource {

    private val networkApi = Retrofit.Builder()
        .baseUrl(UNSPLASH_BASE_URL)
        .callFactory(okhttpCallFactory)
        .addConverterFactory(
            networkJson.asConverterFactory("application/json".toMediaType()),
        )
        .build()
        .create(RetrofitSwNetworkApi::class.java)


    override suspend fun getPhotos(page: Int, pagePer: Int): UnsplashNetworkResponse {
        return networkApi.getPhotos(page, pagePer)
    }

    override suspend fun getPhotoById(id: String): NetworkPhoto {
        return networkApi.getPhoto(id)
    }

    override suspend fun getPhotosByQuery(
        query: String,
        page: Int,
        pagePer: Int
    ): UnsplashNetworkResponse {
        return networkApi.searchPhotos(query, page, pagePer)
    }

    companion object {
        private const val UNSPLASH_BASE_URL = "https://api.unsplash.com"
    }
}
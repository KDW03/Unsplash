package com.example.swing.core.network.retrofit

import com.example.swing.core.network.dto.NetworkPhoto
import com.example.swing.core.network.dto.UnsplashNetworkResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitSwNetworkApi {

    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): UnsplashNetworkResponse

    @GET("photos")
    suspend fun getPhotos(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 30
    ): UnsplashNetworkResponse

    @GET("photos/{id}")
    suspend fun getPhoto(@Path("id") photoId: String): NetworkPhoto

}
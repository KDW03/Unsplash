package com.example.swing.core.network.retrofit

import com.example.swing.core.network.SwNetworkDataSource
import com.example.swing.core.network.dto.NetworkPagingModel
import com.example.swing.core.network.dto.NetworkPhoto
import com.example.swing.core.network.dto.UnsplashNetworkResponse
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Response
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


    override suspend fun getPhotos(page: Int, pagePer: Int): NetworkPagingModel {
        val response = networkApi.getPhotos(page, pagePer)
        val photoList = response.body().orEmpty()

        val nextPage = getNextPage(response, page)
        val prevPage = getPrevPage(response, page)

        return NetworkPagingModel(
            currentPage = page,
            nextPage = nextPage,
            prevPage = prevPage,
            photoList = photoList,
        )
    }

    override suspend fun getPhotoById(id: String): NetworkPhoto {
        return networkApi.getPhoto(id)
    }

    override suspend fun getPhotosByQuery(
        query: String,
        page: Int,
        pagePer: Int
    ): NetworkPagingModel {

        val response = networkApi.searchPhotos(query, page, pagePer)
        val photoList = response.body()?.results ?: emptyList()

        val nextPage = getNextPage(response, page)
        val prevPage = getPrevPage(response, page)

        return NetworkPagingModel(
            currentPage = page,
            nextPage = nextPage,
            prevPage = prevPage,
            photoList = photoList,
        )
    }

    private fun <T> getNextPage(response: Response<T>, page: Int): Int? =
        if (response.findNextPage()) page + 1 else null

    private fun <T> getPrevPage(response: Response<T>, page: Int): Int? =
        if (response.findPrevPage()) page - 1 else null

    private fun Response<*>.findNextPage(): Boolean =
        headers()[PAGE_HEADER_NAME]?.contains(NEXT_PAGE_NAME) ?: false

    private fun Response<*>.findPrevPage(): Boolean =
        headers()[PAGE_HEADER_NAME]?.contains(PREV_PAGE_NAME) ?: false


    companion object {
        private const val PAGE_HEADER_NAME = "Link"
        private const val NEXT_PAGE_NAME = "next"
        private const val PREV_PAGE_NAME = "prev"
        private const val UNSPLASH_BASE_URL = "https://api.unsplash.com"
    }
}
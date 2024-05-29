package com.example.swing.core.data.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.swing.core.model.Photo
import com.example.swing.core.network.SwNetworkDataSource
import com.example.swing.core.network.mapper.toUiModel

class SwPagingSource(
    private val network: SwNetworkDataSource,
    private val query: String,
) : PagingSource<Int, Photo>() {
    companion object {
        private const val STARTING_PAGE_INDEX = 1
        const val PAGE_SIZE = 30
    }

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        } ?: STARTING_PAGE_INDEX
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val page = params.key ?: STARTING_PAGE_INDEX

        return try {

            val response = if (query.isEmpty()) {
                network.getPhotos(page, PAGE_SIZE)
            } else {
                network.getPhotosByQuery(query, page, PAGE_SIZE)
            }


            LoadResult.Page(
                data = response.photoList.map { it.toUiModel() },
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (response.nextPage == null) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
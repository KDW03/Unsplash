package com.example.swing.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.swing.core.data.repository.paging.SwPagingSource
import com.example.swing.core.model.Photo
import com.example.swing.core.network.SwNetworkDataSource
import com.example.swing.core.network.mapper.toUiModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PhotosRepositoryImpl @Inject constructor(
    private val swNetworkDataSource: SwNetworkDataSource,
) : PhotosRepository {

    override fun getPhotosByQuery(query: String): Flow<PagingData<Photo>> =
        Pager(
            config = PagingConfig(
                pageSize = SwPagingSource.PAGE_SIZE
            ),
            pagingSourceFactory = {
                SwPagingSource(
                    network = swNetworkDataSource,
                    query = query
                )
            }
        ).flow

    override fun getPhotos(): Flow<PagingData<Photo>> =
        Pager(
            config = PagingConfig(
                pageSize = SwPagingSource.PAGE_SIZE
            ),
            pagingSourceFactory = {
                SwPagingSource(
                    network = swNetworkDataSource,
                    query = ""
                )
            }
        ).flow

    override suspend fun getPhotoById(id: String): Photo = swNetworkDataSource.getPhotoById(id).toUiModel()

}
package com.example.swing.feature.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.swing.core.data.repository.UserDataRepositoryImpl
import com.example.swing.core.data.repository.UserPhotosResourceRepository
import com.example.swing.core.model.Photo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    photosRepository: UserPhotosResourceRepository
) : ViewModel() {

    val photosFlow: Flow<PagingData<Photo>> =
        photosRepository.getAllPhotos().cachedIn(viewModelScope).stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5_000), initialValue = PagingData.empty()
        )

}
package com.example.swing.feature.gallery

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.swing.core.data.repository.UserDataRepository
import com.example.swing.core.data.repository.UserDataRepositoryImpl
import com.example.swing.core.data.repository.UserPhotosRepository
import com.example.swing.core.model.Photo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository,
    photosRepository: UserPhotosRepository
) : ViewModel() {

    val photosFlow: Flow<PagingData<Photo>> =
        photosRepository.getAllPhotos().cachedIn(viewModelScope).stateIn(
            viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = PagingData.empty()
        ).cachedIn(viewModelScope)

    fun updatePhotoLiked(id: String, isLiked: Boolean) {
        viewModelScope.launch {
            userDataRepository.setPhotoLiked(id, isLiked)
        }
    }

}

package com.example.swing.feature.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swing.core.data.repository.UserDataRepository
import com.example.swing.core.data.repository.UserPhotosRepository
import com.example.swing.core.model.Photo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository,
    photosRepository: UserPhotosRepository
) : ViewModel() {

    val favoritePhotoFlow: StateFlow<FavoriteUiState> = photosRepository.getAllLikePhotos().map {
        FavoriteUiState.Success(it)
    }.stateIn(
        scope = viewModelScope,
        initialValue = FavoriteUiState.Loading,
        started = SharingStarted.WhileSubscribed(5_000)
    )

    fun updatePhotoLiked(id: String, isLiked: Boolean) {
        viewModelScope.launch {
            userDataRepository.setPhotoLiked(id, isLiked)
        }
    }

}

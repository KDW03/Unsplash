package com.example.swing.feature.favorite

import com.example.swing.core.model.Photo

sealed interface FavoriteUiState {
    data object Loading : FavoriteUiState
    data class Success(val photos: List<Photo>) : FavoriteUiState
}
package com.example.swing.feature

import androidx.paging.PagingData
import com.example.swing.core.model.Photo

sealed interface SearchResultUiState {
    data object Loading : SearchResultUiState
    data class Success(val photos: PagingData<Photo>) : SearchResultUiState
    data object Empty : SearchResultUiState
    data class Error(val message: String) : SearchResultUiState
}
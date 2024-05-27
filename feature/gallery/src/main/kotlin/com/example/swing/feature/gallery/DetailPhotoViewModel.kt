package com.example.swing.feature.gallery

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class DetailPhotoViewModel @Inject constructor() : ViewModel() {

    private val photoId = MutableStateFlow<String?>(null)

    fun setPhotoId(id: String) {
        photoId.value = id
    }

}

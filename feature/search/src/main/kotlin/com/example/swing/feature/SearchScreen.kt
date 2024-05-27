package com.example.swing.feature

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
internal fun SearchRoute(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onPhotoClick: (String) -> Unit,
    searchViewModel: SearchViewModel = hiltViewModel(),
) {

}
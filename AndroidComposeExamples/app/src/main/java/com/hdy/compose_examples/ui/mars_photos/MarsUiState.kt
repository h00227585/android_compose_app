package com.hdy.compose_examples.ui.mars_photos

import com.hdy.compose_examples.data.model.MarsPhoto

sealed interface MarsUiState {
    data class Success(val photos: List<MarsPhoto>) : MarsUiState
    object Error : MarsUiState
    object Loading : MarsUiState
}
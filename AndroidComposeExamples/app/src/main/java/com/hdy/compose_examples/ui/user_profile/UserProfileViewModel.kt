package com.hdy.compose_examples.ui.user_profile

import androidx.lifecycle.ViewModel
import com.hdy.compose_examples.data.local.staticdata.model.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserProfileViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UserProfile())
    val uiState: StateFlow<UserProfile> = _uiState.asStateFlow()

    fun toggleExpanded() {
        _uiState.value = _uiState.value.copy(
            isExpanded = !_uiState.value.isExpanded
        )
    }

    fun toggleFavorite() {
        _uiState.value = _uiState.value.copy(
            isFavorite = !_uiState.value.isFavorite
        )
    }
}
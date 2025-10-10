package com.hdy.compose_examples.ui.dessert_release

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.hdy.compose_examples.MyApplication
import com.hdy.compose_examples.R
import com.hdy.compose_examples.data.repository.DessertReleasePrefRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class DessertReleaseViewModel(
    private val repository: DessertReleasePrefRepository
) : ViewModel() {
    // UI states access for various [DessertReleaseUiState]
    val uiState: StateFlow<DessertReleaseUiState> =
        repository.isLinearLayout.map { isLinearLayout ->
            DessertReleaseUiState(isLinearLayout)
        }.stateIn(
            scope = viewModelScope,
            // Flow is set to emits value for when app is on the foreground
            // 5 seconds stop delay is added to ensure it flows continuously
            // for cases such as configuration change
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = runBlocking {
                DessertReleaseUiState(
                    isLinearLayout = repository.isLinearLayout.first()
                )
            }
        )

    fun selectLayout(isLinearLayout: Boolean) {
        viewModelScope.launch {
            repository.saveLayoutPreference(isLinearLayout)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MyApplication)
                DessertReleaseViewModel(application.dessertReleasePrefRepository)
            }
        }
    }
}

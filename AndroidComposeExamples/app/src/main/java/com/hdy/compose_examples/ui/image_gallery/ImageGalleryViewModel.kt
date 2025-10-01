package com.hdy.compose_examples.ui.image_gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hdy.compose_examples.data.model.LocalImage
import com.hdy.compose_examples.data.repository.ImageRepository
import kotlinx.coroutines.flow.Flow

// ViewModel，管理 UI 状态和业务逻辑
class ImageGalleryViewModel(
    private val repository: ImageRepository
) : ViewModel() {

    val images: Flow<PagingData<LocalImage>> = repository
        .getImagesPagingFlow()
        .cachedIn(viewModelScope)
}

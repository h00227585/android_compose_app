package com.hdy.compose_examples.ui.image_gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hdy.compose_examples.data.repository.ImageRepository

// ViewModel 工厂，用于依赖注入
class ImageGalleryViewModelFactory(
    private val repository: ImageRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ImageGalleryViewModel::class.java)) {
            return ImageGalleryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
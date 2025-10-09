package com.hdy.compose_examples.ui.mars_photos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.hdy.compose_examples.data.remote.MarsApiService
import com.hdy.compose_examples.data.repository.NetworkMarsPhotosRepository
import com.hdy.compose_examples.domain.repository.MarsPhotosRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

/**
 * Factory for MarsViewModel.
 * 它接受 MarsPhotosRepository 作为参数，并将其传递给 ViewModel 的构造函数。
 */
class MarsViewModelFactory(
    private val marsPhotosRepository: MarsPhotosRepository
) : ViewModelProvider.Factory {
    // 确保泛型 T 是 ViewModel 的子类
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // 检查请求创建的 ViewModel 是否是 MarsViewModel
        if (modelClass.isAssignableFrom(MarsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            // 构造 MarsViewModel 并返回
            return MarsViewModel(marsPhotosRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
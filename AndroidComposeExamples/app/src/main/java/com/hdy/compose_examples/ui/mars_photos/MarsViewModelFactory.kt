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

class MarsViewModelFactory : ViewModelProvider.Factory {
    // 定义 Retrofit 依赖
    private val baseUrl = "https://android-kotlin-fun-mars-server.appspot.com/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(baseUrl)
            .build()
    }

    // 定义 Retrofit Service 依赖
    private val retrofitService: MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java)
    }

    // 定义 Repository 依赖
    private val marsPhotosRepository: MarsPhotosRepository by lazy {
        NetworkMarsPhotosRepository(retrofitService)
    }

    // ViewModel 实例化
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(MarsViewModel::class.java)) {
            // 在此将局部创建的 Repository 实例注入 ViewModel
            return MarsViewModel(
                marsPhotosRepository = this.marsPhotosRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
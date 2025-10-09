package com.hdy.compose_examples.data.remote

import com.hdy.compose_examples.data.model.MarsPhoto
import retrofit2.http.GET

/**
 * A public interface that exposes the [getPhotos] method
 */
interface MarsApiService {
    /**
     * Returns a [List] of [MarsPhoto] and this method can be called from a Coroutine.
     * The @GET annotation indicates that the "photos" endpoint will be requested with the GET
     * HTTP method
     */

    // 从 baseUrl 的 photos 子目录获取 json 格式的数据
    @GET("photos")
    suspend fun getPhotos(): List<MarsPhoto>
}
package com.hdy.compose_examples.data.repository

import com.hdy.compose_examples.data.model.MarsPhoto
import com.hdy.compose_examples.data.remote.MarsApiService
import com.hdy.compose_examples.domain.repository.MarsPhotosRepository

class NetworkMarsPhotosRepository(
    private val marsApiService: MarsApiService
) : MarsPhotosRepository {
    /** Fetches list of MarsPhoto from marsApi*/
    override suspend fun getMarsPhotos(): List<MarsPhoto> = marsApiService.getPhotos()
}
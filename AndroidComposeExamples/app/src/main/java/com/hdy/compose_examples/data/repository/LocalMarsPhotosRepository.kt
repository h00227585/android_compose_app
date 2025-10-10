package com.hdy.compose_examples.data.repository

import com.hdy.compose_examples.data.model.MarsPhoto
import com.hdy.compose_examples.data.local.file.LocalMarsPhotosData
import com.hdy.compose_examples.domain.repository.MarsPhotosRepository

class LocalMarsPhotosRepository(
    private val localDataSource: LocalMarsPhotosData
) : MarsPhotosRepository {
    override suspend fun getMarsPhotos(): List<MarsPhoto> {
        return localDataSource.getMarsPhotos()
    }
}
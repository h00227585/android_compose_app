package com.hdy.compose_examples.data.repository

import com.hdy.compose_examples.data.model.MarsPhoto
import com.hdy.compose_examples.data.source.LocalMarsPhotosDataSource
import com.hdy.compose_examples.domain.repository.MarsPhotosRepository

class LocalMarsPhotosRepository(
    private val localDataSource: LocalMarsPhotosDataSource
) : MarsPhotosRepository {
    override suspend fun getMarsPhotos(): List<MarsPhoto> {
        return localDataSource.getMarsPhotos()
    }
}
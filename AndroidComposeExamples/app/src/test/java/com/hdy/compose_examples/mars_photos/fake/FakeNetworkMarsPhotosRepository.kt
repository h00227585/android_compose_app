package com.hdy.compose_examples.mars_photos.fake

import com.hdy.compose_examples.data.model.MarsPhoto
import com.hdy.compose_examples.domain.repository.MarsPhotosRepository

class FakeNetworkMarsPhotosRepository : MarsPhotosRepository {
    override suspend fun getMarsPhotos(): List<MarsPhoto> {
        return FakeDataSource.photosList
    }
}
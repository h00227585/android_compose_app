package com.hdy.compose_examples.mars_photos.fake

import com.hdy.compose_examples.data.model.MarsPhoto
import com.hdy.compose_examples.data.remote.MarsApiService

class FakeMarsApiService : MarsApiService {
    override suspend fun getPhotos(): List<MarsPhoto> {
        return FakeDataSource.photosList
    }
}
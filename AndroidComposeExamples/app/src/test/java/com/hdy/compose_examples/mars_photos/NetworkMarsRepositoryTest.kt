package com.hdy.compose_examples.mars_photos

import com.hdy.compose_examples.data.repository.NetworkMarsPhotosRepository
import com.hdy.compose_examples.mars_photos.fake.FakeDataSource
import com.hdy.compose_examples.mars_photos.fake.FakeMarsApiService
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class NetworkMarsRepositoryTest {
    @Test
    fun networkMarsPhotosRepository_getMarsPhotos_verifyPhotoList() =
        runTest {
            val repository = NetworkMarsPhotosRepository(
                marsApiService = FakeMarsApiService()
            )
            assertEquals(FakeDataSource.photosList, repository.getMarsPhotos())
        }
}

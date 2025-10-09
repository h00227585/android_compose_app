package com.hdy.compose_examples.mars_photos

import com.hdy.compose_examples.mars_photos.fake.FakeDataSource
import com.hdy.compose_examples.mars_photos.fake.FakeNetworkMarsPhotosRepository
import com.hdy.compose_examples.mars_photos.rules.TestDispatcherRule
import com.hdy.compose_examples.ui.mars_photos.MarsUiState
import com.hdy.compose_examples.ui.mars_photos.MarsViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class MarsViewModelTest {

    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @Test
    fun marsViewModel_getMarsPhotos_verifyMarsUiStateSuccess() =
        runTest {
            val marsViewModel = MarsViewModel(
                marsPhotosRepository = FakeNetworkMarsPhotosRepository()
            )
            assertEquals(
                MarsUiState.Success(FakeDataSource.photosList),
                marsViewModel.marsUiState
            )
        }
}
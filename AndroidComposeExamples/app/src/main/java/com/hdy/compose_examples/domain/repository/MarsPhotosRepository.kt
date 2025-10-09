package com.hdy.compose_examples.domain.repository

import com.hdy.compose_examples.data.model.MarsPhoto

interface MarsPhotosRepository {
    /** Fetches list of MarsPhoto from marsApi */
    suspend fun getMarsPhotos(): List<MarsPhoto>
}
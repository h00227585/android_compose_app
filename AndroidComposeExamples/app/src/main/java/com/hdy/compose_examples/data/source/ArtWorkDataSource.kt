package com.hdy.compose_examples.data.source

import com.hdy.compose_examples.R
import com.hdy.compose_examples.data.model.Artwork

object ArtWorkDataSource {
    val artworks = listOf(
        Artwork(
            imageRes = R.drawable.image1,
            title = "Artwork 1",
            artist = "Anonymous",
            year = "2025"
        ),
        Artwork(
            imageRes = R.drawable.image2,
            title = "Artwork 2",
            artist = "Anonymous",
            year = "2025"
        ),
        Artwork(
            imageRes = R.drawable.image3,
            title = "Artwork 3",
            artist = "Anonymous",
            year = "2025"
        )
    )
}
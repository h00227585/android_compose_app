package com.hdy.compose_examples.data.model

data class LocalImage(
    val id: Long,
    val uri: String,
    val displayName: String,
    val dateAdded: Long,
    val size: Long
)
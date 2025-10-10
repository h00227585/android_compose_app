package com.hdy.compose_examples.data.local.staticdata.model

import com.hdy.compose_examples.data.model.IconSource


data class Example(
    val id: String,
    val title: String,
    val description: String,
    val icon: IconSource,
    val route: String
)

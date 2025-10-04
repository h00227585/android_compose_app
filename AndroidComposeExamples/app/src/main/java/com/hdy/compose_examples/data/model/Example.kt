package com.hdy.compose_examples.data.model

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.hdy.compose_examples.R

sealed class IconSource {
    data class Vector(val imageVector: ImageVector) : IconSource()
    data class Resource(@DrawableRes val resId: Int) : IconSource()
}

data class Example(
    val id: String,
    val title: String,
    val description: String,
    val icon: IconSource,
    val route: String
)

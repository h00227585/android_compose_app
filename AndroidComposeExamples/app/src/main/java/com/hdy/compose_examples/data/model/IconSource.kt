package com.hdy.compose_examples.data.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.vector.ImageVector

sealed class IconSource {
    data class Vector(val imageVector: ImageVector) : IconSource()
    data class Resource(@DrawableRes val resId: Int) : IconSource()
}
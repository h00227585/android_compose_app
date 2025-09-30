package com.hdy.compose_examples.data.model

import androidx.compose.ui.graphics.painter.Painter

data class Answer(val image: Painter, val text: String, val isSelected: Boolean = false)

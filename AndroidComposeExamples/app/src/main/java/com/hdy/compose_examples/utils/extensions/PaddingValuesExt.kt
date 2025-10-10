package com.hdy.compose_examples.utils.extensions

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection

fun PaddingValues.addHorizontal(horizontal: Dp): PaddingValues {
    return PaddingValues(
        start = this.calculateStartPadding(LayoutDirection.Ltr) + horizontal,
        end = this.calculateEndPadding(LayoutDirection.Ltr) + horizontal,
        top = calculateTopPadding(),
        bottom = calculateBottomPadding()
    )
}

fun PaddingValues.setHorizontal(horizontal: Dp): PaddingValues {
    return PaddingValues(
        start = horizontal,
        end = horizontal,
        top = calculateTopPadding(),
        bottom = calculateBottomPadding()
    )
}
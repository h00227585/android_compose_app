package com.hdy.compose_examples.data.local.staticdata.model

import androidx.annotation.StringRes

data class BlurAmount(
    @StringRes val blurAmountRes: Int,
    val blurAmount: Int
)
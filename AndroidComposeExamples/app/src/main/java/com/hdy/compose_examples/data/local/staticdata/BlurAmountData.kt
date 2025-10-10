package com.hdy.compose_examples.data.local.staticdata

import com.hdy.compose_examples.R
import com.hdy.compose_examples.data.local.staticdata.model.BlurAmount

object BlurAmountData {
    val blurAmount = listOf(
        BlurAmount(
            blurAmountRes = R.string.blur_lv_1,
            blurAmount = 1
        ),
        BlurAmount(
            blurAmountRes = R.string.blur_lv_2,
            blurAmount = 2
        ),
        BlurAmount(
            blurAmountRes = R.string.blur_lv_3,
            blurAmount = 3
        )
    )
}
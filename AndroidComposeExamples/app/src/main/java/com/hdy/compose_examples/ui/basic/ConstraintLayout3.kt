package com.hdy.compose_examples.ui.basic

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension


// 实现比较复杂的布局需求时，可以使用constraint layout，
// 代替 Column, Row, Box等布局
@Composable
fun ConstraintLayout3() {
    ConstraintLayout {
        val (text) = createRefs()
        val guideline = createGuidelineFromStart(fraction = 0.5f)

        Text(
            text = "This is a long long long long long long long long long long long text",
            modifier = Modifier.constrainAs(text) {
                linkTo(start = guideline, end = parent.end)
                // 忽略下面一句，默认也会换行，效果不太一样
                // 适配长内容的换行
                width = Dimension.preferredWrapContent
            }
        )
    }
}
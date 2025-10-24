package com.hdy.compose_examples.ui.basic

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// Compose 有一项规则，即，子项只能测量一次，测量两次就会引发运行时异常。
// 但是，有时需要先收集一些关于子项的信息，然后再测量子项。
//
// Modifier.width(IntrinsicSize.Min) - 显示内容所需的最小宽度是多少？
// Modifier.width(IntrinsicSize.Max) - 需要多大的最大宽度才能正确显示内容？
// Modifier.height(IntrinsicSize.Min) - 显示内容所需的最小高度是多少？
// Modifier.height(IntrinsicSize.Max) - 需要多大的最大高度才能正确显示内容？

@Composable
fun Intrinsics1 (modifier: Modifier = Modifier) {
    TwoTexts("hello", "kotlin")
}

@Composable
private fun TwoTexts(text1: String, text2: String, modifier: Modifier = Modifier) {
    Row(modifier = modifier.height(IntrinsicSize.Min)) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp)
                .wrapContentWidth(Alignment.Start),
            text = text1
        )
        VerticalDivider(
            color = Color.Black,
            modifier = Modifier.fillMaxHeight().width(1.dp)
        )
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp)
                .wrapContentWidth(Alignment.End),

            text = text2
        )
    }
}
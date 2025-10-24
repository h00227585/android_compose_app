package com.hdy.compose_examples.ui.basic.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

// Compose 有一项规则，即，子项只能测量一次，测量两次就会引发运行时异常。
// 但是，有时需要先收集一些关于子项的信息，然后再测量子项。
//
// Modifier.width(IntrinsicSize.Min) - 显示内容所需的最小宽度是多少？
// Modifier.width(IntrinsicSize.Max) - 需要多大的最大宽度才能正确显示内容？
// Modifier.height(IntrinsicSize.Min) - 显示内容所需的最小高度是多少？
// Modifier.height(IntrinsicSize.Max) - 需要多大的最大高度才能正确显示内容？

@Composable
fun Intrinsics2 (modifier: Modifier = Modifier) {
    Texts()
}

@Composable
fun Texts() {
    Column(modifier = Modifier.background(color = Color.LightGray)) {
        Text(text = "hello",
            modifier = Modifier
                .background(Color.Gray))
        Text(text = "hello hello",
            modifier = Modifier
                .background(Color.Gray))
        Text(text = "hello hello hello",
            modifier = Modifier
                .background(Color.Gray))
    }
    Column(modifier = Modifier.background(color = Color.LightGray)) {
        Text(text = "hello",
            modifier = Modifier
                .background(Color.Gray)
                .fillMaxWidth())
        Text(text = "hello hello",
            modifier = Modifier
                .background(Color.Gray)
                .fillMaxWidth())
        Text(text = "hello hello hello",
            modifier = Modifier
                .background(Color.Gray)
                .fillMaxWidth())
    }
    Column(modifier = Modifier
        .background(color = Color.LightGray)
        .width(IntrinsicSize.Max)
    ) {
        Text(text = "hello",
            modifier = Modifier
                .background(Color.Gray)
                .fillMaxWidth())
        Text(text = "hello hello",
            modifier = Modifier
                .background(Color.Gray)
                .fillMaxWidth())
        Text(text = "hello hello hello",
            modifier = Modifier
                .background(Color.Gray)
                .fillMaxWidth())
    }
}
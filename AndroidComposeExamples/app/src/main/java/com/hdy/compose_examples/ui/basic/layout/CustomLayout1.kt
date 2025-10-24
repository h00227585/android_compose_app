package com.hdy.compose_examples.ui.basic.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// 通过扩展函数自定义布局
@Composable
fun CustomLayout1(modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        // 默认
        Text(text = "Text 1",
            color = Color.White,
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            modifier = modifier
                .background(Color.Red)
                .weight(1f))
        // 设置padding
        Text(text = "Text 2",
            color = Color.White,
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            modifier = modifier
                .background(Color.DarkGray)
                .weight(1f)
                .padding(top = 16.dp))
        Text(text = "Text 3",
            color = Color.White,
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            modifier = modifier
                .background(Color.Blue)
                .weight(1f)
                .firstBaseLineToTop(45.dp)
        )
    }
}

@Composable
fun Modifier.firstBaseLineToTop(
    firstBaselineToTop: Dp // 第一条基线到顶部的距离
) = layout { measurable, constraints ->
    // 测量被修饰的 Composable（通常是它的唯一子项）
    val placeable: Placeable = measurable.measure(constraints)

    // 检查 Composable 是否有第一条基线并获取其位置
    // 对于 Text Composable，它会公开 FirstBaseline
    val firstBaseline = placeable[FirstBaseline]

    // 检查是否有 FirstBaseline，如果没有则抛出异常或使用默认值
    if (firstBaseline == AlignmentLine.Unspecified) {
        error("This Composable does not provide a FirstBaseline alignment line.")
    }

    // 计算 Composable 需要向下放置的垂直偏移量
    // 目标距离 - 原始基线距离 = 需要的额外顶部空间
    val placeableY = firstBaselineToTop.roundToPx() - firstBaseline

    // 计算最终布局的总高度
    // 原始高度 + 偏移量（如果偏移量是正的，即向下推）
    val height = placeable.height + maxOf(0, placeableY) // 确保高度不小于原始高度（或者根据需要调整）

    // 确定布局的尺寸
    layout(placeable.width, height) {
        // 放置 Composable
        // placeRelative 用于从左上角 (0, 0) 开始放置。
        // 我们将 y 坐标设置为 placeableY，以达到控制基线位置的目的。
        placeable.placeRelative(0, placeableY)
    }
}
package com.hdy.compose_examples.ui.basic.layout

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout


// 自定义列布局

@Composable
fun CustomLayout2() {
    MyColumn {
        Text(
            text = "Hello Kotlin",
        )
        Text(
            text = "Hello Kotlin",
        )
        Text(
            text = "Hello Kotlin",
        )
    }
}

@Composable
private fun MyColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) {measurables, constraints ->
        // 测量子元素
        val placeables = measurables.map { it.measure(constraints) }

        // 计算Column实际尺寸
        val columnWidth = placeables.maxOfOrNull { it.width } ?: constraints.minWidth
        val columnHeight = placeables.sumOf { it.height }

        // 布局
        var yPos = 0
        layout(width = columnWidth, height = columnHeight) {
            placeables.forEach { it ->
                it.placeRelative(x = 0, y = yPos)
                yPos += it.height
            }
        }
    }
}
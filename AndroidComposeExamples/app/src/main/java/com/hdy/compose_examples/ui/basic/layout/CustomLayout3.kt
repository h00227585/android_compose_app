package com.hdy.compose_examples.ui.basic.layout

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// 自定义瀑布流布局

@Composable
fun CustomLayout3() {
    StaggeredGrid {
        for (fruit in fruits) {
            Item(
                text = fruit,
                modifier = Modifier.padding(9.dp)
            )
        }
    }
}

@Composable
private fun StaggeredGrid(
    modifier: Modifier = Modifier,
    columnCount: Int = 3,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        // 计算每列的约束条件
        val columnWidth = if (columnCount > 0) {
            constraints.maxWidth / columnCount
        } else {
            // 防止除以零
            constraints.maxWidth
        }

        // 为每个子元素设置测量的宽度约束（高度将是无限制的）
        val itemConstraints = constraints.copy(
            minWidth = columnWidth,
            maxWidth = columnWidth,
            minHeight = 0,
            maxHeight = Constraints.Infinity
        )

        // 追踪每一列的当前高度（Y 坐标）
        val columnHeights = IntArray(columnCount)


        // 存储所有已测量的子元素 (Placeable)
        val placeables = measurables.mapIndexed { index, measurable ->
            // 找到当前高度最小的列，也可以自己计算
//            val columnIndex = columnHeights.indices.minByOrNull { columnHeights[it] } ?: 0
            val columnIndex = index % columnCount

            // 测量子元素
            val placeable = measurable.measure(itemConstraints)

            // 更新该列的高度（累积高度）
            columnHeights[columnIndex] += placeable.height

            // 将列索引和 Placeable 捆绑，供稍后放置
            Pair(columnIndex, placeable)
        }

        // 确定布局的整体高度
        val height = columnHeights.maxOrNull()?.coerceIn(constraints.minHeight, constraints.maxHeight)
            ?: constraints.minHeight

        // 放置子元素
        layout(constraints.maxWidth, height) {
            // 重新追踪每一列的当前 Y 坐标，用于放置
            val columnY = IntArray(columnCount)

            placeables.forEach { (columnIndex, placeable) ->
                // 计算 X 坐标：列索引 * 列宽
                val x = columnIndex * columnWidth

                // 计算 Y 坐标：该列当前的累积 Y 高度
                val y = columnY[columnIndex]

                // 放置子元素
                placeable.placeRelative(x = x, y = y)

                // 更新该列的 Y 坐标
                columnY[columnIndex] += placeable.height
            }
        }
    }
}

@Composable
private fun Item(modifier: Modifier = Modifier, text: String) {
    Card(
        border = BorderStroke(
            width = Dp.Hairline,
            color = Color.Black,
        ),
        shape = RoundedCornerShape(6.dp),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(Color.Magenta)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = text)
        }
    }
}

val fruits = listOf(
    "Apple", "Banana", "Orange", "Grape", "Mango",
    "Strawberry", "Pineapple", "Watermelon", "Peach",
    "Pear", "Kiwi", "Blueberry", "Raspberry", "Blackberry",
    "Cherry", "Plum"
)
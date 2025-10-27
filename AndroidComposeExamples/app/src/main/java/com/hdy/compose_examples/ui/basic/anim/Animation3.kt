package com.hdy.compose_examples.ui.basic.anim

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// 内容大小动画: modifier
//
// 添加animateContentSize()后更平滑

@Composable
fun Animation3(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ContentSizeAnimationExample()
    }
}

@Composable
fun ContentSizeAnimationExample() {
    // 1. 控制文本长度状态
    var isExpanded by remember { mutableStateOf(false) }

    val longText = "Compose 提供了强大的动画 API，其中 animateContentSize 是一个非常实用的 Modifier，用于在内容尺寸发生变化时，自动为容器的尺寸变化添加平滑的动画效果，常用于实现文本展开/收起等场景。"
    val shortText = "点击以展开/收起更多文本..."

    Box(
        modifier = Modifier
            .padding(24.dp)
            .background(Color.LightGray)
            // 2. 关键：将此 Modifier 应用于父容器（Box）
            .animateContentSize()
            .clickable {
                // 3. 改变内部内容的状态，从而导致 Box 的内容尺寸变化
                isExpanded = !isExpanded
            }
    ) {
        Text(
            text = if (isExpanded) longText else shortText,
            modifier = Modifier.padding(12.dp)
        )
    }
}

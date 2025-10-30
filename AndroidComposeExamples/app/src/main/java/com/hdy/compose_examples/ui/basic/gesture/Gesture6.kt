package com.hdy.compose_examples.ui.basic.gesture

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// 滑动
// 与 draggable 修饰符不同的是，
// swipeable 修饰符允许开发者通过锚点设置从而实现组件呈现吸附效果的动画，
// 常用于开关等动画，也可用于下拉刷新等特殊效果的实现。

@Composable
fun Gesture6(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .height(200.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SimpleSwipeToDismiss()
    }
}

@Composable
fun SimpleSwipeToDismiss() {
    // 1. 创建滑动状态
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { dismissValue ->
            // 只接受从右到左的滑动（EndToStart），即左滑删除
            dismissValue == SwipeToDismissBoxValue.EndToStart
        }
    )

    // 2. 监听状态变化，执行实际操作并重置
    LaunchedEffect(dismissState.currentValue) {
        if (dismissState.currentValue == SwipeToDismissBoxValue.EndToStart) {
            // 在这里执行删除/归档操作
            println("✅ 动作已确认：Item 已被逻辑删除 (Swiped EndToStart)")

            // 重要：重置状态，让卡片弹回初始位置
            // 如果是在 LazyColumn 中，你会在外部列表状态中真正移除该 item
            dismissState.reset()
        }
    }

    // 3. 构建 SwipeToDismissBox
    SwipeToDismissBox(
        state = dismissState,
        modifier = Modifier
            .padding(16.dp)
            .height(70.dp),

        // 4. 背景内容 (滑动时显示的内容)
        backgroundContent = {
            // 根据滑动状态，动画颜色从灰色变为红色
            val color by animateColorAsState(
                targetValue = when (dismissState.targetValue) {
                    SwipeToDismissBoxValue.EndToStart -> Color.Red.copy(alpha = 0.8f) // 目标是删除，显示红色
                    else -> Color.LightGray
                },
                label = "DismissBackgroundAnimation"
            )

            Box(
                Modifier
                    .fillMaxSize()
                    .background(color),
                contentAlignment = Alignment.CenterEnd // 图标靠右显示
            ) {
                // 显示删除图标
                Icon(
                    Icons.Filled.Delete,
                    contentDescription = "Delete",
                    tint = Color.White,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }
        },

        // 5. 前景内容 (被滑动的主卡片)
        content = {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = "向左滑动删除 (Swipe Left to Delete)",
                        modifier = Modifier.padding(horizontal = 16.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        },

        // 6. 指定可滑动的方向（只允许从右到左）
        enableDismissFromStartToEnd = false // 禁用向右滑动
    )
}

package com.hdy.compose_examples.ui.basic.anim

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// 过渡动画/组合动画/同步动画
// 多个属性同时变化

@Composable
fun Animation4(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TransitionAnimationExample()
    }
}

// 1. 定义状态
enum class BoxState {
    Collapsed, // 收缩状态
    Expanded   // 展开状态
}

@Composable
fun TransitionAnimationExample() {
    // 2. 创建一个控制过渡的 State 变量
    var currentState by remember { mutableStateOf(BoxState.Collapsed) }

    // 3. 创建 Transition 实例，并将其 targetState 设置为 currentState
    val transition = updateTransition(
        targetState = currentState,
        label = "BoxStateTransition"
    )

    // 4. 定义动画规范（可选，这里使用 tween）
    val animationSpec = tween<Color>(durationMillis = 500) // 颜色动画时间
    val sizeAnimationSpec = tween<Dp>(durationMillis = 500) // 尺寸动画时间

    // 5. 在 Transition 中定义各个属性的动画值

    // 动画背景颜色
    val animatedColor by transition.animateColor(
        transitionSpec = { animationSpec },
        label = "BackgroundColor"
    ) { state ->
        when (state) {
            BoxState.Collapsed -> Color.Blue
            BoxState.Expanded -> Color.Red
        }
    }

    // 动画角标尺寸（使用 Dp）
    val animatedPadding by transition.animateDp(
        targetValueByState = { state ->
            when (state) {
                BoxState.Collapsed -> 10.dp
                BoxState.Expanded -> 30.dp // 展开时内边距变大
            }
        },
        transitionSpec = { sizeAnimationSpec },
        label = "PaddingSize"
    )

    // 6. 组合 UI 并应用动画值
    Box(
        modifier = Modifier
            .padding(24.dp)
            .background(animatedColor, RoundedCornerShape(8.dp))
            .clickable {
                // 7. 改变状态以触发过渡动画
                currentState = if (currentState == BoxState.Collapsed) {
                    BoxState.Expanded
                } else {
                    BoxState.Collapsed
                }
            }
            .padding(animatedPadding) // 应用动画 Dp 值
            .size(100.dp), // 保证大小一致，动画主要体现在 padding 上
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "State: ${currentState.name}",
            color = Color.White
        )
    }
}
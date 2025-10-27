package com.hdy.compose_examples.ui.basic.anim

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


// 动画规范:
// 所有Animation API 通常都接受一个 AnimationSpec 参数来定制动画的行为：
// spring： 基于物理特性的动画，默认类型，感觉更自然，可以中断。
// tween： 基于时长的动画，使用 Easing 函数来控制速度曲线（如 FastOutSlowInEasing）。
// keyframes： 允许你在动画的不同时间点（关键帧）指定特定的值。
// repeatable / infiniteRepeatable： 用于重复执行动画。
// snap： 立即跳到最终值，没有动画过程。

// 基于值的动画

// 用于对单个值（如颜色、大小、透明度等）的变化添加动画效果。
//
// animate*AsState 函数族：
// 这是最简单直接的 API，用于基于状态（State）的变化对单个值进行动画处理。例如：
// animateFloatAsState：动画 Float 值。
// animateColorAsState：动画 Color 值。
// animateDpAsState：动画 Dp 值。
// animateOffsetAsState：动画 Offset 值等。
//
// Animatable：
// 一个更底层的、基于协程的单值动画 API，允许你更精细地控制动画的启动和停止（例如使用 animateTo 或 snapTo）。

@Composable
fun Animation1(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ColorAnimationExample()
        Spacer(modifier = modifier.height(6.dp))
        AlphaAnimationExample()
        Spacer(modifier = modifier.height(6.dp))
        PaddingAnimationExample()
    }
}

@Composable
fun ColorAnimationExample(modifier: Modifier = Modifier) {
    // 创建一个控制动画状态的 State 变量
    var isRed by remember { mutableStateOf(true) }
    // 使用 animateColorAsState 来计算动画过程中的颜色值
    // 当 isRed 变化时，颜色会自动在 Color.Red 和 Color.Blue 之间过渡
    val animatedColor by animateColorAsState(
        targetValue = if (isRed) Color.Red else Color.Blue,
        label = "ColorAnimation" // 推荐提供 label
    )
    Box(
        modifier = Modifier
            .size(60.dp)
            // 将动画值应用到背景色
            .background(animatedColor)
            // 点击 Box 切换 isRed 的状态，从而触发动画
            .clickable {
                isRed = !isRed
            }
    )
}

// 注意:
// alpha(animatedAlpha)要在background(Color.Red, CircleShape)之前，否则不生效
@Composable
fun AlphaAnimationExample() {
    var visible by remember {
        mutableStateOf(true)
    }
    val animatedAlpha by animateFloatAsState(
        targetValue = if (visible) 1.0f else 0.1f,
        label = "alpha"
    )

    Box(
        modifier = Modifier
            .size(100.dp)
            .alpha(animatedAlpha)
            .background(Color.Red, CircleShape)
            .clickable {
                visible = !visible
            }
    )
}

@Composable
fun PaddingAnimationExample() {
    var toggled by remember { mutableStateOf(false) }

    // 动画 Dp 值
    val animatedPadding by animateDpAsState(
        targetValue = if (toggled) 0.dp else 20.dp,
        label = "PaddingAnimation"
    )

    Box(
        modifier = Modifier
            .size(150.dp)
            .background(Color.Cyan)
            // 应用动画后的内边距
            .padding(animatedPadding)
            .background(Color.Magenta) // 这个背景会显示在内边距的“里面”
            .clickable {
                toggled = !toggled
            }
    )
}

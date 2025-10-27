package com.hdy.compose_examples.ui.basic.anim

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

// 重复动画
//
// rememberInfiniteTransition：
// 用于创建无限重复的动画，例如加载指示器。

@Composable
fun Animation5(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        InfinitePulseAnimationExample()
    }
}

@Composable
fun InfinitePulseAnimationExample() {
    // 1. 创建一个无限过渡实例
    val infiniteTransition = rememberInfiniteTransition(label = "PulsingTransition")

    // 2. 使用 animateFloat 定义一个在 0.8f 和 1.2f 之间循环的浮点值
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.8f, // 初始值 (80% 尺寸)
        targetValue = 1.2f,  // 目标值 (120% 尺寸)
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000, // 每次循环持续 1 秒
                easing = FastOutSlowInEasing // 速度曲线
            ),
            repeatMode = RepeatMode.Reverse // 播放完 0.8 -> 1.2 后，反向播放 1.2 -> 0.8
        ),
        label = "ScaleAnimation"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        // 3. 将动画值应用到图形层
        Box(
            modifier = Modifier
                .size(100.dp)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                }
                .background(Color.Magenta, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text("Pulse", color = Color.White)
        }

        Spacer(modifier = Modifier.height(32.dp))
        Text("我正在无限循环...")
    }
}
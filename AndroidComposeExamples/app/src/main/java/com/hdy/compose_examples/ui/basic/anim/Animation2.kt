package com.hdy.compose_examples.ui.basic.anim

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
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
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.hdy.compose_examples.utils.Log

// 可见性动画

@Composable
fun Animation2(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SimpleVisibilityAnimation()
        Spacer(modifier = modifier.height(6.dp))
        ContentSwitchAnimation()
    }
}

@Composable
fun SimpleVisibilityAnimation() {
    // 1. 控制可见性的状态
    var isVisible by remember { mutableStateOf(false) }

    Column {
        // 点击此文本来切换 isVisible 的状态
        Text(
            text = if (isVisible) "点击我隐藏" else "点击我显示",
            modifier = Modifier.clickable {
                isVisible = !isVisible
            }
        )

        // 2. 使用 AnimatedVisibility 包裹需要动画的 Composable
        AnimatedVisibility(
            visible = isVisible,
            // 3. 可选：自定义进入和退出的过渡效果
            enter = fadeIn() + slideInHorizontally(),
            exit = fadeOut() + slideOutHorizontally()
        ) {
            // 这是要显示或隐藏的内容
            Text(text = "我是有动画效果的文本！")
        }
    }
}

@Composable
fun ContentSwitchAnimation() {
    // 1. 控制切换状态
    var pageState by remember { mutableIntStateOf(0) }

    // 2. 使用 AnimatedContent，当 pageState 变化时，内容会过渡
    AnimatedContent(
        targetState = pageState,
        label = "ContentSwitch",
        // 3. 自定义过渡：旧内容淡出/新内容淡入
        transitionSpec = {
            fadeIn(animationSpec = tween(500)) togetherWith fadeOut(animationSpec = tween(500))
        }
    ) { targetPage ->
        // 4. 根据当前状态渲染不同的内容
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(16.dp).clickable {
                pageState = (pageState + 1) % 2 // 在 0 和 1 之间切换
            }
        ) {
            when (targetPage) {
                0 -> Text("当前是页面 0，点击切换")
                1 -> Text("当前是页面 1，点击切换回去")
            }
        }
    }
}

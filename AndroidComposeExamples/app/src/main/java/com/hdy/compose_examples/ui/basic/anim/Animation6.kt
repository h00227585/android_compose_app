package com.hdy.compose_examples.ui.basic.anim

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

// 手势动画
//
// 拖动时随着手势的位置发生变动，拖动结束回到原点

@Composable
fun Animation6(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GestureAnimationExample()
    }
}

@Composable
fun GestureAnimationExample() {
    val offsetX = remember { Animatable(0f) }
    val offsetY = remember { Animatable(0f) }

    // 与 Compose 生命周期绑定的协程作用域
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDrag = { change, dragAmount ->
                        change.consume()
                        // 在协程中安全调用挂起函数
                        coroutineScope.launch {
                            offsetX.snapTo(offsetX.value + dragAmount.x)
                            offsetY.snapTo(offsetY.value + dragAmount.y)
                        }
                    },
                    onDragEnd = {
                        coroutineScope.launch {
                            offsetX.animateTo(0f, animationSpec = tween(600))
                            offsetY.animateTo(0f, animationSpec = tween(600))
                        }
                    }
                )
            }
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .offset {
                    IntOffset(offsetX.value.roundToInt(), offsetY.value.roundToInt())
                }
                .background(MaterialTheme.colorScheme.primary, CircleShape)
        )
    }
}

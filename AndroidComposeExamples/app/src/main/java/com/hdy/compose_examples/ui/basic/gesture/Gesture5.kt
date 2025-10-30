package com.hdy.compose_examples.ui.basic.gesture

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

// 拖动
// 由于Modifer链式执行，offset必需在draggable与background前面。

@Composable
fun Gesture5(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .height(200.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DragSample()
    }
}

@Composable
fun DragSample() {
    var offsetX by remember { mutableFloatStateOf(0f) }

    // 用于存储父容器和子组件的宽度
    var parentWidth by remember { mutableIntStateOf(0) }
    var childWidth by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxWidth() // 确保 Column 占满宽度
            .onSizeChanged {
                parentWidth = it.width
            }
            .background(Color.LightGray) // (可选)给父容器一个背景色，方便观察
    ) {
        Text(
            modifier = Modifier
            .onSizeChanged {
                childWidth = it.width
            }
            .offset { IntOffset(offsetX.roundToInt(), 0)}
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    offsetX += delta
                    offsetX = offsetX.coerceIn(0f, (parentWidth - childWidth).toFloat())
                }
            )
            .background(Color.DarkGray),
            text = "水平拖动",
            color = Color.White
        )
    }

    var x by remember { mutableFloatStateOf(0f) }
    var y by remember { mutableFloatStateOf(0f) }

    Text(
        modifier = Modifier
            .offset { IntOffset(x.roundToInt(), y.roundToInt())}
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    x += dragAmount.x
                    y += dragAmount.y
                }
            },
        text = "任意拖动",
        color = Color.Red
    )
}

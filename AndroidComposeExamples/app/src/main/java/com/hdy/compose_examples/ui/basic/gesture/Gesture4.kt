package com.hdy.compose_examples.ui.basic.gesture

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

// 嵌套滚动: 子组件不能滚动时父组件再滚动

@Composable
fun Gesture4(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .height(200.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NestedScrollSample()
    }
}

@Composable
fun NestedScrollSample() {
    val brushGradient = Brush.verticalGradient(
        0f to Color.Gray, 1000f to Color.White
    )

    Column(modifier = Modifier
        .background(color = Color.LightGray)
        .verticalScroll(rememberScrollState())
    ) {
        repeat(6) {
            Box(
                modifier = Modifier
                    .padding(6.dp)  // 内边距
                    .height(64.dp)
            ) {
                Text(
                    text = "Scroll here",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .height(56.dp)
                        .border(width = 2.dp, color = Color.DarkGray)
                        .background(brushGradient)
                )
            }
        }
    }
}
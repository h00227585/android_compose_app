package com.hdy.compose_examples.ui.basic.gesture

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


// 滚动

@Composable
fun Gesture3(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ScrollDemo()
    }
}

@Composable
fun ScrollDemo() {
    val state = rememberScrollState()

    LaunchedEffect(Unit) {
        // 初始化
        state.animateScrollTo(100)
    }

    Column(
        modifier = Modifier
            .size(100.dp)
            .verticalScroll(state)
    ) {
        repeat(6) {
            Text("item $it")
        }
    }
}
package com.hdy.compose_examples.ui.basic.side_effect

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay

// SideEffect

@Composable
fun SideEffect5() {
    // 让 count 成为可观察状态
    var count by remember { mutableIntStateOf(1) }

    // 使用 LaunchedEffect 启动一个协程定时更新
    LaunchedEffect(Unit) {
        for (i in 1..10) {
            count = i
            delay(1000)
        }
    }

    LoggingCounter(count)
}

@Composable
fun LoggingCounter(count: Int) {
    SideEffect {
        // 如果外部变量需要跟踪count的值，可以在此处赋值
        println("UI 已更新为 count=$count")
    }

    Text("计数: $count")
}

package com.hdy.compose_examples.ui.basic.side_effect

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier

// snapshotFlow

@Composable
fun SideEffect8() {
    SnapshotFlowCounter()
}

@Composable
fun SnapshotFlowCounter() {
    var count by remember { mutableIntStateOf(0) }

    // 使用 snapshotFlow 监听 count 变化
    LaunchedEffect(Unit) {
        snapshotFlow { count }
            .collect { value ->
                println("count 改变为 $value, ${value::class.simpleName}")
            }
    }

    Button(onClick = { count++ }) {
        Text("点击：$count")
    }
}
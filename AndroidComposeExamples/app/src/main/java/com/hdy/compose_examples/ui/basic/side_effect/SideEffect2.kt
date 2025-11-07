package com.hdy.compose_examples.ui.basic.side_effect

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

@Composable
fun SideEffect2(
    modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()

    Button(onClick = {
        scope.launch {
            println("执行异步任务中...")
        }
    }) {
        Text("加载")
    }
}

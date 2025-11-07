package com.hdy.compose_examples.ui.basic.side_effect

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier

@Composable
fun SideEffect1(
    userId: Int,
    modifier: Modifier = Modifier) {
    LaunchedEffect(userId) {
        println("加载用户资料: $userId")
        // 假设进行网络请求或动画
    }

    Text("用户ID: $userId")
}

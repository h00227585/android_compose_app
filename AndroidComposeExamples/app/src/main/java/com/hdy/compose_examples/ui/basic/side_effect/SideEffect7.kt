package com.hdy.compose_examples.ui.basic.side_effect

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

// derivedStateOf

@Composable
fun SideEffect7() {
    LoginForm()
}

@Composable
fun LoginForm() {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val isValid by remember {
        derivedStateOf {
            username.isNotBlank() && password.length >= 6
        }
    }

    Column {
        TextField(value = username, onValueChange = { username = it }, label = { Text("用户名") })
        TextField(value = password, onValueChange = { password = it }, label = { Text("密码") })
        // isValid 依赖两个输入值。
        // Compose 会在任一输入变化时重新计算。
        // 若计算结果相同（例如输入空格），不会重复重组按钮部分。
        Button(onClick = { /* 登录 */ }, enabled = isValid) {
            Text("登录")
        }
    }
}

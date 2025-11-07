package com.hdy.compose_examples.ui.basic.side_effect

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import kotlinx.coroutines.delay

// produceState

@Composable
fun SideEffect6() {
    UserProfile("9527")
}

@Composable
fun UserProfile(userId: String) {
    // 使用 produceState 创建异步状态
    val uiState by produceState<UiState<User>>(initialValue = UiState.Loading, userId) {
        try {
            // 模拟网络请求
            delay(2000)
            val user = fetchUser(userId)
            value = UiState.Success(user)
        } catch (e: Exception) {
            value = UiState.Error(e.message ?: "未知错误")
        }
    }

    // 根据状态展示不同界面
    when (uiState) {
        is UiState.Loading -> Text("正在加载用户信息…")
        is UiState.Success -> {
            val user = (uiState as UiState.Success<User>).data
            Text("用户名：${user.name}\n邮箱：${user.email}")
        }
        is UiState.Error -> {
            val message = (uiState as UiState.Error).message
            Text("加载失败：$message")
        }
    }
}

// 模拟数据模型
data class User(val name: String, val email: String)

// 模拟 suspend 网络请求函数
suspend fun fetchUser(userId: String): User {
    // 模拟网络成功或失败
    if (userId == "error") throw Exception("用户不存在")
    return User(name = "User $userId", email = "user$userId@example.com")
}

// UI 状态封装
sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}
package com.hdy.compose_examples.data.local.staticdata.model

data class UserProfile(
    val name: String = "张三",
    val role: String = "高级开发工程师",
    val email: String = "zhangsan@example.com",
    val phone: String = "138-0000-0000",
    val isExpanded: Boolean = false,
    val isFavorite: Boolean = false
)
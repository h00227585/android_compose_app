package com.hdy.compose_examples.data.model

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.hdy.compose_examples.R

sealed class IconSource {
    data class Vector(val imageVector: ImageVector) : IconSource()
    data class Resource(@DrawableRes val resId: Int) : IconSource()
}

data class Example(
    val id: String,
    val title: String,
    val description: String,
    val icon: IconSource,
    val route: String
)

object ExampleData {
    val examples = listOf(
        Example(
            id = "task_list",
            title = "任务列表",
            description = "动态列表管理，展示添加、删除和状态管理",
            icon = IconSource.Vector(Icons.AutoMirrored.Filled.List),
            route = "task_list"
        ),
        Example(
            id = "user_profile",
            title = "用户卡片",
            description = "可展开的用户信息卡片，展示动画和交互",
            icon = IconSource.Vector(Icons.Filled.Person),
            route = "user_profile"
        ),
        Example(
            id = "hexagon",
            title = "六边形",
            description = "多个六边形",
            icon = IconSource.Resource(R.drawable.ic_hexagon),
            route = "hexagon"
        )
    )
}

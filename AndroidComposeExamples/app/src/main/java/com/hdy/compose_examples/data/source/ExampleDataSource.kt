package com.hdy.compose_examples.data.source

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Person
import com.hdy.compose_examples.R
import com.hdy.compose_examples.data.model.Example
import com.hdy.compose_examples.data.model.IconSource


object ExampleDataSource {
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
        ),
        Example(
            id = "survey",
            title = "调查问卷",
            description = "调查问卷",
            icon = IconSource.Resource(R.drawable.ic_mood),
            route = "survey"
        ),
        Example(
            id = "image_gallery",
            title = "本地图片",
            description = "本地图片",
            icon = IconSource.Resource(R.drawable.ic_mood),
            route = "image_gallery"
        ),
        Example(
            id = "affirmations",
            title = "自我鼓励的话",
            description = "官方示例",
            icon = IconSource.Resource(R.drawable.ic_mood),
            route = "affirmations"
        ),
        Example(
            id = "topics",
            title = "话题网格",
            description = "官方示例",
            icon = IconSource.Resource(R.drawable.ic_mood),
            route = "topics"
        ),
        Example(
            id = "dice_roller",
            title = "掷骰子",
            description = "官方示例",
            icon = IconSource.Resource(R.drawable.ic_mood),
            route = "dice_roller"
        ),
    )
}
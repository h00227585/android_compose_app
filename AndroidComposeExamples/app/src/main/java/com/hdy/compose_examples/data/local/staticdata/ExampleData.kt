package com.hdy.compose_examples.data.local.staticdata

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Person
import com.hdy.compose_examples.R
import com.hdy.compose_examples.data.local.staticdata.model.Example
import com.hdy.compose_examples.data.model.IconSource

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

        // 官方示例
        // 基本布局
        Example(
            id = "birthday_card",
            title = "生日贺卡",
            description = "官方示例",
            icon = IconSource.Resource(R.drawable.ic_mood),
            route = "birthday_card"
        ),
        Example(
            id = "business_card",
            title = "名片",
            description = "官方示例",
            icon = IconSource.Resource(R.drawable.ic_mood),
            route = "business_card"
        ),
        // 响应式UI：点击按钮
        Example(
            id = "dice_roller",
            title = "掷骰子",
            description = "官方示例",
            icon = IconSource.Resource(R.drawable.ic_mood),
            route = "dice_roller"
        ),
        // compose 状态
        Example(
            id = "tip_calculator",
            title = "小费计算器",
            description = "官方示例",
            icon = IconSource.Resource(R.drawable.ic_mood),
            route = "tip_calculator"
        ),
        Example(
            id = "art_space",
            title = "艺术空间",
            description = "官方示例",
            icon = IconSource.Resource(R.drawable.ic_mood),
            route = "art_space"
        ),
        // 可滚动列表
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
        // Material
        Example(
            id = "material",
            title = "Material",
            description = "官方示例",
            icon = IconSource.Resource(R.drawable.ic_mood),
            route = "material"
        ),
        // Activity生命周期
        Example(
            id = "dessert",
            title = "甜点",
            description = "官方示例, Activity 生命周期",
            icon = IconSource.Resource(R.drawable.ic_mood),
            route = "dessert"
        ),
        // ViewModel
        Example(
            id = "guess_word",
            title = "猜单词",
            description = "官方示例, ViewModel",
            icon = IconSource.Resource(R.drawable.ic_mood),
            route = "guess_word"
        ),
        // 导航
        Example(
            id = "cupcake",
            title = "Cupcake",
            description = "官方示例, Navigation",
            icon = IconSource.Resource(R.drawable.ic_mood),
            route = "cupcake"
        ),
        // 动态导航，自适应屏幕
        Example(
            id = "reply",
            title = "Reply",
            description = "官方示例, 动态导航+自适应屏幕",
            icon = IconSource.Resource(R.drawable.ic_mood),
            route = "reply"
        ),
        // 协程
        Example(
            id = "race_tracker",
            title = "RaceTracker",
            description = "官方示例, 协程",
            icon = IconSource.Resource(R.drawable.ic_mood),
            route = "race_tracker"
        ),
        // 协程+网络
        Example(
            id = "mars_photos",
            title = "MarsPhotos",
            description = "官方示例, 协程+网络",
            icon = IconSource.Resource(R.drawable.ic_mood),
            route = "mars_photos"
        ),
        // Room
        Example(
            id = "inventory",
            title = "商品",
            description = "官方示例, Room+手动依赖注入",
            icon = IconSource.Resource(R.drawable.ic_mood),
            route = "inventory"
        ),
        // datastore-preferences
        Example(
            id = "dessert_release",
            title = "Android Dessert Release",
            description = "官方示例, DataStore-Preferences",
            icon = IconSource.Resource(R.drawable.ic_mood),
            route = "dessert_release"
        ),
    )
}
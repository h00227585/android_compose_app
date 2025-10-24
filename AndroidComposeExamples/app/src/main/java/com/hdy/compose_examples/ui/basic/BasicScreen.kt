package com.hdy.compose_examples.ui.basic

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicScreen(
    onBackClick: () -> Unit = {}
) {
    var homeSelected by remember { mutableStateOf(false) }
    var searchSelected by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),

        // 标题栏
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "标题栏",
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { onBackClick() }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "返回"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { /* TODO: Handle action */ }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.FavoriteBorder,
                            contentDescription = "收藏"
                        )
                    }
                    IconButton(
                        onClick = { /* TODO: Handle action */ }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "搜索"
                        )
                    }
                    IconButton(
                        onClick = { /* TODO: Handle action */ }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "设置"
                        )
                    }
                }
            )
        },

        // 底部导航栏
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(
                        onClick = { },
                        enabled = homeSelected
                    ) {
                        Icon(Icons.Default.Home, "首页")
                    }
                    Spacer(Modifier.weight(1f))
                    IconButton(
                        onClick = { },
                        enabled = searchSelected
                    ) {
                        Icon(Icons.Default.Search, "搜索")
                    }
                },
            )
        },

        // 悬浮按钮
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    homeSelected = !homeSelected
                    searchSelected = !searchSelected
                },
                modifier = Modifier.padding(end = 16.dp, bottom = 16.dp), // 调整位置
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                shape = CircleShape
            ) {
                Icon(Icons.Default.Add, "添加")
            }
        },
        floatingActionButtonPosition = FabPosition.End, // 设置FAB在右侧

        // 内容区域
        content = { innerPadding ->
            BodyContent(modifier = Modifier.padding(innerPadding).fillMaxSize())
        }
    )
}

// note:
// 如果parent的高度不固定，child的高度必须固定，否则会crash
@Composable
private fun BodyContent(modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        // Intrinsics
        item {
            HorizontalDivider(
                thickness = 2.dp,
                color = (MaterialTheme.colorScheme.primary))
            Intrinsics1()
        }

        item {
            HorizontalDivider(
                thickness = 2.dp,
                color = (MaterialTheme.colorScheme.primary))
            Intrinsics2()
        }

        // 约束布局
        item {
            HorizontalDivider(
                thickness = 2.dp,
                color = (MaterialTheme.colorScheme.primary))
            ConstraintLayout1()
        }
        item {
            HorizontalDivider(
                thickness = 2.dp,
                color = (MaterialTheme.colorScheme.primary))
            ConstraintLayout2()
        }
        item {
            HorizontalDivider(
                thickness = 2.dp,
                color = (MaterialTheme.colorScheme.primary))
            ConstraintLayout3()
        }
        item {
            HorizontalDivider(
                thickness = 2.dp,
                color = (MaterialTheme.colorScheme.primary))
            ConstraintLayout4()
        }

        // 自定义布局
        item {
            HorizontalDivider(
                thickness = 2.dp,
                color = (MaterialTheme.colorScheme.primary))
            CustomLayout1()
        }
        item {
            HorizontalDivider(
                thickness = 2.dp,
                color = (MaterialTheme.colorScheme.primary))
            CustomLayout2()
        }
        item {
            HorizontalDivider(
                thickness = 2.dp,
                color = (MaterialTheme.colorScheme.primary))
            CustomLayout3()
        }

        // list
        item {
            HorizontalDivider(
                thickness = 2.dp,
                color = (MaterialTheme.colorScheme.primary))
            List1(Modifier
                .height(320.dp)
                .padding(6.dp)
                .border(width = 2.dp, color = MaterialTheme.colorScheme.secondary))
        }
        item {
            HorizontalDivider(
                thickness = 2.dp,
                color = (MaterialTheme.colorScheme.primary))
            List2(Modifier
                .height(320.dp)
                .padding(6.dp)
                .border(width = 2.dp, color = MaterialTheme.colorScheme.secondary))
        }
        item {
            HorizontalDivider(
                thickness = 2.dp,
                color = (MaterialTheme.colorScheme.primary))
            List3(Modifier
                .height(320.dp)
                .padding(6.dp)
                .border(width = 2.dp, color = MaterialTheme.colorScheme.secondary))
        }

        // 文本
        item {
            HorizontalDivider(
                thickness = 2.dp,
                color = (MaterialTheme.colorScheme.primary))
            TextDemo()
        }
    }
}


@Composable
fun TextDemo(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(12.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Line 1",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Line 2",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}
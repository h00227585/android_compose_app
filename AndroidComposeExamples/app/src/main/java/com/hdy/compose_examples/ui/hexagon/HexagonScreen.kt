package com.hdy.compose_examples.ui.hexagon

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hdy.compose_examples.ui.components.RoundedHexagonShape
import kotlin.math.sqrt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HexagonScreen(
    onBackClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("六边形") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "返回")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        content = { paddingValues ->
            HexagonList(
                count = 20,
                size = 100.dp,
                cornerRadius = 2.dp,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) { index ->
                Text(
                    "Item $index",
                    color = Color.White
                )
            }
        }
    )
}

@Composable
fun HexagonList(
    count: Int,
    size: Dp,                // 六边形边长
    cornerRadius: Dp,        // 圆角
    modifier: Modifier = Modifier,
    content: @Composable (Int) -> Unit
) {
    val hexHeight = (sqrt(3f) * size.value).dp
    val overlap = hexHeight / 2   // 控制上下交错的垂直步长

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(-overlap) // 关键：负间距
    ) {
        items(count) { index ->
            Hexagon(
                index = index,
                size = size,
                cornerRadius = cornerRadius,
                rotation = 0f,
                content = { content(index) }
            )
        }
    }
}

@Composable
fun Hexagon(
    index: Int,
    size: Dp,
    cornerRadius: Dp,
    rotation: Float = 0f, // 旋转角度
    content: @Composable () -> Unit
) {
    val cornerPx = with(LocalDensity.current) { cornerRadius.toPx() } // ✅ 转换
    val shape = RoundedHexagonShape(cornerPx, rotation)

    val xShift = if (index % 2 == 0) 0.dp else size * 1.5f

    val hexWidth = size * 2
    val hexHeight = (sqrt(3f) * size.value).dp

    Box(
        modifier = Modifier
            .offset(x = xShift)
            .size(width = hexWidth, height = hexHeight)
            .clip(shape)
            .background(
                if (index % 2 == 0) Color(0xFFFF5722) else Color(0xFF673AB7)
            ),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}
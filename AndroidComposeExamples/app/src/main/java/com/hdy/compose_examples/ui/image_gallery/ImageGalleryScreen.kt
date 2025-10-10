package com.hdy.compose_examples.ui.image_gallery

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.hdy.compose_examples.R
import com.hdy.compose_examples.core.permission.PermissionManager
import com.hdy.compose_examples.core.permission.PermissionRequestScreen
import com.hdy.compose_examples.core.permission.rememberPermissionState
import com.hdy.compose_examples.data.repository.ImageRepository
import com.hdy.compose_examples.ui.components.EmptyContent
import com.hdy.compose_examples.ui.components.ErrorContent
import com.hdy.compose_examples.ui.components.ImageGrid
import com.hdy.compose_examples.utils.Log

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageGalleryScreen(
    viewModel: ImageGalleryViewModel = viewModel(
        factory = ImageGalleryViewModelFactory(
            ImageRepository(LocalContext.current.applicationContext)
        )
    ),
    onBackClick: () -> Unit
) {
    val imagePermissions = PermissionManager.getImagePermissions()
    val permissionState = rememberPermissionState(
        permission = imagePermissions.first()
    )

    val lazyPagingItems = viewModel.images.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("本地图片") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "返回"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )

            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (permissionState.hasPermission) {
                Log.d("ImageGalleryScreen", "有权限，显示内容")
                when {
                    lazyPagingItems.loadState.refresh is LoadState.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    lazyPagingItems.loadState.refresh is LoadState.Error -> {
                        ErrorContent(
                            message = "加载失败，请重试",
                            onRetry = { lazyPagingItems.retry() },
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    lazyPagingItems.itemCount == 0 -> {
                        EmptyContent(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    else -> {
                        ImageGrid(
                            lazyPagingItems = lazyPagingItems
                        )
                    }
                }
            } else {
                Log.d("ImageGalleryScreen", "无权限，显示请求界面")
                PermissionRequestScreen(
                    title = "需要访问相册",
                    description = "为了展示您设备中的图片，我们需要访问相册的权限",
                    painter = painterResource(R.drawable.ic_photo_library),
                    shouldShowRationale = permissionState.shouldShowRationale,
                    onRequestPermission = permissionState.requestPermission
                )
            }
        }
    }
}
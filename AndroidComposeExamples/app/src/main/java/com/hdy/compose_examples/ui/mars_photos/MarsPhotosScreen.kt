package com.hdy.compose_examples.ui.mars_photos

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.hdy.compose_examples.R
import com.hdy.compose_examples.data.model.MarsPhoto
import com.hdy.compose_examples.data.remote.MarsApiService
import com.hdy.compose_examples.data.repository.LocalMarsPhotosRepository
import com.hdy.compose_examples.data.repository.NetworkMarsPhotosRepository
import com.hdy.compose_examples.data.source.LocalMarsPhotosDataSource
import com.hdy.compose_examples.domain.repository.MarsPhotosRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarsPhotosScreen() {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { MarsTopAppBar(scrollBehavior = scrollBehavior) }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            val useLocalMarsDataSource = true
            val marsPhotosRepository: MarsPhotosRepository = if (useLocalMarsDataSource) {
                // 本地数据源
                // 必须使用 applicationContext，并且放在 remember 中
                val context = LocalContext.current.applicationContext
                remember {
                    val localDataSource = LocalMarsPhotosDataSource(
                        context = context,
                        jsonFileName = "mars_photos.json"
                    )
                    LocalMarsPhotosRepository(localDataSource)
                }
            } else {
                // 网络数据源
                remember {
                    val baseUrl = "https://android-kotlin-fun-mars-server.appspot.com/"
                    // 创建 Retrofit 实例 (使用 remember)
                    val retrofit = Retrofit.Builder()
                        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
                        .baseUrl(baseUrl)
                        .build()
                    // 创建 Service 实例
                    val retrofitService = retrofit.create(MarsApiService::class.java)
                    // 创建 Repository 实例
                    NetworkMarsPhotosRepository(retrofitService)
                }
            }

            // Factory 只需要依赖于 Repository 即可，逻辑是通用的
            val viewModelFactory = remember {
                MarsViewModelFactory(marsPhotosRepository)
            }

            // 统一获取 ViewModel
            val marsViewModel: MarsViewModel = viewModel(factory = viewModelFactory)

            HomeScreen(
                marsUiState = marsViewModel.marsUiState,
                retryAction = marsViewModel::getMarsPhotos,
                contentPadding = it
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarsTopAppBar(scrollBehavior: TopAppBarScrollBehavior, modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = "火星图片",
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        modifier = modifier
    )
}

@Composable
private fun HomeScreen(
    marsUiState: MarsUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    when (marsUiState) {
        is MarsUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is MarsUiState.Success -> PhotosGridScreen(
            marsUiState.photos,
            contentPadding = contentPadding,
            modifier = modifier.fillMaxWidth()
        )
        is MarsUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxSize())
    }
}

/**
 * The home screen displaying the loading message.
 */
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.ic_loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

/**
 * The home screen displaying error message with re-attempt button.
 */
@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

/**
 * The home screen displaying photo grid.
 */
@Composable
fun PhotosGridScreen(
    photos: List<MarsPhoto>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    // 通过 LazyVerticalGrid，可以指定列表项的宽度，然后网格将适应尽可能多的列。
    // 在计算列数之后，网格会在各列之间平均分配所有剩余的宽度。
    // 这种自适应尺寸调整方式非常适合在不同尺寸的屏幕上显示多组列表项。
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = modifier.padding(horizontal = 4.dp),
        contentPadding = contentPadding,
    ) {
        // 当用户滚动网格（LazyColumn 中的 LazyRow）时，列表项的位置会发生变化。
        // 不过，由于屏幕方向发生变化或者添加或移除了项，用户可能会丢失该行中的滚动位置。
        // item key 可以帮助您根据键来保持滚动位置。
        // 通过提供item key，您可以帮助 Compose 正确处理重新排序。
        items(items = photos, key = { photo -> photo.id }) { photo ->
            MarsPhotoCard(
                photo,
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
                    .aspectRatio(1.5f)
            )
        }
    }
}

// AsyncImage 周围添加一个 elevation=8dp 的 card
@Composable
fun MarsPhotoCard(photo: MarsPhoto, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        // AsyncImage: 异步请求图片
        // crossfade(true): 请求成功完成时启用淡入淡出动画
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current).data(photo.imgSrc)
                .crossfade(true).build(),
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.ic_loading_img),
            contentDescription = stringResource(R.string.mars_photo),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

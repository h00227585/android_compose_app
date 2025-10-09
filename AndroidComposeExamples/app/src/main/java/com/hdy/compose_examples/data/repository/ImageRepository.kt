package com.hdy.compose_examples.data.repository

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hdy.compose_examples.data.model.LocalImage
import com.hdy.compose_examples.data.local.mediastore.LocalImagePagingSource
import kotlinx.coroutines.flow.Flow

// 数据仓库，封装数据访问逻辑
class ImageRepository(private val context: Context) {

    fun getImagesPagingFlow(): Flow<PagingData<LocalImage>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 5,
                enablePlaceholders = false,
                initialLoadSize = 40
            ),
            pagingSourceFactory = { LocalImagePagingSource(context) }
        ).flow
    }
}
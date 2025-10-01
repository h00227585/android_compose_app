package com.hdy.compose_examples.data.source

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hdy.compose_examples.data.model.LocalImage
import com.hdy.compose_examples.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


// Paging3 数据源，负责从 MediaStore 加载图片
class LocalImagePagingSource(
    private val context: Context
) : PagingSource<Int, LocalImage>() {

    private val TAG = LocalImagePagingSource::class.simpleName

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LocalImage> {
        Log.d(TAG, "load()")
        return withContext(Dispatchers.IO) {
            try {
                val offset = params.key ?: 0
                val limit = params.loadSize

                val images = loadImagesFromMediaStore(offset, limit)

                LoadResult.Page(
                    data = images,
                    prevKey = if (offset == 0) null else offset - limit,
                    nextKey = if (images.isEmpty()) null else offset + limit
                )
            } catch (e: Exception) {
                LoadResult.Error(e)
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, LocalImage>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(state.config.pageSize)
                ?: anchorPage?.nextKey?.minus(state.config.pageSize)
        }
    }

    private fun loadImagesFromMediaStore(offset: Int, limit: Int): List<LocalImage> {
        Log.d(TAG, "loadImagesFromMediaStore()")

        val images = mutableListOf<LocalImage>()
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATE_ADDED,
            MediaStore.Images.Media.SIZE
        )

        val cursor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Android 11+ 使用 Bundle 查询
            val bundle = Bundle().apply {
                putInt(ContentResolver.QUERY_ARG_LIMIT, limit)
                putInt(ContentResolver.QUERY_ARG_OFFSET, offset)
                putStringArray(
                    ContentResolver.QUERY_ARG_SORT_COLUMNS,
                    arrayOf(MediaStore.Images.Media.DATE_ADDED)
                )
                putInt(
                    ContentResolver.QUERY_ARG_SORT_DIRECTION,
                    ContentResolver.QUERY_SORT_DIRECTION_DESCENDING
                )
            }
            context.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                bundle,
                null
            )
        } else {
            // Android 10 及以下用 SQL LIMIT/OFFSET
            val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC LIMIT $limit OFFSET $offset"
            context.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                sortOrder
            )
        }

        cursor?.use {
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val nameColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
            val dateColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED)
            val sizeColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)

            while (it.moveToNext()) {
                val id = it.getLong(idColumn)
                val name = it.getString(nameColumn)
                val date = it.getLong(dateColumn) * 1000 // 注意：DATE_ADDED 是秒，需转毫秒
                val size = it.getLong(sizeColumn)
                val uri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id
                )
                images.add(
                    LocalImage(
                        id = id,
                        uri = uri.toString(),
                        displayName = name,
                        dateAdded = date,
                        size = size
                    )
                )
            }
        }
        return images
    }
}
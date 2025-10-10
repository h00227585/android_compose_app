package com.hdy.compose_examples.domain.usecase

import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import com.hdy.compose_examples.utils.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val TAG = "SaveImage"

/**
 * Save image into MediaStore.
 */

// CreateImageUriUseCase 提供了保存数据的“地址”，
// SaveImageUseCase 负责向这个地址投递“包裹” 并通知系统投递完成。
// 它们是实现 WorkManager 任务中保存图片结果所必需的、相互依赖的两个环节。
class SaveImageUseCase {
    suspend operator fun invoke(
        resolver: ContentResolver,
        contentUri: Uri?,
        bitmap: Bitmap,
    ) = withContext(Dispatchers.IO) {
        try {
            contentUri?.let { contentUri ->
                resolver.openOutputStream(contentUri, "w")?.use { outputStream ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outputStream)
                }
                val values = ContentValues().apply {
                    // 设置 IS_PENDING 为 0 表示 图片对其他应用可见。
                    put(MediaStore.Images.Media.IS_PENDING, 0)
                }
                resolver.update(contentUri, values, null, null)
            }
        } catch (e: Throwable) {
            Log.e(TAG, "Error occurs $e")
            throw e
        }
    }
}
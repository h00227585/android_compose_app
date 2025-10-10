package com.hdy.compose_examples.domain.usecase

import android.content.ContentResolver
import android.content.ContentValues
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CreateImageUriUseCase {
    // operator fun invoke: 这是 Kotlin 的操作符重载。
    // 它使得这个类的实例可以像函数一样被调用，
    // 例如：val uri = CreateImageUriUseCase()(resolver, "my_image.jpg")。
    //
    // suspend: 表明这是一个挂起函数。由于涉及文件系统操作，它必须在一个协程中执行。
    @RequiresApi(Build.VERSION_CODES.Q)
    suspend operator fun invoke(resolver: ContentResolver, filename: String): Uri? {
        return withContext(Dispatchers.IO) {
            // MediaStore.VOLUME_EXTERNAL_PRIMARY: 指定操作的目标是设备的主要外部存储卷
            // （即用户通常看到的那个存储空间，如内部存储或 SD 卡）。
            val imageCollection =
                MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
            // 设置文件元数据
            //
            // 告诉系统文件应存储在公共 Pictures 目录下，并在其中创建一个名为 Blur 的子文件夹。
            //
            // IS_PENDING=1: 将文件标记为待处理。这意味着文件已预留位置，但尚未完成写入。
            // 其他应用（和媒体扫描器）在文件写入完成前不会看到它，防止在文件不完整时被访问。
            val values = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, filename)
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/Blur")
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                put(MediaStore.Images.Media.IS_PENDING, 1)
            }
            val imageUri = resolver.insert(imageCollection, values)
            imageUri
        }
    }
}
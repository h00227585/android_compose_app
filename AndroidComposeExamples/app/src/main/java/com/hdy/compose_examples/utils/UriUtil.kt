package com.hdy.compose_examples.utils

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.annotation.AnyRes
import androidx.core.net.toUri
import java.io.File

object UriUtil {

    /**
     * 通过资源 ID 获取资源 URI，例如：
     * android.resource://com.example.app/drawable/ic_launcher
     */
    fun fromResource(context: Context, @AnyRes resId: Int): Uri {
        val res = context.resources
        return Uri.Builder()
            .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
            .authority(res.getResourcePackageName(resId))
            .appendPath(res.getResourceTypeName(resId))
            .appendPath(res.getResourceEntryName(resId))
            .build()
    }

    /**
     * 通过 assets 文件名获取 URI，例如：
     * sound.mp3 -> file:///android_asset/sound.mp3
     */
    fun fromAssets(assetName: String): Uri {
        return "file:///android_asset/$assetName".toUri()
    }

    /**
     * 通过本地文件路径生成 URI，例如：
     * /sdcard/Download/pic.jpg -> file:///sdcard/Download/pic.jpg
     */
    fun fromFilePath(path: String): Uri {
        return Uri.fromFile(File(path))
    }

    /**
     * 通过网络 URL（http / https）生成 URI。
     */
    fun fromNetwork(url: String): Uri {
        return url.toUri()
    }

    /**
     * 已经是 content:// 或 file:// 等完整 URI 的字符串。
     */
    fun fromString(uriString: String): Uri {
        return uriString.toUri()
    }
}
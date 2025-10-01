package com.hdy.compose_examples.core.permission

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat

/**
 * 权限管理工具类
 */
object PermissionManager {

    /**
     * 检查单个权限是否已授予
     */
    fun hasPermission(context: Context, permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * 检查多个权限是否全部已授予
     */
    fun hasPermissions(context: Context, permissions: List<String>): Boolean {
        return permissions.all { permission ->
            hasPermission(context, permission)
        }
    }

    /**
     * 获取未授予的权限列表
     */
    fun getDeniedPermissions(context: Context, permissions: List<String>): List<String> {
        return permissions.filter { permission ->
            !hasPermission(context, permission)
        }
    }

    /**
     * 获取图片读取权限（根据 Android 版本自动选择）
     */
    fun getImagePermissions(): List<String> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            listOf(Manifest.permission.READ_MEDIA_IMAGES)
        } else {
            listOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    /**
     * 获取视频读取权限
     */
    fun getVideoPermissions(): List<String> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            listOf(Manifest.permission.READ_MEDIA_VIDEO)
        } else {
            listOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    /**
     * 获取音频读取权限
     */
    fun getAudioPermissions(): List<String> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            listOf(Manifest.permission.READ_MEDIA_AUDIO)
        } else {
            listOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    /**
     * 获取相机权限
     */
    fun getCameraPermissions(): List<String> {
        return listOf(Manifest.permission.CAMERA)
    }

    /**
     * 获取定位权限（精确定位和粗略定位）
     */
    fun getLocationPermissions(): List<String> {
        return listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }

    /**
     * 获取通知权限（Android 13+）
     */
    fun getNotificationPermissions(): List<String> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            listOf(Manifest.permission.POST_NOTIFICATIONS)
        } else {
            emptyList()
        }
    }
}
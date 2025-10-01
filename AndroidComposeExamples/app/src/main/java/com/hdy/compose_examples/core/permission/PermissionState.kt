package com.hdy.compose_examples.core.permission

/**
 * 权限状态
 */
sealed class PermissionState {
    /** 已授予 */
    object Granted : PermissionState()

    /** 已拒绝 */
    data class Denied(val shouldShowRationale: Boolean) : PermissionState()

    /** 等待请求 */
    object NotRequested : PermissionState()
}

/**
 * 多权限状态
 */
data class MultiPermissionState(
    val allGranted: Boolean,
    val grantedPermissions: List<String>,
    val deniedPermissions: List<String>
)
package com.hdy.compose_examples.core.permission

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext

/**
 * 单个权限处理 Hook
 */
@Composable
fun rememberPermissionState(
    permission: String,
    onPermissionResult: (Boolean) -> Unit = {}
): PermissionStateHandler {
    val context = LocalContext.current
    var hasPermission by remember {
        mutableStateOf(PermissionManager.hasPermission(context, permission))
    }
    var shouldShowRationale by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasPermission = isGranted
        if (!isGranted) {
            shouldShowRationale = true
        }
        onPermissionResult(isGranted)
    }

    return PermissionStateHandler(
        hasPermission = hasPermission,
        shouldShowRationale = shouldShowRationale,
        requestPermission = { launcher.launch(permission) }
    )
}

/**
 * 多个权限处理 Hook
 */
@Composable
fun rememberMultiplePermissionsState(
    permissions: List<String>,
    onPermissionsResult: (Map<String, Boolean>) -> Unit = {}
): MultiplePermissionsStateHandler {
    val context = LocalContext.current
    var permissionsGranted by remember {
        mutableStateOf(PermissionManager.hasPermissions(context, permissions))
    }
    var deniedPermissions by remember {
        mutableStateOf(PermissionManager.getDeniedPermissions(context, permissions))
    }
    var shouldShowRationale by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsMap ->
        permissionsGranted = permissionsMap.values.all { it }
        deniedPermissions = permissionsMap.filter { !it.value }.keys.toList()
        if (deniedPermissions.isNotEmpty()) {
            shouldShowRationale = true
        }
        onPermissionsResult(permissionsMap)
    }

    return MultiplePermissionsStateHandler(
        allPermissionsGranted = permissionsGranted,
        deniedPermissions = deniedPermissions,
        shouldShowRationale = shouldShowRationale,
        requestPermissions = { launcher.launch(permissions.toTypedArray()) }
    )
}

/**
 * 单个权限状态处理器
 */
data class PermissionStateHandler(
    val hasPermission: Boolean,
    val shouldShowRationale: Boolean,
    val requestPermission: () -> Unit
)

/**
 * 多个权限状态处理器
 */
data class MultiplePermissionsStateHandler(
    val allPermissionsGranted: Boolean,
    val deniedPermissions: List<String>,
    val shouldShowRationale: Boolean,
    val requestPermissions: () -> Unit
)
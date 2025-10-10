package com.hdy.compose_examples.domain.repository

import androidx.work.WorkInfo
import kotlinx.coroutines.flow.Flow

interface BlurRepository {
    // 订阅了 WorkManager 的工作状态变化。
    // 实时提供一个 WorkInfo 对象，该对象包含了关于后台任务的最新信息（如状态、进度和输出数据）。
    val outputWorkInfo: Flow<WorkInfo>
    fun applyBlur(blurLevel: Int)
    fun cancelWork()
}
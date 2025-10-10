package com.hdy.compose_examples.ui.blur

import android.app.Application
import androidx.core.net.toUri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.work.WorkInfo
import com.hdy.compose_examples.MyApplication
import com.hdy.compose_examples.data.local.staticdata.BlurAmountData
import com.hdy.compose_examples.domain.repository.BlurRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

// 如果要使用 applicationContext，所以不能继承ViewModel，而是继承AndroidViewModel
class BlurViewModel(application: Application, private val blurRepository: BlurRepository) : AndroidViewModel(application) {
    internal val blurAmount = BlurAmountData.blurAmount

    // note:
    // 第一次 save image 之后，
    // WorkManager 会将这个成功状态及其输出数据（outputUri）持久化存储在设备的数据库中。
    // 因此，不但要检查outputImageUri是否为empty，也要检查对应的文件是否存在，
    // 因为，该文件可能被其他应用删除，但是 outputImageUri 不会被同步更新。
    // note2：通过isUriAccessible()检查不生效

    // 将底层的异步工作信息，实时地转化为用户界面可以直接使用的三种状态之一：Default、Loading 或 Complete。
    // map 运算符的作用是将上游（outputWorkInfo）发出的每一个 WorkInfo 对象，同步地转换成一个 BlurUiState 对象。
    //
    // stateIn 是 Flow 的一个关键操作符，它将一个普通的 Flow 转换为 一个 StateFlow。
    // StateFlow 是一个热流，它总是持有一个最新值（状态），非常适合在 ViewModel 中作为 UI 状态使用。
    val blurUiState: StateFlow<BlurUiState> = blurRepository.outputWorkInfo
        .map { info ->
            val outputImageUri = info.outputData.getString(KEY_IMAGE_URI)

            when {
                info.state.isFinished && !outputImageUri.isNullOrEmpty() -> {
//                    if (isUriAccessible(application, outputImageUri)) {
//                        BlurUiState.Complete(outputUri = outputImageUri)
//                    } else {
//                        BlurUiState.Default
//                    }
                    BlurUiState.Complete(outputUri = outputImageUri)
                }
                info.state == WorkInfo.State.CANCELLED -> {
                    BlurUiState.Default
                }
                else -> BlurUiState.Loading
            }
        }.stateIn(
            // scope = viewModelScope: 指定 StateFlow 的生命周期与 ViewModel 绑定。
            // 当 ViewModel 被清除时，StateFlow 也会被停止。
            scope = viewModelScope,
            // 仅当有 至少一个观察者 时，才开始运行上游的 Flow (outputWorkInfo)。
            //
            // 即使所有观察者都消失了，也继续保持运行 5 秒，
            // 以防视图（如 Fragment）只是短暂地进行了配置更改（如屏幕旋转）又回来订阅。
            started = SharingStarted.WhileSubscribed(5_000),
            // StateFlow 必须有一个初始值。
            // 在 WorkManager 状态发出任何值之前，BlurUiState 会立刻提供 Default 状态。
            initialValue = BlurUiState.Default
        )

    /**
     * Call the method from repository to create the WorkRequest to apply the blur
     * and save the resulting image
     * @param blurLevel The amount to blur the image
     */
    fun applyBlur(blurLevel: Int) {
        blurRepository.applyBlur(blurLevel)
    }

    /**
     * Call method from repository to cancel any ongoing WorkRequest
     * */
    fun cancelWork() {
        blurRepository.cancelWork()
    }

    /**
     * Factory for [BlurViewModel] that takes [BlurRepository] as a dependency
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
//                val blurRepository =
//                    (this[APPLICATION_KEY] as MyApplication).container.blurRepository
//                BlurViewModel(blurRepository = blurRepository)

                // 从 initializer 上下文中获取 Application 实例
                val application = this[APPLICATION_KEY] as MyApplication
                // 获取 Repository 实例
                val blurRepository = application.container.blurRepository
                // 构造函数传入 Application 和 Repository
                BlurViewModel(
                    application = application,
                    blurRepository = blurRepository
                )
            }
        }
    }

    // 不生效
    private fun isUriAccessible(application: Application, uriString: String): Boolean {
        return try {
            val uri = uriString.toUri()
            // 尝试打开文件描述符来验证文件是否存在
            application.contentResolver.openFileDescriptor(uri, "r")?.use {
                true
            } ?: false
        } catch (e: Exception) {
            false
        }
    }
}

package com.hdy.compose_examples.data.repository


import android.content.Context
import android.net.Uri
import androidx.lifecycle.asFlow
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.hdy.compose_examples.R
import com.hdy.compose_examples.domain.repository.BlurRepository
import com.hdy.compose_examples.ui.blur.IMAGE_MANIPULATION_WORK_NAME
import com.hdy.compose_examples.ui.blur.KEY_BLUR_LEVEL
import com.hdy.compose_examples.ui.blur.KEY_IMAGE_URI
import com.hdy.compose_examples.ui.blur.TAG_OUTPUT
import com.hdy.compose_examples.utils.extensions.resourceUri
import com.hdy.compose_examples.workers.BlurWorker
import com.hdy.compose_examples.workers.CleanupWorker
import com.hdy.compose_examples.workers.SaveImageToFileWorker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull


// 如果有多种实现方式，不应该命名为 BlurRepositoryImpl
// 推荐命名为 WorkManagerBlurRepository
//
// 传统上，Repository 模式的主要职责是作为 数据源（Data Source） 的单一入口，负责：
// 数据抽象： 隐藏数据来源（是数据库、网络还是文件）。
// 数据整合： 决定从哪个数据源获取数据（例如，先查缓存，再查网络）。
// 但在现代 Android 架构中，Repository 的角色已经扩展，它的核心职责变为：
// 管理应用的数据和业务逻辑（Business Logic）。这里的“数据”不仅仅指简单的值，也指数据状态的生成和管理。
class BlurRepositoryImpl(context: Context) : BlurRepository {

    // 存储了要进行模糊处理的原始图片 URI。
    // 它通过一个扩展函数 context.resourceUri() 在初始化时获取，表示要处理的源图片。
    private var imageUri: Uri = context.resourceUri(R.drawable.android_cupcake)

    // workManager: WorkManager 的单例实例，用于调度、查询和管理后台工作。
    private val workManager = WorkManager.getInstance(context)

    // 将 WorkManager 的状态查询结果转换为 Kotlin Flow，供 ViewModel 观察。
    //
    // .asFlow(): 这是一个 Jetpack KTX 扩展函数，将 LiveData 转换为 Flow，以便在协程环境中使用。
    override val outputWorkInfo: Flow<WorkInfo> =
        workManager.getWorkInfosByTagLiveData(TAG_OUTPUT).asFlow().mapNotNull {
            if (it.isNotEmpty()) it.first() else null
        }

    /**
     * Create the WorkRequests to apply the blur and save the resulting image
     * @param blurLevel The amount to blur the image
     */
    override fun applyBlur(blurLevel: Int) {
        // beginUniqueWork: 启动一个唯一的工作序列。
        // ExistingWorkPolicy.REPLACE: 如果发现有同名的任务正在运行或排队，取消旧任务并替换为新任务。
        // CleanupWorker: 任务链的第一步。这个 OneTimeWorkRequest 用于清理上一次操作遗留的临时文件。
        var continuation = workManager
            .beginUniqueWork(
                IMAGE_MANIPULATION_WORK_NAME,
                ExistingWorkPolicy.REPLACE,
                OneTimeWorkRequest.from(CleanupWorker::class.java)
            )

        // Constraints: 定义了工作执行的条件，这里要求设备的电池电量不能过低
        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()

        // Add WorkRequest to blur the image
        val blurBuilder = OneTimeWorkRequestBuilder<BlurWorker>()

        // 设置输入数据，包括源图片 URI 和模糊级别
        blurBuilder.setInputData(createInputDataForWorkRequest(blurLevel, imageUri))

        blurBuilder.setConstraints(constraints)

        // 将blur处理任务链接到 CleanupWorker 之后，只有当清理任务成功后，模糊任务才会开始。
        //
        // note:
        // BlurWorker不只是进行了模糊处理，还将处理结果保存为了临时文件。
        continuation = continuation.then(blurBuilder.build())

        // TAG_OUTPUT: 这个标签用于标识最终的 WorkRequest，
        // 使得 outputWorkInfo Flow 可以准确地跟踪这个最终任务的状态。
        val save = OneTimeWorkRequestBuilder<SaveImageToFileWorker>()
            .addTag(TAG_OUTPUT)
            .build()

        // 将保存任务链接到模糊处理任务之后，确保只有在图片模糊成功后，才会尝试保存文件。
        continuation = continuation.then(save)

        // 提交整个任务链给 WorkManager，使其开始调度执行。
        continuation.enqueue()
    }

    /**
     * IMAGE_MANIPULATION_WORK_NAME: 利用 beginUniqueWork 时设置的 唯一名称，
     * 调用 cancelUniqueWork 可以立即尝试取消整个工作序列中所有未完成或正在运行的任务。
     * */
    override fun cancelWork() {
        workManager.cancelUniqueWork(IMAGE_MANIPULATION_WORK_NAME)
    }

    /**
     * 将输入参数（URI 和模糊级别）封装成 WorkManager 所需的 Data 对象，
     * 这是 WorkManager 任务间传递数据的标准方式。
     */
    private fun createInputDataForWorkRequest(blurLevel: Int, imageUri: Uri): Data {
        val builder = Data.Builder()
        builder.putString(KEY_IMAGE_URI, imageUri.toString()).putInt(KEY_BLUR_LEVEL, blurLevel)
        return builder.build()
    }
}
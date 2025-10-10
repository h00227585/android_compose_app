package com.hdy.compose_examples.workers

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.hdy.compose_examples.R
import com.hdy.compose_examples.ui.blur.DELAY_TIME_MILLIS
import com.hdy.compose_examples.ui.blur.KEY_BLUR_LEVEL
import com.hdy.compose_examples.ui.blur.KEY_IMAGE_URI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import androidx.core.net.toUri

private const val TAG = "BlurWorker"

// BlurWorker 类继承自 CoroutineWorker，
// 这意味着它的核心逻辑 doWork() 方法是在一个协程中执行的，可以利用 Kotlin 协程的强大功能来处理异步任务。
class BlurWorker(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {

    override suspend fun doWork(): Result {
        // 要处理的原始图片的 URI
        val resourceUri = inputData.getString(KEY_IMAGE_URI)
        // 图片的模糊程度，默认为 1。
        val blurLevel = inputData.getInt(KEY_BLUR_LEVEL, 1)

        // 在任务开始前，调用一个实用函数来显示一个通知，告诉用户后台正在进行工作。
        makeStatusNotification(
            applicationContext.resources.getString(R.string.blurring_image),
            applicationContext
        )

        // doWork() 默认运行在 WorkManager 提供的默认调度器上。
        // 由于图片处理和文件读写是 I/O 密集型 任务，
        // withContext(Dispatchers.IO) 将后续的代码块切换到 I/O 线程池 执行，
        // 避免阻塞主线程或默认调度器，这是处理磁盘操作的最佳实践。
        return withContext(Dispatchers.IO) {

            // 非阻塞的暂停，用于模拟图片处理需要较长时间才能完成的情况。
            delay(DELAY_TIME_MILLIS)

            // 所有的耗时操作被包裹在一个 try/catch 块中，
            // 以确保任何异常都能被捕获，并返回一个 Result.failure()。
            return@withContext try {
                // 输入校验，确保 resourceUri 必须存在且非空。
                // 如果校验失败，require 会抛出 IllegalArgumentException，被 catch 块捕获，任务将失败。
                require(!resourceUri.isNullOrBlank()) {
                    val errorMessage =
                        applicationContext.resources.getString(R.string.invalid_input_uri)
                    Log.e(TAG, errorMessage)
                    errorMessage
                }

                // 加载
                // 使用 ContentResolver 打开输入 URI 对应的文件流，
                // 并使用 BitmapFactory.decodeStream 将其解码为 Bitmap 对象 (picture)。
                val resolver = applicationContext.contentResolver
                val picture = BitmapFactory.decodeStream(
                    resolver.openInputStream(resourceUri.toUri())
                )

                // 模糊处理
                // 调用 blurBitmap(picture, blurLevel) 实用函数执行实际的图片模糊算法，生成结果 output Bitmap。
                val output = blurBitmap(picture, blurLevel)

                // 保存
                // 调用 writeBitmapToFile 实用函数将处理后的 output Bitmap 写入一个临时文件，
                // 并获取该临时文件的 URI (outputUri)。
                val outputUri = writeBitmapToFile(applicationContext, output)

                // 使用 workDataOf 创建一个 Data 对象，
                // 将结果图片的 URI 存储起来，以便上游（如 ViewModel）可以获取和显示它。
                val outputData = workDataOf(KEY_IMAGE_URI to outputUri.toString())

                Result.success(outputData)
            } catch (throwable: Throwable) {
                Log.e(
                    TAG,
                    applicationContext.resources.getString(R.string.error_applying_blur),
                    throwable
                )
                Result.failure()
            }
        }
    }
}
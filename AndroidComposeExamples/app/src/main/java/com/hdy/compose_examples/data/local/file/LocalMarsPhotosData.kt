package com.hdy.compose_examples.data.local.file

import android.content.Context
import com.hdy.compose_examples.data.model.MarsPhoto
import com.hdy.compose_examples.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.IOException

class LocalMarsPhotosData(
    private val context: Context,
    private val jsonFileName: String = "mars_photos.json"
) {
    suspend fun getMarsPhotos(): List<MarsPhoto> {
        // 使用 withContext(Dispatchers.IO) 确保在后台线程进行文件操作
        return withContext(Dispatchers.IO) {
            try {
                // 从 assets 获取 InputStream
                val inputStream = context.assets.open(jsonFileName)
                // 将 InputStream 读取为 String
                val jsonString = inputStream.bufferedReader().use { it.readText() }
                // 使用 kotlinx.serialization 解析 JSON 字符串
                // 注意：需要确保 MarsPhoto 类是可序列化的 (@Serializable)
                // 因为 json 是列表格式的，所以，使用 List<MarsPhoto>
                return@withContext Json.Default.decodeFromString<List<MarsPhoto>>(jsonString)
            } catch (e: IOException) {
                // 处理文件读取错误
                Log.e("LocalDataSource", "Error reading local JSON file", e)
                emptyList()
            } catch (e: SerializationException) {
                // 处理 JSON 解析错误
                Log.e("LocalDataSource", "Error parsing local JSON", e)
                emptyList()
            }
        }
    }
}
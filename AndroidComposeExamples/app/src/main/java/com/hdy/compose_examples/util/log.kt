package com.hdy.compose_examples.util

import android.util.Log

object Log {

    /** 默认前缀 */
    private const val PREFIX = "Examples-"

    /** 默认的 Tag */
    private const val DEFAULT_TAG = "Examples"

    /**
     * 拼接前缀
     */
    private fun withPrefix(tag: String?): String {
        return if (tag.isNullOrBlank()) DEFAULT_TAG else PREFIX + tag
    }

    fun d(tag: String? = null, msg: String) {
        Log.d(withPrefix(tag), msg)
    }

    fun i(tag: String? = null, msg: String) {
        Log.i(withPrefix(tag), msg)
    }

    fun w(tag: String? = null, msg: String) {
        Log.w(withPrefix(tag), msg)
    }

    fun e(tag: String? = null, msg: String, throwable: Throwable? = null) {
        Log.e(withPrefix(tag), msg, throwable)
    }
}
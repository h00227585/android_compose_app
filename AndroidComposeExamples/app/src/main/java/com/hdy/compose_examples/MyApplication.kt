package com.hdy.compose_examples

import android.app.Application
import com.hdy.compose_examples.di.AppContainer
import com.hdy.compose_examples.di.AppDataContainer

class MyApplication: Application() {
    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}
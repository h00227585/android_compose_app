package com.hdy.compose_examples

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.hdy.compose_examples.data.repository.DessertReleasePrefRepository
import com.hdy.compose_examples.di.AppContainer
import com.hdy.compose_examples.di.AppDataContainer

private const val LAYOUT_PREFERENCE_NAME = "layout_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = LAYOUT_PREFERENCE_NAME
)

class MyApplication: Application() {
    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer
    lateinit var dessertReleasePrefRepository: DessertReleasePrefRepository

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)

        dessertReleasePrefRepository = DessertReleasePrefRepository(dataStore)
    }
}
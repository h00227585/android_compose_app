package com.hdy.compose_examples.di

import android.content.Context
import com.hdy.compose_examples.data.local.database.InventoryDatabase
import com.hdy.compose_examples.data.repository.OfflineItemsRepository
import com.hdy.compose_examples.domain.repository.ItemsRepository

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val itemsRepository: ItemsRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineItemsRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [ItemsRepository]
     */
    override val itemsRepository: ItemsRepository by lazy {
        OfflineItemsRepository(InventoryDatabase.getDatabase(context).itemDao())
    }
}
package com.hdy.compose_examples.di

import android.content.Context
import com.hdy.compose_examples.data.local.db.InventoryDatabase
import com.hdy.compose_examples.data.repository.BlurRepositoryImpl
import com.hdy.compose_examples.data.repository.OfflineItemsRepository
import com.hdy.compose_examples.domain.repository.BlurRepository
import com.hdy.compose_examples.domain.repository.ItemsRepository

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val itemsRepository: ItemsRepository
    val blurRepository: BlurRepository
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

    override val blurRepository = BlurRepositoryImpl(context)
}
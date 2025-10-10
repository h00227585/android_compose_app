package com.hdy.compose_examples.data.repository

import com.hdy.compose_examples.data.local.db.dao.ItemDao
import com.hdy.compose_examples.data.local.db.entity.Item
import com.hdy.compose_examples.domain.repository.ItemsRepository
import kotlinx.coroutines.flow.Flow

class OfflineItemsRepository(private val itemDao: ItemDao) : ItemsRepository {
    override fun getAllItemsStream(): Flow<List<Item>> = itemDao.getAllItems()

    override fun getItemStream(id: Int): Flow<Item?> = itemDao.getItem(id)

    override suspend fun insertItem(item: Item) = itemDao.insert(item)

    override suspend fun deleteItem(item: Item) = itemDao.delete(item)

    override suspend fun updateItem(item: Item) = itemDao.update(item)
}

package com.hdy.compose_examples.ui.inventory.item.item_details

import com.hdy.compose_examples.data.local.db.model.ItemDetails

/**
 * UI state for ItemDetailsScreen
 */
data class ItemDetailsUiState(
    val outOfStock: Boolean = true,
    val itemDetails: ItemDetails = ItemDetails()
)
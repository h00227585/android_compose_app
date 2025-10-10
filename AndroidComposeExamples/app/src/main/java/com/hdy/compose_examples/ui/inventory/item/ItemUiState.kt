package com.hdy.compose_examples.ui.inventory.item

import com.hdy.compose_examples.data.local.db.model.ItemDetails

/**
 * Represents Ui State for an Item.
 */
data class ItemUiState(
    val itemDetails: ItemDetails = ItemDetails(),
    val isEntryValid: Boolean = false
)
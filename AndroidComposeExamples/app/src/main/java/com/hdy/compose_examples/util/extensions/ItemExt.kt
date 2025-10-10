package com.hdy.compose_examples.util.extensions

import com.hdy.compose_examples.data.local.db.entity.Item
import com.hdy.compose_examples.data.local.db.model.ItemDetails
import com.hdy.compose_examples.ui.inventory.item.ItemUiState
import java.text.NumberFormat

fun Item.formatedPrice(): String {
    return NumberFormat.getCurrencyInstance().format(price)
}

/**
 * Extension function to convert [Item] to [ItemUiState]
 */
fun Item.toItemUiState(isEntryValid: Boolean = false): ItemUiState = ItemUiState(
    itemDetails = this.toItemDetails(),
    isEntryValid = isEntryValid
)

/**
 * Extension function to convert [Item] to [ItemDetails]
 */
fun Item.toItemDetails(): ItemDetails = ItemDetails(
    id = id,
    name = name,
    price = price.toString(),
    quantity = quantity.toString()
)

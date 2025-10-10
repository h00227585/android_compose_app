package com.hdy.compose_examples.util.extensions

import com.hdy.compose_examples.data.local.db.entity.Item
import com.hdy.compose_examples.data.local.db.model.ItemDetails

fun ItemDetails.toItem(): Item = Item(
    id = id,
    name = name,
    price = price.toDoubleOrNull() ?: 0.0,
    quantity = quantity.toIntOrNull() ?: 0
)
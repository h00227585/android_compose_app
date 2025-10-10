package com.hdy.compose_examples.ui.inventory.home

import com.hdy.compose_examples.data.local.db.entity.Item

data class HomeUiState(val itemList: List<Item> = listOf())
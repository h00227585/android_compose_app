package com.hdy.compose_examples.ui.dessert

import androidx.annotation.DrawableRes
import com.hdy.compose_examples.data.local.staticdata.DessertData.dessertList

data class DessertUiState(
    val currentDessertIndex: Int = 0,
    val dessertsSold: Int = 0,
    val revenue: Int = 0,
    val currentDessertPrice: Int = dessertList[currentDessertIndex].price,
    @DrawableRes val currentDessertImageId: Int = dessertList[currentDessertIndex].imageId
)
package com.hdy.compose_examples.tip_calculator

import com.hdy.compose_examples.ui.tip_calculator.calculateTip
import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.NumberFormat

class TipCalculatorTest {
    @Test
    fun calculateTip_20PercentNoRoundup() {
        val amount = 120.0
        val tipPercent = 12.0
        val expectedTip = NumberFormat.getCurrencyInstance().format(14.40)
        val actualTip = calculateTip(amount = amount,
            tipPercent = tipPercent,
            false)
        assertEquals(expectedTip, actualTip)
    }

    @Test
    fun calculateTip_20PercentRoundup() {
        val amount = 120.0
        val tipPercent = 12.0
        val expectedTip = NumberFormat.getCurrencyInstance().format(15)
        val actualTip = calculateTip(amount = amount,
            tipPercent = tipPercent,
            true)
        assertEquals(expectedTip, actualTip)
    }
}
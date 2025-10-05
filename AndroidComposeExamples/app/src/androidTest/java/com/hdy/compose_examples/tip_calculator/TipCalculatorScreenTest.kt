package com.hdy.compose_examples.tip_calculator

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import com.hdy.compose_examples.ui.tip_calculator.TipCalculatorScreen
import org.junit.Rule
import org.junit.Test
import java.text.NumberFormat

class TipCalculatorScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun checkNodes() {
        composeTestRule.setContent {
            TipCalculatorScreen()
        }

        composeTestRule.onNodeWithTag("round up switch").assertExists()
    }

    @Test
    fun enterValuesAndCheckTipCalculation() {
        composeTestRule.setContent {
            TipCalculatorScreen()
        }

        composeTestRule.onNodeWithText("Bill Amount")
            .performTextInput("120")
        composeTestRule.onNodeWithText("Tip Percentage")
            .performTextInput("12")
        val expectedTip = NumberFormat.getCurrencyInstance().format(14.40)
        composeTestRule.onNodeWithText("Tip Amount: $expectedTip").assertIsDisplayed()
    }
}
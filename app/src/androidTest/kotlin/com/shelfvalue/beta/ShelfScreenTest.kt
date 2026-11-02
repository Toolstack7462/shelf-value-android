package com.shelfvalue.beta

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Rule
import org.junit.Test

class ShelfScreenTest {
    @get:Rule val compose = createAndroidComposeRule<MainActivity>()
    @Test fun comparesAndResets() {
        compose.onNodeWithTag("price_A").performTextInput("120")
        compose.onNodeWithTag("quantity_A").performTextInput("500")
        compose.onNodeWithTag("price_B").performScrollTo().performTextInput("150")
        compose.onNodeWithTag("quantity_B").performScrollTo().performTextInput("500")
        compose.onNodeWithTag("compare").performScrollTo().performClick()
        compose.onNodeWithText("Product A is cheaper per unit").assertExists()
        compose.onNodeWithTag("reset").performClick()
        compose.onNodeWithText("Enter both products to compare.").assertExists()
    }
}

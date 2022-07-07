package com.makeappssimple.abhimanyu.financemanager.android.ui.components.total_balance_card

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.MyAppTheme
import org.junit.Rule
import org.junit.Test

class TotalBalanceCardTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun defaultTest() {
        composeTestRule.setContent {
            MyAppTheme {
                TotalBalanceCardView(
                    data = TotalBalanceCardViewData(
                        totalBalanceAmount = 123456L,
                    ),
                )
            }
        }

        composeTestRule
            .onNodeWithText("123456")
            .assertIsDisplayed()
    }
}

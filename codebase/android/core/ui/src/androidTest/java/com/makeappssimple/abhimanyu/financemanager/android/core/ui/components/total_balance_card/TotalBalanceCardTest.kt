package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.total_balance_card

import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.MyAppTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TotalBalanceCardTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private lateinit var balanceAmount: SemanticsNodeInteraction
    private lateinit var title: SemanticsNodeInteraction

    @Before
    fun setup() {

    }

    @Test
    fun defaultTest() {
        composeTestRule.setContent {
            MyAppTheme {
                TotalBalanceCardView(
                    totalBalanceAmount = 123456L,
                )
            }
        }
        balanceAmount = composeTestRule.onNodeWithText("₹1,23,456")
        title = composeTestRule.onNodeWithText("TOTAL BALANCE")

        title.assertIsDisplayed()
        balanceAmount.assertIsDisplayed()

        composeTestRule.onNode(hasClickAction()).assertDoesNotExist()
    }

    @Test
    fun hasClick() {
        composeTestRule.setContent {
            MyAppTheme {
                TotalBalanceCardView(
                    totalBalanceAmount = 123456L,
                    onClick = {},
                )
            }
        }

        composeTestRule.onNode(hasClickAction()).assertExists()
    }
}

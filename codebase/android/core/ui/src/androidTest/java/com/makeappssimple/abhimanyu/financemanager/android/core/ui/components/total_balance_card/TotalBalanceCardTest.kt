package com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.total_balance_card

import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertHasNoClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.MyAppTheme
import com.makeappssimple.abhimanyu.financemanager.android.core.testing.constants.TestTags
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.total_balance_card.TotalBalanceCard
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.total_balance_card.TotalBalanceCardData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.total_balance_card.TotalBalanceCardEvents
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
                TotalBalanceCard(
                    data = TotalBalanceCardData(
                        totalBalanceAmount = TEST_TOTAL_BALANCE_AMOUNT,
                    ),
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
    fun hasClick_hasNoClickActionWhenEventIsNull() {
        composeTestRule.setContent {
            MyAppTheme {
                TotalBalanceCard(
                    data = TotalBalanceCardData(
                        totalBalanceAmount = TEST_TOTAL_BALANCE_AMOUNT,
                    ),
                )
            }
        }

        composeTestRule
            .onNodeWithTag(TestTags.TOTAL_BALANCE_CARD)
            .assertHasNoClickAction()
    }

    @Test
    fun hasClick_hasClickActionWhenEventIsNotNull() {
        composeTestRule.setContent {
            MyAppTheme {
                TotalBalanceCard(
                    data = TotalBalanceCardData(
                        totalBalanceAmount = TEST_TOTAL_BALANCE_AMOUNT,
                    ),
                    events = TotalBalanceCardEvents(
                        onClick = {},
                    ),
                )
            }
        }

        composeTestRule
            .onNodeWithTag(TestTags.TOTAL_BALANCE_CARD)
            .assertHasClickAction()
    }

    companion object {
        private const val TEST_TOTAL_BALANCE_AMOUNT = 123456L
    }
}

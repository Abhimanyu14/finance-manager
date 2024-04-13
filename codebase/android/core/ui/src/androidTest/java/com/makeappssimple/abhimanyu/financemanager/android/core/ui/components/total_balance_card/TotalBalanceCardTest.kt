package com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.total_balance_card

import android.content.Context
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertHasNoClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ApplicationProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.MyAppTheme
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.R
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.total_balance_card.TotalBalanceCard
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.total_balance_card.TotalBalanceCardData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.total_balance_card.TotalBalanceCardEvents
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
public class TotalBalanceCardTest {
    @get:Rule(order = 0)
    public var hiltRule: HiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    public val composeTestRule: ComposeContentTestRule = createComposeRule()

    private lateinit var context: Context

    private lateinit var balanceAmount: SemanticsNodeInteraction
    private lateinit var title: SemanticsNodeInteraction

    @Before
    public fun setup() {
        context = ApplicationProvider.getApplicationContext()

        balanceAmount = composeTestRule.onNodeWithText("₹1,23,456")
        title = composeTestRule.onNodeWithText(
            context.getString(R.string.total_balance_card_title)
        )
    }

    @Test
    public fun defaultTest() {
        composeTestRule.setContent {
            MyAppTheme {
                TotalBalanceCard(
                    data = TotalBalanceCardData(
                        totalBalanceAmount = TEST_TOTAL_BALANCE_AMOUNT,
                    ),
                )
            }
        }

        title.assertIsDisplayed()
        balanceAmount.assertIsDisplayed()

        composeTestRule.onNode(hasClickAction()).assertDoesNotExist()
    }

    @Test
    public fun hasClick_hasNoClickActionWhenEventIsNull() {
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
            .onNodeWithTag(TestTags.COMPONENT_TOTAL_BALANCE_CARD)
            .assertHasNoClickAction()
    }

    @Test
    public fun hasClick_hasClickActionWhenEventIsNotNull() {
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
            .onNodeWithTag(TestTags.COMPONENT_TOTAL_BALANCE_CARD)
            .assertHasClickAction()
    }

    public companion object {
        private const val TEST_TOTAL_BALANCE_AMOUNT = 123456L
    }
}

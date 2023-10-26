package com.makeappssimple.abhimanyu.financemanager.android

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.MyAppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class NavigationTest {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun homeScreenTest() {
        composeTestRule.activity.setContent {
            MyAppTheme {
                MyApp()
            }
        }

        composeTestRule
            .onNodeWithText("Finance Manager")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription("Settings")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithContentDescription("Settings")
            .assertHasClickAction()

        composeTestRule
            .onNodeWithContentDescription("Add Transaction")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithContentDescription("Add Transaction")
            .assertHasClickAction()
    }

    @Test
    fun settingsScreenTest() {
        composeTestRule.activity.setContent {
            MyAppTheme {
                MyApp()
            }
        }

        composeTestRule
            .onNodeWithText("Finance Manager")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription("Settings")
            .performClick()
        composeTestRule
            .onNodeWithText("Settings")
            .assertIsDisplayed()
    }

    @Test
    fun addTransactionScreenTest() {
        composeTestRule.activity.setContent {
            MyAppTheme {
                MyApp()
            }
        }

        composeTestRule
            .onNodeWithText("Finance Manager")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription("Add Transaction")
            .performClick()
        composeTestRule
            .onNodeWithText("Enter Transaction Details")
            .assertIsDisplayed()
    }
}

package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.screen

import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertHasNoClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.MyAppTheme
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenViewState
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SettingsScreenViewTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val testAppVersion = "2023.04.07.1"
    private val testSettingsScreenViewData = SettingsScreenViewData(
        isLoading = false,
        appVersion = testAppVersion,
        backupData = {},
        navigateToTransactionForValuesScreen = {},
        navigateUp = {},
        recalculateTotal = {},
        restoreData = {},
    )

    private lateinit var linearProgressIndicator: SemanticsNodeInteraction
    private lateinit var backupDataListItem: SemanticsNodeInteraction
    private lateinit var restoreDataListItem: SemanticsNodeInteraction
    private lateinit var recalculateTotalListItem: SemanticsNodeInteraction
    private lateinit var transactionForListItem: SemanticsNodeInteraction
    private lateinit var appVersionText: SemanticsNodeInteraction

    @Before
    fun setup() {
        linearProgressIndicator = composeTestRule.onNodeWithTag(
            testTag = "linear_progress_indicator",
        )
        backupDataListItem = composeTestRule.onNodeWithText(
            text = "Backup Data",
        )
        recalculateTotalListItem = composeTestRule.onNodeWithText(
            text = "Restore Data",
        )
        restoreDataListItem = composeTestRule.onNodeWithText(
            text = "Recalculate Total",
        )
        transactionForListItem = composeTestRule.onNodeWithText(
            text = "Transaction For",
        )
        appVersionText = composeTestRule.onNodeWithText(
            text = "Version",
            substring = true,
        )
    }

    @Test
    fun isLoading_isTrue() {
        composeTestRule.setContent {
            MyAppTheme {
                SettingsScreenView(
                    data = testSettingsScreenViewData.copy(
                        isLoading = true,
                    ),
                    state = rememberCommonScreenViewState(),
                )
            }
        }

        // Assert that the progress indicator is not shown
        linearProgressIndicator.assertIsDisplayed()

        // Assert that the list items are shown while loading
        backupDataListItem.assertIsDisplayed()
        recalculateTotalListItem.assertIsDisplayed()
        restoreDataListItem.assertIsDisplayed()
        transactionForListItem.assertIsDisplayed()

        // Assert that the list items are clickable
        backupDataListItem.assertHasNoClickAction()
        recalculateTotalListItem.assertHasNoClickAction()
        restoreDataListItem.assertHasNoClickAction()
        transactionForListItem.assertHasNoClickAction()
    }

    @Test
    fun isLoading_isFalse() {
        composeTestRule.setContent {
            MyAppTheme {
                SettingsScreenView(
                    data = testSettingsScreenViewData.copy(
                        isLoading = false,
                    ),
                    state = rememberCommonScreenViewState(),
                )
            }
        }

        // Assert that the progress indicator is not shown
        linearProgressIndicator.assertDoesNotExist()

        // Assert that the list items are shown while not loading
        backupDataListItem.assertIsDisplayed()
        recalculateTotalListItem.assertIsDisplayed()
        restoreDataListItem.assertIsDisplayed()
        transactionForListItem.assertIsDisplayed()

        // Assert that the list items are clickable
        backupDataListItem.assertHasClickAction()
        recalculateTotalListItem.assertHasClickAction()
        restoreDataListItem.assertHasClickAction()
        transactionForListItem.assertHasClickAction()
    }

    @Test
    fun appVersion_isShown() {
        composeTestRule.setContent {
            MyAppTheme {
                SettingsScreenView(
                    data = testSettingsScreenViewData.copy(
                        appVersion = testAppVersion,
                    ),
                    state = rememberCommonScreenViewState(),
                )
            }
        }

        // Assert that the progress indicator is not shown
        appVersionText.assertIsDisplayed()
    }

    @Test
    fun appVersion_isNotShown() {
        composeTestRule.setContent {
            MyAppTheme {
                SettingsScreenView(
                    data = testSettingsScreenViewData.copy(
                        appVersion = null,
                    ),
                    state = rememberCommonScreenViewState(),
                )
            }
        }

        // Assert that the progress indicator is not shown
        appVersionText.assertDoesNotExist()
    }
}

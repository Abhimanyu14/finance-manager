package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.screen

import android.content.Context
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertHasNoClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ApplicationProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.MyAppTheme
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.screen.SettingsScreenUI
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.screen.SettingsScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.screen.SettingsScreenUIState
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
public class SettingsScreenViewTest {
    @get:Rule(order = 0)
    public var hiltRule: HiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    public val composeTestRule: ComposeContentTestRule = createComposeRule()

    private lateinit var context: Context

    private lateinit var linearProgressIndicator: SemanticsNodeInteraction
    private lateinit var backupDataListItem: SemanticsNodeInteraction
    private lateinit var restoreDataListItem: SemanticsNodeInteraction
    private lateinit var recalculateTotalListItem: SemanticsNodeInteraction
    private lateinit var transactionForListItem: SemanticsNodeInteraction
    private lateinit var appVersionText: SemanticsNodeInteraction

    @Before
    public fun setup() {
        context = ApplicationProvider.getApplicationContext()

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
    public fun isLoading_isTrue() {
        composeTestRule.setContent {
            MyAppTheme {
                SettingsScreenUI(
                    uiState = SettingsScreenUIState(
                        data = MyResult.Success(
                            data = testSettingsScreenViewData.copy(
                                isLoading = true,
                            ),
                        ),
                    ),
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
    public fun isLoading_isFalse() {
        composeTestRule.setContent {
            MyAppTheme {
                SettingsScreenUI(
                    uiState = SettingsScreenUIState(
                        data = MyResult.Success(
                            data = testSettingsScreenViewData.copy(
                                isLoading = false,
                            ),
                        ),
                    ),
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
    public fun appVersion_isShown() {
        composeTestRule.setContent {
            MyAppTheme {
                SettingsScreenUI(
                    uiState = SettingsScreenUIState(
                        data = MyResult.Success(
                            data = testSettingsScreenViewData.copy(
                                appVersion = TEST_APP_VERSION,
                            ),
                        ),
                    ),
                )
            }
        }

        linearProgressIndicator.assertDoesNotExist()
        appVersionText.assertIsDisplayed()
    }

    @Test
    public fun appVersion_isNotShown() {
        composeTestRule.setContent {
            MyAppTheme {
                SettingsScreenUI(
                    uiState = SettingsScreenUIState(
                        data = MyResult.Success(
                            data = testSettingsScreenViewData.copy(
                                appVersion = null,
                            ),
                        ),
                    ),
                )
            }
        }

        linearProgressIndicator.assertDoesNotExist()
        appVersionText.assertDoesNotExist()
    }

    public companion object {
        private const val TEST_APP_VERSION = "2023.04.07.1"
        private val testSettingsScreenViewData = SettingsScreenUIData(
            isLoading = false,
            appVersion = TEST_APP_VERSION,
        )
    }
}

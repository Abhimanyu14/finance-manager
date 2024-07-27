package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.screen

/*
@HiltAndroidTest
internal class SettingsScreenViewTest {
    @get:Rule(order = 0)
    internal var hiltRule: HiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    internal val composeTestRule: ComposeContentTestRule = createComposeRule()

    private lateinit var context: Context

    private lateinit var linearProgressIndicator: SemanticsNodeInteraction
    private lateinit var backupDataListItem: SemanticsNodeInteraction
    private lateinit var restoreDataListItem: SemanticsNodeInteraction
    private lateinit var recalculateTotalListItem: SemanticsNodeInteraction
    private lateinit var transactionForListItem: SemanticsNodeInteraction
    private lateinit var appVersionText: SemanticsNodeInteraction

    @Before
    fun setup() {
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

    @Test fun isLoading_isTrue() {
        composeTestRule.setContent {
            MyAppTheme {
                SettingsScreenUI(
                    uiState = SettingsScreenUIState(
                        isLoading = true,
                        isReminderEnabled = null,
                        screenBottomSheetType = SettingsScreenBottomSheetType.None,
                        snackbarHostState = SnackbarHostState(),
                        appVersion = null,
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

    @Test fun isLoading_isFalse() {
        composeTestRule.setContent {
            MyAppTheme {
                SettingsScreenUI(
                    uiState = SettingsScreenUIState(
                        isLoading = true,
                        isReminderEnabled = null,
                        screenBottomSheetType = SettingsScreenBottomSheetType.None,
                        snackbarHostState = SnackbarHostState(),
                        appVersion = null,
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

    @Test fun appVersion_isShown() {
        composeTestRule.setContent {
            MyAppTheme {
                SettingsScreenUI(
                    uiState = SettingsScreenUIState(
                        isLoading = true,
                        isReminderEnabled = null,
                        screenBottomSheetType = SettingsScreenBottomSheetType.None,
                        snackbarHostState = SnackbarHostState(),
                        appVersion = null,
                    ),
                )
            }
        }

        linearProgressIndicator.assertDoesNotExist()
        appVersionText.assertIsDisplayed()
    }

    @Test fun appVersion_isNotShown() {
        composeTestRule.setContent {
            MyAppTheme {
                SettingsScreenUI(
                    uiState = SettingsScreenUIState(
                        isLoading = true,
                        isReminderEnabled = null,
                        screenBottomSheetType = SettingsScreenBottomSheetType.None,
                        snackbarHostState = SnackbarHostState(),
                        appVersion = null,
                    ),
                )
            }
        }

        linearProgressIndicator.assertDoesNotExist()
        appVersionText.assertDoesNotExist()
    }

    internal companion object {
        private const val TEST_APP_VERSION = "2023.04.07.1"
        private val testSettingsScreenViewData = SettingsScreenUIData(
            isLoading = false,
            appVersion = TEST_APP_VERSION,
        )
    }
}
*/

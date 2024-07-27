package com.makeappssimple.abhimanyu.financemanager.android

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.SemanticsNodeInteractionCollection
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.printToLog
import androidx.navigation.NavController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.COMPONENT_OVERVIEW_CARD
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.COMPONENT_TOTAL_BALANCE_CARD
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_ACCOUNTS
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_ADD_OR_EDIT_ACCOUNT
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_ADD_OR_EDIT_CATEGORY
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_ADD_OR_EDIT_TRANSACTION
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_ADD_OR_EDIT_TRANSACTION_FOR
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_ANALYSIS
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_CATEGORIES
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_CONTENT_ACCOUNTS
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_CONTENT_ADD_OR_EDIT_ACCOUNT
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_CONTENT_ADD_OR_EDIT_CATEGORY
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_CONTENT_ADD_OR_EDIT_TRANSACTION
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_CONTENT_ADD_OR_EDIT_TRANSACTION_FOR
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_CONTENT_ANALYSIS
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_CONTENT_CATEGORIES
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_CONTENT_HOME
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_CONTENT_OPEN_SOURCE_LICENSES
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_CONTENT_SETTINGS
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_CONTENT_TRANSACTIONS
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_CONTENT_TRANSACTION_FOR_VALUES
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_CONTENT_VIEW_TRANSACTION
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_HOME
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_OPEN_SOURCE_LICENSES
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_SETTINGS
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_TRANSACTIONS
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_TRANSACTION_FOR_VALUES
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_VIEW_TRANSACTION
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.fake.FakeMyLoggerImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Screen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Job
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.Duration.Companion.seconds
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.R as CoreUiR
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.R as AccountsR
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.R as CategoriesR
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.R as HomeR
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.R as SettingsR
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.R as TransactionForR
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.R as TransactionsR

@HiltAndroidTest
internal class ComposeNavigationTest {
    @get:Rule(order = 0)
    var hiltRule: HiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<HiltComponentActivity>, HiltComponentActivity> =
        createAndroidComposeRule<HiltComponentActivity>()

    private lateinit var testNavHostController: TestNavHostController

    private val testContext: Context = ApplicationProvider.getApplicationContext()
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(
        context = testDispatcher + Job(),
    )

    // region nodes declaration
    private lateinit var accountsScreen: SemanticsNodeInteraction
    private lateinit var accountsScreenContent: SemanticsNodeInteraction
    private lateinit var accountsFloatingActionButton: SemanticsNodeInteraction

    private lateinit var addOrEditAccountScreen: SemanticsNodeInteraction
    private lateinit var addOrEditAccountScreenContent: SemanticsNodeInteraction
    private lateinit var addAccountAppbarTitle: SemanticsNodeInteraction
    private lateinit var editAccountAppbarTitle: SemanticsNodeInteraction

    private lateinit var addOrEditCategoryScreen: SemanticsNodeInteraction
    private lateinit var addOrEditCategoryScreenContent: SemanticsNodeInteraction
    private lateinit var addCategoryAppbarTitle: SemanticsNodeInteraction
    private lateinit var editCategoryAppbarTitle: SemanticsNodeInteraction

    private lateinit var addOrEditTransactionScreen: SemanticsNodeInteraction
    private lateinit var addOrEditTransactionScreenContent: SemanticsNodeInteraction
    private lateinit var addTransactionAppbarTitle: SemanticsNodeInteraction
    private lateinit var editTransactionAppbarTitle: SemanticsNodeInteraction

    private lateinit var addOrEditTransactionForScreen: SemanticsNodeInteraction
    private lateinit var addOrEditTransactionForScreenContent: SemanticsNodeInteraction
    private lateinit var addTransactionForAppbarTitle: SemanticsNodeInteraction
    private lateinit var editTransactionForAppbarTitle: SemanticsNodeInteraction

    private lateinit var analysisScreen: SemanticsNodeInteraction
    private lateinit var analysisScreenContent: SemanticsNodeInteraction

    private lateinit var categoriesScreen: SemanticsNodeInteraction
    private lateinit var categoriesScreenContent: SemanticsNodeInteraction
    private lateinit var categoriesFloatingActionButton: SemanticsNodeInteraction

    private lateinit var homeScreen: SemanticsNodeInteraction
    private lateinit var homeScreenContent: SemanticsNodeInteraction
    private lateinit var homeTotalBalanceCard: SemanticsNodeInteraction
    private lateinit var homeOverviewCard: SemanticsNodeInteraction
    private lateinit var homeRecentTransactions: SemanticsNodeInteraction
    private lateinit var homeFloatingActionButton: SemanticsNodeInteraction
    private lateinit var homeAppbarSettings: SemanticsNodeInteraction

    private lateinit var openSourceLicensesScreen: SemanticsNodeInteraction
    private lateinit var openSourceLicensesScreenContent: SemanticsNodeInteraction

    private lateinit var settingsScreen: SemanticsNodeInteraction
    private lateinit var settingsScreenContent: SemanticsNodeInteraction
    private lateinit var settingsLinearProgressIndicator: SemanticsNodeInteraction

    // TODO(Abhi): Fix these to single node
    private lateinit var settingsCategoriesListItem: SemanticsNodeInteractionCollection
    private lateinit var settingsAccountsListItem: SemanticsNodeInteractionCollection
    private lateinit var settingsTransactionForListItem: SemanticsNodeInteractionCollection
    private lateinit var settingsOpenSourceLicensesListItem: SemanticsNodeInteractionCollection

    private lateinit var transactionForValuesScreen: SemanticsNodeInteraction
    private lateinit var transactionForValuesScreenContent: SemanticsNodeInteraction
    private lateinit var transactionForValuesFloatingActionButton: SemanticsNodeInteraction

    private lateinit var transactionsScreen: SemanticsNodeInteraction
    private lateinit var transactionsScreenContent: SemanticsNodeInteraction
    private lateinit var transactionsFloatingActionButton: SemanticsNodeInteraction

    private lateinit var viewTransactionScreen: SemanticsNodeInteraction
    private lateinit var viewTransactionScreenContent: SemanticsNodeInteraction
    // endregion

    @Before
    fun setUp() {
        hiltRule.inject()

        initNodes()

        composeTestRule.setContent {
            testNavHostController = TestNavHostController(
                context = LocalContext.current,
            ).apply {
                navigatorProvider.addNavigator(
                    navigator = ComposeNavigator(),
                )
            }
            MyApp(
                myLogger = FakeMyLoggerImpl(),
                navHostController = testNavHostController,
            )
        }
    }

    @Test
    fun verifyStartDestination() = runTestWithTimeout {
        testNavHostController.assertCurrentRouteName(
            expectedRouteName = Screen.Home.route,
        )
    }

    @Test
    fun accountsScreen_fromHomeScreen() = runTestWithTimeout {
        testNavHostController.assertCurrentRouteName(
            expectedRouteName = Screen.Home.route,
        )

        homeTotalBalanceCard.assertIsDisplayed()
        homeTotalBalanceCard.performClick()
        homeTotalBalanceCard.performClick()

        testNavHostController.assertCurrentRouteName(
            expectedRouteName = Screen.Accounts.route,
        )
    }

    @Test
    fun accountsScreen_fromSettingsScreen() = runTestWithTimeout {
        navigateToSettingsScreen()
        settingsAccountsListItem.onFirst().performClick()

        testNavHostController.assertCurrentRouteName(
            expectedRouteName = Screen.Accounts.route,
        )
    }

    @Test
    fun addAccountScreen_fromAccountsScreen() = runTestWithTimeout {
        navigateToAccountsScreen()
        accountsFloatingActionButton.assertIsDisplayed()
        accountsFloatingActionButton.performClick()

        testNavHostController.assertCurrentRouteName(
            expectedRouteName = Screen.AddAccount.route,
        )
    }

    @Test
    fun addCategoryScreen_fromCategoriesScreen() = runTestWithTimeout {
        navigateToCategoriesScreen()
        categoriesFloatingActionButton.performClick()

        testNavHostController.assertCurrentRouteName(
            expectedRouteName = Screen.AddCategory.route,
            partialMatch = true,
        )
    }

    @Test
    fun addTransactionScreen_fromHomeScreen() = runTestWithTimeout {
        testNavHostController.assertCurrentRouteName(
            expectedRouteName = Screen.Home.route,
        )

        homeFloatingActionButton.performClick()

        testNavHostController.assertCurrentRouteName(
            expectedRouteName = Screen.AddTransaction.route,
            partialMatch = true,
        )
    }

    @Test
    fun addTransactionForScreen_fromTransactionForValuesScreen() = runTestWithTimeout {
        navigateToTransactionForValuesScreen()
        transactionForValuesFloatingActionButton.performClick()

        testNavHostController.assertCurrentRouteName(
            expectedRouteName = Screen.AddTransactionFor.route,
        )
    }

    @Test
    fun analysisScreen_fromHomeScreen() = runTestWithTimeout {
        testNavHostController.assertCurrentRouteName(
            expectedRouteName = Screen.Home.route,
        )

        homeOverviewCard.assertIsDisplayed()
        homeOverviewCard.performClick()

        testNavHostController.assertCurrentRouteName(
            expectedRouteName = Screen.Analysis.route,
        )
    }

    @Test
    fun categoriesScreen_fromSettingsScreen() = runTestWithTimeout {
        navigateToSettingsScreen()
        settingsCategoriesListItem.onFirst().performClick()

        testNavHostController.assertCurrentRouteName(
            expectedRouteName = Screen.Categories.route,
        )
    }

    @Test
    fun editAccountScreen_fromAccountsScreen() = runTestWithTimeout {
        homeScreen.assertIsDisplayed()

        homeTotalBalanceCard.performClick()
        accountsFloatingActionButton.performClick()

        addOrEditAccountScreen.assertIsDisplayed()
        addOrEditAccountScreenContent.assertIsDisplayed()
        editAccountAppbarTitle.assertIsDisplayed()
    }

    @Test
    fun editCategoryScreen_fromCategories() = runTestWithTimeout {
        homeScreen.assertIsDisplayed()

        addOrEditCategoryScreen.assertIsDisplayed()
        addOrEditCategoryScreenContent.assertIsDisplayed()
        editCategoryAppbarTitle.assertIsDisplayed()
    }

    @Test
    fun editTransactionScreen_fromTransactionsScreen() = runTestWithTimeout {
        homeScreen.assertIsDisplayed()

        homeFloatingActionButton.performClick()

        addOrEditTransactionScreen.assertIsDisplayed()
        addOrEditTransactionScreenContent.assertIsDisplayed()
        editTransactionAppbarTitle.assertIsDisplayed()
    }

    @Test
    fun editTransactionForScreen_fromTransactionForValuesScreen() = runTestWithTimeout {
        homeScreen.assertIsDisplayed()



        addOrEditTransactionForScreen.assertIsDisplayed()
        addOrEditTransactionForScreenContent.assertIsDisplayed()
        editTransactionForAppbarTitle.assertIsDisplayed()
    }

    @Test
    fun homeScreen() = runTestWithTimeout {
        testNavHostController.assertCurrentRouteName(
            expectedRouteName = Screen.Home.route,
        )

//        printToLog()
//        homeScreen.assertIsDisplayed()
//        homeScreenContent.assertIsDisplayed()
//        // homeTotalBalanceCard.assertIsDisplayed()
//        // homeOverviewCard.assertIsDisplayed()
//        homeRecentTransactions.assertIsDisplayed()
//        homeFloatingActionButton.assertIsDisplayed()
//        homeAppbarSettings.assertIsDisplayed()
    }

    @Test
    fun openSourceLicensesScreen_fromSettingsScreen() = runTestWithTimeout {
        navigateToSettingsScreen()
        settingsScreenContent.performScrollToNode(
            matcher = hasText(
                text = testContext.getString(SettingsR.string.screen_settings_open_source_licenses),
            ),
        )
        settingsOpenSourceLicensesListItem.onFirst().performClick()

        testNavHostController.assertCurrentRouteName(
            expectedRouteName = Screen.OpenSourceLicenses.route,
        )
    }

    @Test
    fun settingsScreen_fromHomeScreen() = runTestWithTimeout {
        testNavHostController.assertCurrentRouteName(
            expectedRouteName = Screen.Home.route,
        )

        homeAppbarSettings.assertHasClickAction()
        homeAppbarSettings.performClick()

        testNavHostController.assertCurrentRouteName(
            expectedRouteName = Screen.Settings.route,
        )

//        settingsScreen.assertIsDisplayed()
//        settingsScreenContent.assertIsDisplayed()
//        settingsCategoriesListItem.onFirst().assertIsDisplayed()
//        settingsAccountsListItem.onFirst().assertIsDisplayed()
//        settingsTransactionForListItem.onFirst().assertIsDisplayed()
//
//        settingsScreenContent.performScrollToNode(
//            matcher = hasText(
//                text = testContext.getString(SettingsR.string.screen_settings_open_source_licenses),
//            ),
//        )
//        settingsOpenSourceLicensesListItem.onFirst().assertIsDisplayed()
    }

    @Test
    fun transactionForValuesScreen_fromSettingsScreen() = runTestWithTimeout {
        navigateToSettingsScreen()
        settingsTransactionForListItem.onFirst().performClick()

        testNavHostController.assertCurrentRouteName(
            expectedRouteName = Screen.TransactionForValues.route,
        )
    }

    @Test
    fun transactionsScreen_fromHomeScreen() = runTestWithTimeout {
        homeScreen.assertIsDisplayed()

        homeRecentTransactions.assertHasClickAction()
        homeRecentTransactions.performClick()

        testNavHostController.assertCurrentRouteName(
            expectedRouteName = Screen.Transactions.route,
        )

//        transactionsScreen.assertIsDisplayed()
//        transactionsScreenContent.assertIsDisplayed()
//        transactionsFloatingActionButton.assertIsDisplayed()
    }

    @Test
    fun viewTransactionScreen_fromTransactionsScreen() = runTestWithTimeout {
        homeScreen.assertIsDisplayed()

        viewTransactionScreen.assertIsDisplayed()
        viewTransactionScreenContent.assertIsDisplayed()
    }

    // region navigation methods
    private fun navigateToAccountsScreen() {
        navigateToSettingsScreen()
        settingsAccountsListItem.onFirst().performClick()

        testNavHostController.assertCurrentRouteName(
            expectedRouteName = Screen.Accounts.route,
        )
    }

    private fun navigateToCategoriesScreen() {
        navigateToSettingsScreen()
        settingsCategoriesListItem.onFirst().performClick()

        testNavHostController.assertCurrentRouteName(
            expectedRouteName = Screen.Categories.route,
        )
    }

    private fun navigateToTransactionForValuesScreen() {
        navigateToSettingsScreen()
        settingsTransactionForListItem.onFirst().performClick()

        testNavHostController.assertCurrentRouteName(
            expectedRouteName = Screen.TransactionForValues.route,
        )
    }

    private fun navigateToSettingsScreen() {
        testNavHostController.assertCurrentRouteName(
            expectedRouteName = Screen.Home.route,
        )

        homeAppbarSettings.assertHasClickAction()
        homeAppbarSettings.performClick()

        testNavHostController.assertCurrentRouteName(
            expectedRouteName = Screen.Settings.route,
        )
    }
    // endregion

    // region initNodes
    private fun initNodes() {
        accountsScreen = composeTestRule.onNodeWithTag(
            testTag = SCREEN_ACCOUNTS,
        )
        accountsScreenContent = composeTestRule.onNodeWithTag(
            testTag = SCREEN_CONTENT_ACCOUNTS,
        )
        accountsFloatingActionButton = composeTestRule.onNodeWithContentDescription(
            testContext.getString(AccountsR.string.screen_accounts_floating_action_button_content_description)
        )

        addOrEditAccountScreen = composeTestRule.onNodeWithTag(
            testTag = SCREEN_ADD_OR_EDIT_ACCOUNT,
        )
        addOrEditAccountScreenContent =
            composeTestRule.onNodeWithTag(SCREEN_CONTENT_ADD_OR_EDIT_ACCOUNT)
        addAccountAppbarTitle = composeTestRule.onNodeWithText(
            testContext.getString(AccountsR.string.screen_add_account_appbar_title)
        )
        editAccountAppbarTitle = composeTestRule.onNodeWithText(
            testContext.getString(AccountsR.string.screen_edit_account_appbar_title)
        )

        addOrEditCategoryScreen = composeTestRule.onNodeWithTag(SCREEN_ADD_OR_EDIT_CATEGORY)
        addOrEditCategoryScreenContent =
            composeTestRule.onNodeWithTag(SCREEN_CONTENT_ADD_OR_EDIT_CATEGORY)
        addCategoryAppbarTitle = composeTestRule.onNodeWithText(
            testContext.getString(CategoriesR.string.screen_add_category_appbar_title)
        )
        editCategoryAppbarTitle = composeTestRule.onNodeWithText(
            testContext.getString(CategoriesR.string.screen_edit_category_appbar_title)
        )

        addOrEditTransactionScreen = composeTestRule.onNodeWithTag(SCREEN_ADD_OR_EDIT_TRANSACTION)
        addOrEditTransactionScreenContent =
            composeTestRule.onNodeWithTag(SCREEN_CONTENT_ADD_OR_EDIT_TRANSACTION)
        addTransactionAppbarTitle = composeTestRule.onNodeWithText(
            testContext.getString(TransactionsR.string.screen_add_transaction_appbar_title)
        )
        editTransactionAppbarTitle = composeTestRule.onNodeWithText(
            testContext.getString(TransactionsR.string.screen_edit_transaction_appbar_title)
        )

        addOrEditTransactionForScreen =
            composeTestRule.onNodeWithTag(SCREEN_ADD_OR_EDIT_TRANSACTION_FOR)
        addOrEditTransactionForScreenContent =
            composeTestRule.onNodeWithTag(SCREEN_CONTENT_ADD_OR_EDIT_TRANSACTION_FOR)
        addTransactionForAppbarTitle = composeTestRule.onNodeWithText(
            testContext.getString(TransactionForR.string.screen_add_transaction_for_appbar_title)
        )
        editTransactionForAppbarTitle = composeTestRule.onNodeWithText(
            testContext.getString(TransactionForR.string.screen_edit_transaction_for_appbar_title)
        )

        analysisScreen = composeTestRule.onNodeWithTag(SCREEN_ANALYSIS)
        analysisScreenContent = composeTestRule.onNodeWithTag(SCREEN_CONTENT_ANALYSIS)

        categoriesScreen = composeTestRule.onNodeWithTag(SCREEN_CATEGORIES)
        categoriesScreenContent = composeTestRule.onNodeWithTag(SCREEN_CONTENT_CATEGORIES)
        categoriesFloatingActionButton = composeTestRule.onNodeWithContentDescription(
            testContext.getString(CategoriesR.string.screen_categories_floating_action_button_content_description)
        )

        homeScreen = composeTestRule.onNodeWithTag(SCREEN_HOME)
        homeScreenContent = composeTestRule.onNodeWithTag(SCREEN_CONTENT_HOME)
        homeTotalBalanceCard = composeTestRule.onNodeWithTag(COMPONENT_TOTAL_BALANCE_CARD)
        homeOverviewCard = composeTestRule.onNodeWithTag(COMPONENT_OVERVIEW_CARD)
        homeRecentTransactions = composeTestRule.onNodeWithText(
            testContext.getString(CoreUiR.string.screen_home_recent_transactions)
        )
        homeFloatingActionButton = composeTestRule.onNodeWithContentDescription(
            testContext.getString(HomeR.string.screen_home_floating_action_button_content_description)
        )
        homeAppbarSettings = composeTestRule.onNodeWithContentDescription(
            testContext.getString(HomeR.string.screen_home_appbar_settings)
        )

        openSourceLicensesScreen = composeTestRule.onNodeWithTag(SCREEN_OPEN_SOURCE_LICENSES)
        openSourceLicensesScreenContent =
            composeTestRule.onNodeWithTag(SCREEN_CONTENT_OPEN_SOURCE_LICENSES)

        settingsScreen = composeTestRule.onNodeWithTag(SCREEN_SETTINGS)
        settingsScreenContent = composeTestRule.onNodeWithTag(
            testTag = SCREEN_CONTENT_SETTINGS,
            useUnmergedTree = true,
        )
        settingsLinearProgressIndicator = composeTestRule.onNodeWithTag(
            testTag = testContext.getString(SettingsR.string.screen_settings_linear_progress_indicator_test_tag),
        )
        settingsCategoriesListItem = composeTestRule.onAllNodesWithText(
            testContext.getString(SettingsR.string.screen_settings_categories)
        )
        settingsAccountsListItem = composeTestRule.onAllNodesWithText(
            testContext.getString(SettingsR.string.screen_settings_accounts)
        )
        settingsTransactionForListItem = composeTestRule.onAllNodesWithText(
            testContext.getString(SettingsR.string.screen_settings_transaction_for)
        )
        settingsOpenSourceLicensesListItem = composeTestRule.onAllNodesWithText(
            testContext.getString(SettingsR.string.screen_settings_open_source_licenses)
        )

        transactionForValuesScreen = composeTestRule.onNodeWithTag(SCREEN_TRANSACTION_FOR_VALUES)
        transactionForValuesScreenContent =
            composeTestRule.onNodeWithTag(SCREEN_CONTENT_TRANSACTION_FOR_VALUES)
        transactionForValuesFloatingActionButton = composeTestRule.onNodeWithContentDescription(
            testContext.getString(TransactionForR.string.screen_transaction_for_values_floating_action_button_content_description)
        )

        transactionsScreen = composeTestRule.onNodeWithTag(SCREEN_TRANSACTIONS)
        transactionsScreenContent = composeTestRule.onNodeWithTag(SCREEN_CONTENT_TRANSACTIONS)
        transactionsFloatingActionButton = composeTestRule.onNodeWithContentDescription(
            testContext.getString(TransactionsR.string.screen_transactions_floating_action_button_content_description)
        )

        viewTransactionScreen = composeTestRule.onNodeWithTag(SCREEN_VIEW_TRANSACTION)
        viewTransactionScreenContent =
            composeTestRule.onNodeWithTag(SCREEN_CONTENT_VIEW_TRANSACTION)
    }
    // endregion

    // region extension methods
    private fun printToLog() {
        composeTestRule.onRoot().printToLog("Abhi")
    }

    private fun runTestWithTimeout(
        block: suspend TestScope.() -> Unit,
    ) = testScope.runTest(
        timeout = 10.seconds,
    ) {
        block()
    }

    private fun NavController.assertCurrentRouteName(
        expectedRouteName: String,
        partialMatch: Boolean = false,
    ) {
        val routeToMatch = if (partialMatch) {
            currentRoute.split("/").first()
        } else {
            currentRoute
        }
        println("Abhi - expectedRouteName: $expectedRouteName routeToMatch: $routeToMatch")
        waitForRoute(
            expectedRouteName = expectedRouteName,
            routeToMatch = routeToMatch,
        )
        assertEquals(
            expectedRouteName,
            routeToMatch,
        )
    }

    private fun waitForRoute(
        expectedRouteName: String,
        routeToMatch: String,
    ) {
        composeTestRule.waitUntil(
            timeoutMillis = TIMEOUT,
        ) {
            expectedRouteName == routeToMatch
        }
    }

    private val NavController.currentRoute: String
        get() = currentBackStackEntry?.destination?.route.orEmpty()
    // endregion

    companion object {
        private const val TIMEOUT = 3_000L
    }
}

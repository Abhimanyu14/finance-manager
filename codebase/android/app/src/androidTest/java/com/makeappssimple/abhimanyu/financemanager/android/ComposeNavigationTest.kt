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
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Screen
import com.makeappssimple.abhimanyu.financemanager.android.navigation.MyNavHost
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.R as CoreUiR
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.R as AccountsR
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.R as CategoriesR
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.R as HomeR
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.R as SettingsR
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.R as TransactionForR
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.R as TransactionsR

@Ignore("Fix Navigation")
@HiltAndroidTest
internal class ComposeNavigationTest {
    @get:Rule(order = 0)
    var hiltRule: HiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<HiltComponentActivity>, HiltComponentActivity> =
        createAndroidComposeRule<HiltComponentActivity>()

    private lateinit var testNavHostController: TestNavHostController

    private lateinit var context: Context

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

    @Before
    fun setUp() {
        hiltRule.inject()
        context = ApplicationProvider.getApplicationContext()

        initNodes()

        composeTestRule.setContent {
            testNavHostController = TestNavHostController(
                context = LocalContext.current,
            ).apply {
                navigatorProvider.addNavigator(
                    navigator = ComposeNavigator(),
                )
            }
            MyNavHost(
                navHostController = testNavHostController,
            )
        }
    }

    @Test
    fun verifyStartDestination() = runTest {
        testNavHostController.assertCurrentRouteName(
            expectedRouteName = Screen.Home.route,
        )
    }

    @Test
    fun accountsScreenTest() = runTest {
        homeScreen.assertIsDisplayed()

        homeTotalBalanceCard.performClick()

        assertEquals(
            Screen.Accounts.route,
            testNavHostController.currentBackStackEntry?.destination?.route,
        )

//        accountsScreen.assertIsDisplayed()
//        accountsScreenContent.assertIsDisplayed()
//        accountsFloatingActionButton.assertIsDisplayed()
    }

    @Test
    fun accountsScreen_fromSettings() = runTest {
        homeScreen.assertIsDisplayed()

        homeAppbarSettings.performClick()
        settingsAccountsListItem.onFirst().performClick()

        accountsScreen.assertIsDisplayed()
        accountsScreenContent.assertIsDisplayed()
        accountsFloatingActionButton.assertIsDisplayed()
    }

    @Test
    fun addAccountScreenTest() = runTest {
        homeScreen.assertIsDisplayed()

        homeTotalBalanceCard.performClick()
        accountsFloatingActionButton.performClick()

        addOrEditAccountScreen.assertIsDisplayed()
        addOrEditAccountScreenContent.assertIsDisplayed()
        addAccountAppbarTitle.assertIsDisplayed()
    }

    @Test
    fun addCategoryScreenTest() = runTest {
        homeScreen.assertIsDisplayed()

        homeAppbarSettings.performClick()
        settingsCategoriesListItem.onFirst().performClick()
        categoriesFloatingActionButton.performClick()

        addOrEditCategoryScreen.assertIsDisplayed()
        addOrEditCategoryScreenContent.assertIsDisplayed()
        addCategoryAppbarTitle.assertIsDisplayed()
    }

    @Test
    fun addTransactionScreenTest() = runTest {
        homeScreen.assertIsDisplayed()

        homeFloatingActionButton.performClick()

        testNavHostController.assertCurrentRouteName(
            expectedRouteName = Screen.AddTransaction.route,
        )

//        addOrEditTransactionScreen.assertIsDisplayed()
//        addOrEditTransactionScreenContent.assertIsDisplayed()
//        addTransactionAppbarTitle.assertIsDisplayed()
    }

    @Test
    fun addTransactionForScreenTest() = runTest {
        homeScreen.assertIsDisplayed()

        homeAppbarSettings.performClick()
        settingsTransactionForListItem.onFirst().performClick()
        transactionForValuesFloatingActionButton.performClick()

        addOrEditTransactionForScreen.assertIsDisplayed()
        addOrEditTransactionForScreenContent.assertIsDisplayed()
        addTransactionForAppbarTitle.assertIsDisplayed()
    }

    @Test
    fun analysisScreenTest() = runTest {
        homeScreen.assertIsDisplayed()

        homeOverviewCard.performClick()

        analysisScreen.assertIsDisplayed()
        analysisScreenContent.assertIsDisplayed()
    }

    @Test
    fun categoriesScreenTest() = runTest {
        homeScreen.assertIsDisplayed()

        homeAppbarSettings.performClick()
        settingsCategoriesListItem.onFirst().performClick()

        categoriesScreen.assertIsDisplayed()
        categoriesScreenContent.assertIsDisplayed()
        categoriesFloatingActionButton.assertIsDisplayed()
    }

    @Test
    fun editAccountScreenTest() = runTest {
        homeScreen.assertIsDisplayed()

        homeTotalBalanceCard.performClick()
        accountsFloatingActionButton.performClick()

        addOrEditAccountScreen.assertIsDisplayed()
        addOrEditAccountScreenContent.assertIsDisplayed()
        editAccountAppbarTitle.assertIsDisplayed()
    }

    @Test
    fun editCategoryScreenTest() = runTest {
        homeScreen.assertIsDisplayed()

        addOrEditCategoryScreen.assertIsDisplayed()
        addOrEditCategoryScreenContent.assertIsDisplayed()
        editCategoryAppbarTitle.assertIsDisplayed()
    }

    @Test
    fun editTransactionScreenTest() = runTest {
        homeScreen.assertIsDisplayed()

        homeFloatingActionButton.performClick()

        addOrEditTransactionScreen.assertIsDisplayed()
        addOrEditTransactionScreenContent.assertIsDisplayed()
        editTransactionAppbarTitle.assertIsDisplayed()
    }

    @Test
    fun editTransactionForScreenTest() = runTest {
        homeScreen.assertIsDisplayed()



        addOrEditTransactionForScreen.assertIsDisplayed()
        addOrEditTransactionForScreenContent.assertIsDisplayed()
        editTransactionForAppbarTitle.assertIsDisplayed()
    }

    @Test
    fun homeScreenTest() = runTest {
        printToLog()
        homeScreen.assertIsDisplayed()
        homeScreenContent.assertIsDisplayed()
        // homeTotalBalanceCard.assertIsDisplayed()
        // homeOverviewCard.assertIsDisplayed()
        homeRecentTransactions.assertIsDisplayed()
        homeFloatingActionButton.assertIsDisplayed()
        homeAppbarSettings.assertIsDisplayed()
    }

    @Test
    fun openSourceLicensesScreenTest() = runTest {
        homeScreen.assertIsDisplayed()

        homeAppbarSettings.performClick()
        settingsScreenContent.performScrollToNode(
            matcher = hasText(
                text = context.getString(SettingsR.string.screen_settings_open_source_licenses),
            ),
        )
        settingsOpenSourceLicensesListItem.onFirst().performClick()

        openSourceLicensesScreen.assertIsDisplayed()
        openSourceLicensesScreenContent.assertIsDisplayed()
    }

    @Test
    fun settingsScreenTest() = runTest {
        homeScreen.assertIsDisplayed()

        homeAppbarSettings.assertHasClickAction()
        homeAppbarSettings.performClick()
        Thread.sleep(2000)
        composeTestRule.awaitIdle()

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
//                text = context.getString(SettingsR.string.screen_settings_open_source_licenses),
//            ),
//        )
//        settingsOpenSourceLicensesListItem.onFirst().assertIsDisplayed()
    }

    @Test
    fun transactionForValuesScreenTest() = runTest {
        homeScreen.assertIsDisplayed()

        homeAppbarSettings.performClick()
        settingsTransactionForListItem.onFirst().performClick()

        transactionForValuesScreen.assertIsDisplayed()
        transactionForValuesScreenContent.assertIsDisplayed()
        transactionForValuesFloatingActionButton.assertIsDisplayed()
    }

    @Test
    fun transactionsScreenTest() = runTest {
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
    fun viewTransactionScreenTest() = runTest {
        homeScreen.assertIsDisplayed()

        viewTransactionScreen.assertIsDisplayed()
        viewTransactionScreenContent.assertIsDisplayed()
    }

    private fun initNodes() = runTest {
        accountsScreen = composeTestRule.onNodeWithTag(SCREEN_ACCOUNTS)
        accountsScreenContent = composeTestRule.onNodeWithTag(SCREEN_CONTENT_ACCOUNTS)
        accountsFloatingActionButton = composeTestRule.onNodeWithContentDescription(
            context.getString(AccountsR.string.screen_accounts_floating_action_button_content_description)
        )

        addOrEditAccountScreen = composeTestRule.onNodeWithTag(SCREEN_ADD_OR_EDIT_ACCOUNT)
        addOrEditAccountScreenContent =
            composeTestRule.onNodeWithTag(SCREEN_CONTENT_ADD_OR_EDIT_ACCOUNT)
        addAccountAppbarTitle = composeTestRule.onNodeWithText(
            context.getString(AccountsR.string.screen_add_account_appbar_title)
        )
        editAccountAppbarTitle = composeTestRule.onNodeWithText(
            context.getString(AccountsR.string.screen_edit_account_appbar_title)
        )

        addOrEditCategoryScreen = composeTestRule.onNodeWithTag(SCREEN_ADD_OR_EDIT_CATEGORY)
        addOrEditCategoryScreenContent =
            composeTestRule.onNodeWithTag(SCREEN_CONTENT_ADD_OR_EDIT_CATEGORY)
        addCategoryAppbarTitle = composeTestRule.onNodeWithText(
            context.getString(CategoriesR.string.screen_add_category_appbar_title)
        )
        editCategoryAppbarTitle = composeTestRule.onNodeWithText(
            context.getString(CategoriesR.string.screen_edit_category_appbar_title)
        )

        addOrEditTransactionScreen = composeTestRule.onNodeWithTag(SCREEN_ADD_OR_EDIT_TRANSACTION)
        addOrEditTransactionScreenContent =
            composeTestRule.onNodeWithTag(SCREEN_CONTENT_ADD_OR_EDIT_TRANSACTION)
        addTransactionAppbarTitle = composeTestRule.onNodeWithText(
            context.getString(TransactionsR.string.screen_add_transaction_appbar_title)
        )
        editTransactionAppbarTitle = composeTestRule.onNodeWithText(
            context.getString(TransactionsR.string.screen_edit_transaction_appbar_title)
        )

        addOrEditTransactionForScreen =
            composeTestRule.onNodeWithTag(SCREEN_ADD_OR_EDIT_TRANSACTION_FOR)
        addOrEditTransactionForScreenContent =
            composeTestRule.onNodeWithTag(SCREEN_CONTENT_ADD_OR_EDIT_TRANSACTION_FOR)
        addTransactionForAppbarTitle = composeTestRule.onNodeWithText(
            context.getString(TransactionForR.string.screen_add_transaction_for_appbar_title)
        )
        editTransactionForAppbarTitle = composeTestRule.onNodeWithText(
            context.getString(TransactionForR.string.screen_edit_transaction_for_appbar_title)
        )

        analysisScreen = composeTestRule.onNodeWithTag(SCREEN_ANALYSIS)
        analysisScreenContent = composeTestRule.onNodeWithTag(SCREEN_CONTENT_ANALYSIS)

        categoriesScreen = composeTestRule.onNodeWithTag(SCREEN_CATEGORIES)
        categoriesScreenContent = composeTestRule.onNodeWithTag(SCREEN_CONTENT_CATEGORIES)
        categoriesFloatingActionButton = composeTestRule.onNodeWithContentDescription(
            context.getString(CategoriesR.string.screen_categories_floating_action_button_content_description)
        )

        homeScreen = composeTestRule.onNodeWithTag(SCREEN_HOME)
        homeScreenContent = composeTestRule.onNodeWithTag(SCREEN_CONTENT_HOME)
        homeTotalBalanceCard = composeTestRule.onNodeWithTag(COMPONENT_TOTAL_BALANCE_CARD)
        homeOverviewCard = composeTestRule.onNodeWithTag(COMPONENT_OVERVIEW_CARD)
        homeRecentTransactions = composeTestRule.onNodeWithText(
            context.getString(CoreUiR.string.screen_home_recent_transactions)
        )
        homeFloatingActionButton = composeTestRule.onNodeWithContentDescription(
            context.getString(HomeR.string.screen_home_floating_action_button_content_description)
        )
        homeAppbarSettings = composeTestRule.onNodeWithContentDescription(
            context.getString(HomeR.string.screen_home_appbar_settings)
        )

        openSourceLicensesScreen = composeTestRule.onNodeWithTag(SCREEN_OPEN_SOURCE_LICENSES)
        openSourceLicensesScreenContent =
            composeTestRule.onNodeWithTag(SCREEN_CONTENT_OPEN_SOURCE_LICENSES)

        settingsScreen = composeTestRule.onNodeWithTag(SCREEN_SETTINGS)
        settingsScreenContent = composeTestRule.onNodeWithTag(SCREEN_CONTENT_SETTINGS)
        settingsLinearProgressIndicator = composeTestRule.onNodeWithTag(
            context.getString(SettingsR.string.screen_settings_linear_progress_indicator_test_tag)
        )
        settingsCategoriesListItem = composeTestRule.onAllNodesWithText(
            context.getString(SettingsR.string.screen_settings_categories)
        )
        settingsAccountsListItem = composeTestRule.onAllNodesWithText(
            context.getString(SettingsR.string.screen_settings_accounts)
        )
        settingsTransactionForListItem = composeTestRule.onAllNodesWithText(
            context.getString(SettingsR.string.screen_settings_transaction_for)
        )
        settingsOpenSourceLicensesListItem = composeTestRule.onAllNodesWithText(
            context.getString(SettingsR.string.screen_settings_open_source_licenses)
        )

        transactionForValuesScreen = composeTestRule.onNodeWithTag(SCREEN_TRANSACTION_FOR_VALUES)
        transactionForValuesScreenContent =
            composeTestRule.onNodeWithTag(SCREEN_CONTENT_TRANSACTION_FOR_VALUES)
        transactionForValuesFloatingActionButton = composeTestRule.onNodeWithContentDescription(
            context.getString(TransactionForR.string.screen_transaction_for_values_floating_action_button_content_description)
        )

        transactionsScreen = composeTestRule.onNodeWithTag(SCREEN_TRANSACTIONS)
        transactionsScreenContent = composeTestRule.onNodeWithTag(SCREEN_CONTENT_TRANSACTIONS)
        transactionsFloatingActionButton = composeTestRule.onNodeWithContentDescription(
            context.getString(TransactionsR.string.screen_transactions_floating_action_button_content_description)
        )

        viewTransactionScreen = composeTestRule.onNodeWithTag(SCREEN_VIEW_TRANSACTION)
        viewTransactionScreenContent =
            composeTestRule.onNodeWithTag(SCREEN_CONTENT_VIEW_TRANSACTION)
    }

    private fun printToLog() = runTest {
        composeTestRule.onRoot().printToLog("Abhi")
    }

    private fun NavController.assertCurrentRouteName(
        expectedRouteName: String,
    ) = runTest {
        assertEquals(
            expectedRouteName,
            currentBackStackEntry?.destination?.route,
        )
    }
}

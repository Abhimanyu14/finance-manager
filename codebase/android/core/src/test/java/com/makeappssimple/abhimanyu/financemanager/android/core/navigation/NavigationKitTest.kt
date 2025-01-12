package com.makeappssimple.abhimanyu.financemanager.android.core.navigation

import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import kotlin.time.Duration.Companion.seconds

internal class NavigationKitTest {
    // region testing
    private lateinit var standardTestDispatcher: TestDispatcher
    private lateinit var testScope: TestScope
    // endregion

    // region SUT
    private lateinit var navigationKit: NavigationKit
    // endregion

    @Test
    fun navigateToAccountsScreen() = runTestWithTimeout {
        navigationKit.navigateToAccountsScreen()

        Assert.assertEquals(
            MyNavigationDirections.Accounts,
            navigationKit.command.first(),
        )
    }

    @Test
    fun navigateToAddAccountScreen() = runTestWithTimeout {
        navigationKit.navigateToAddAccountScreen()

        Assert.assertEquals(
            MyNavigationDirections.AddAccount,
            navigationKit.command.first(),
        )
    }

    @Test
    fun navigateToAddCategoryScreen() = runTestWithTimeout {
        val transactionType = TransactionType.INCOME.title
        navigationKit.navigateToAddCategoryScreen(
            transactionType = transactionType,
        )

        Assert.assertEquals(
            MyNavigationDirections.AddCategory(
                transactionType = TransactionType.INCOME.title,
            ),
            navigationKit.command.first(),
        )
    }

    @Test
    fun navigateToAddTransactionScreen() = runTestWithTimeout {
        val transactionId = 123
        navigationKit.navigateToAddTransactionScreen(
            transactionId = transactionId,
        )

        Assert.assertEquals(
            MyNavigationDirections.AddTransaction(
                transactionId = transactionId,
            ),
            navigationKit.command.first(),
        )
    }

    @Test
    fun navigateToAddTransactionForScreen() = runTestWithTimeout {
        navigationKit.navigateToAddTransactionForScreen()

        Assert.assertEquals(
            MyNavigationDirections.AddTransactionFor,
            navigationKit.command.first(),
        )
    }

    @Test
    fun navigateToAnalysisScreen() = runTestWithTimeout {
        navigationKit.navigateToAnalysisScreen()

        Assert.assertEquals(
            MyNavigationDirections.Analysis,
            navigationKit.command.first(),
        )
    }

    @Test
    fun navigateToCategoriesScreen() = runTestWithTimeout {
        navigationKit.navigateToCategoriesScreen()

        Assert.assertEquals(
            MyNavigationDirections.Categories,
            navigationKit.command.first(),
        )
    }

    @Test
    fun navigateToEditAccountScreen() = runTestWithTimeout {
        val accountId = 123
        navigationKit.navigateToEditAccountScreen(
            accountId = accountId,
        )

        Assert.assertEquals(
            MyNavigationDirections.EditAccount(
                accountId = accountId,
            ),
            navigationKit.command.first(),
        )
    }

    @Test
    fun navigateToEditCategoryScreen() = runTestWithTimeout {
        val categoryId = 123
        navigationKit.navigateToEditCategoryScreen(
            categoryId = categoryId,
        )

        Assert.assertEquals(
            MyNavigationDirections.EditCategory(
                categoryId = categoryId,
            ),
            navigationKit.command.first(),
        )
    }

    @Test
    fun navigateToEditTransactionScreen() = runTestWithTimeout {
        val transactionId = 123
        navigationKit.navigateToEditTransactionScreen(
            transactionId = transactionId,
        )

        Assert.assertEquals(
            MyNavigationDirections.EditTransaction(
                transactionId = transactionId,
            ),
            navigationKit.command.first(),
        )
    }

    @Test
    fun navigateToEditTransactionForScreen() = runTestWithTimeout {
        val transactionForId = 123
        navigationKit.navigateToEditTransactionForScreen(
            transactionForId = transactionForId,
        )

        Assert.assertEquals(
            MyNavigationDirections.EditTransactionFor(
                transactionForId = transactionForId,
            ),
            navigationKit.command.first(),
        )
    }

    @Test
    fun navigateToHomeScreen() = runTestWithTimeout {
        navigationKit.navigateToHomeScreen()

        Assert.assertEquals(
            MyNavigationDirections.Home,
            navigationKit.command.first(),
        )
    }

    @Test
    fun navigateToOpenSourceLicensesScreen() = runTestWithTimeout {
        navigationKit.navigateToOpenSourceLicensesScreen()

        Assert.assertEquals(
            MyNavigationDirections.OpenSourceLicenses,
            navigationKit.command.first(),
        )
    }

    @Test
    fun navigateToSettingsScreen() = runTestWithTimeout {
        navigationKit.navigateToSettingsScreen()

        Assert.assertEquals(
            MyNavigationDirections.Settings,
            navigationKit.command.first(),
        )
    }

    @Test
    fun navigateToTransactionForValuesScreen() = runTestWithTimeout {
        navigationKit.navigateToTransactionForValuesScreen()

        Assert.assertEquals(
            MyNavigationDirections.TransactionForValues,
            navigationKit.command.first(),
        )
    }

    @Test
    fun navigateToTransactionsScreen() = runTestWithTimeout {
        navigationKit.navigateToTransactionsScreen()

        Assert.assertEquals(
            MyNavigationDirections.Transactions,
            navigationKit.command.first(),
        )
    }

    @Test
    fun navigateUp() = runTestWithTimeout {
        navigationKit.navigateUp()

        Assert.assertEquals(
            MyNavigationDirections.NavigateUp,
            navigationKit.command.first(),
        )
    }

    @Test
    fun navigateToViewTransactionScreen() = runTestWithTimeout {
        val transactionId = 123
        navigationKit.navigateToViewTransactionScreen(
            transactionId = transactionId,
        )

        Assert.assertEquals(
            MyNavigationDirections.ViewTransaction(
                transactionId = transactionId,
            ),
            navigationKit.command.first(),
        )
    }

    // region test setup
    private fun runTestWithTimeout(
        block: suspend TestScope.() -> Unit,
    ) = runTest(
        timeout = 3.seconds,
        testBody = {
            setUp()
            with(
                receiver = testScope,
            ) {
                block()
            }
            tearDown()
        },
    )

    private fun TestScope.setUp() {
        standardTestDispatcher = StandardTestDispatcher(
            scheduler = testScheduler,
        )
        testScope = TestScope(
            context = standardTestDispatcher,
        )
        setupSUT()
    }

    private fun setupSUT() {
        navigationKit = NavigationKitImpl(
            coroutineScope = testScope,
        )
    }

    private fun tearDown() {}
    // endregion
}

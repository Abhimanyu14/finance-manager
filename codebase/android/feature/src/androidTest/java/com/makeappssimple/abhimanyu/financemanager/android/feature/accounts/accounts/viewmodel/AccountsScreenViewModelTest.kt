package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.viewmodel

import app.cash.turbine.test
import com.makeappssimple.abhimanyu.financemanager.android.core.common.state.common.ScreenUICommonStateImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.common.state.loading.ScreenUIStateLoadingImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.common.state.refresh.ScreenUIStateRefreshImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.DeleteAccountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAllAccountsFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.accounts.AccountsListItemContentData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.accounts.AccountsListItemHeaderData
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.state.AccountsScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.state.AccountsScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.usecase.GetAccountsTotalBalanceAmountValueUseCase
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.usecase.GetAccountsTotalMinimumBalanceAmountValueUseCase
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.usecase.GetAllAccountsListItemDataListUseCase
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.usecase.GetDefaultAccountIdFlowUseCase
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.time.Duration.Companion.seconds

internal class AccountsScreenViewModelTest {
    // region testing
    private lateinit var standardTestDispatcher: TestDispatcher
    private lateinit var testScope: TestScope
    // endregion

    // region dependencies
    private val deleteAccountUseCase: DeleteAccountUseCase = mock()
    private val getAccountsTotalBalanceAmountValueUseCase: GetAccountsTotalBalanceAmountValueUseCase =
        mock()
    private val getAccountsTotalMinimumBalanceAmountValueUseCase: GetAccountsTotalMinimumBalanceAmountValueUseCase =
        mock()
    private val getAllAccountsFlowUseCase: GetAllAccountsFlowUseCase = mock()
    private val getAllAccountsListItemDataListUseCase: GetAllAccountsListItemDataListUseCase =
        mock()
    private val myPreferencesRepository: MyPreferencesRepository = mock()
    private val getDefaultAccountIdFlowUseCase: GetDefaultAccountIdFlowUseCase = mock()
    private val navigationKit: NavigationKit = mock()
    // endregion

    // region SUT
    private lateinit var accountsScreenViewModel: AccountsScreenViewModel
    // endregion

    // region initial state
    @Test
    fun `initial state`() = runTestWithTimeout {
        setupSUT(
            coroutineScope = testScope,
        )

        assertEquals(
            AccountsScreenUIState(),
            uiState(),
        )
    }
    // endregion

    // region initViewModel
    @Test
    fun `when initViewModel is completed, then isLoading is false`() = runTestWithTimeout {
        setupSUT(
            coroutineScope = testScope,
        )

        refreshSignal().test {
            initViewModel()
            advanceUntilIdle()

            assertEquals(
                false,
                uiState().isLoading,
            )
            assertEquals(
                Unit,
                awaitItem(),
            )
            expectNoEvents()
        }
    }

    @Test
    fun `when GetAllAccountsFlowUseCase emits new data, then ui state is updated accordingly`() =
        runTestWithTimeout {
            whenever(
                methodCall = getAllAccountsFlowUseCase(),
            ).thenReturn(flowOf(testAllAccounts))
            whenever(
                methodCall = getAccountsTotalBalanceAmountValueUseCase(
                    allAccounts = testAllAccounts,
                ),
            ).thenReturn(TEST_TOTAL_BALANCE_AMOUNT_VALUE)
            whenever(
                methodCall = getAccountsTotalMinimumBalanceAmountValueUseCase(
                    allAccounts = testAllAccounts,
                ),
            ).thenReturn(TEST_TOTAL_MINIMUM_BALANCE_AMOUNT_VALUE)
            whenever(
                methodCall = getAllAccountsListItemDataListUseCase(
                    allAccounts = testAllAccounts,
                    defaultAccountId = null,
                ),
            ).thenReturn(testAllAccountsListItemData)
            setupSUT(
                coroutineScope = testScope,
            )

            initViewModel()
            advanceUntilIdle()

            assertEquals(
                TEST_TOTAL_BALANCE_AMOUNT_VALUE,
                uiState().accountsTotalBalanceAmountValue,
            )
            assertEquals(
                TEST_TOTAL_MINIMUM_BALANCE_AMOUNT_VALUE,
                uiState().accountsTotalMinimumBalanceAmountValue,
            )
            assertEquals(
                testAllAccountsListItemData,
                uiState().accountsListItemDataList,
            )
        }

    @Test
    fun `when GetDefaultAccountIdFlowUseCase emits new data, then ui state is updated accordingly`() =
        runTestWithTimeout {
            val testDefaultAccountIdFlow = MutableSharedFlow<Int?>()
            whenever(
                methodCall = getDefaultAccountIdFlowUseCase(),
            ).thenReturn(flowOf(TEST_DEFAULT_ACCOUNT_ID))
            whenever(
                methodCall = getAllAccountsListItemDataListUseCase(
                    allAccounts = persistentListOf(),
                    defaultAccountId = TEST_DEFAULT_ACCOUNT_ID,
                ),
            ).thenReturn(testAllAccountsListItemData)
            setupSUT(
                coroutineScope = testScope,
            )

            initViewModel()
            testDefaultAccountIdFlow.emit(TEST_DEFAULT_ACCOUNT_ID)
            advanceUntilIdle()

            assertEquals(
                testAllAccountsListItemData,
                uiState().accountsListItemDataList,
            )
        }
    // endregion

    // region state events
    @Test
    fun `when deleteAccount is called and clickedItemId is null, then DeleteAccountUseCase is not called`() =
        runTestWithTimeout {
            setupSUT(
                coroutineScope = testScope,
            )

            refreshSignal().test {
                initViewModel()
                advanceUntilIdle()
                uiStateEvents().setClickedItemId(null)
                advanceUntilIdle()
                assertEquals(
                    Unit,
                    awaitItem(),
                )

                uiStateEvents().deleteAccount()
                advanceUntilIdle()

                verify(
                    mock = deleteAccountUseCase,
                    mode = never(),
                ).invoke(any())
                expectNoEvents()
            }
        }

    @Test
    fun `when deleteAccount is called and clickedItemId is not null, then DeleteAccountUseCase is called`() =
        runTestWithTimeout {
            setupSUT(
                coroutineScope = testScope,
            )

            initViewModel()
            advanceUntilIdle()
            uiStateEvents().setClickedItemId(TEST_CLICKED_ITEM_ID)
            advanceUntilIdle()

            uiStateEvents().deleteAccount()
            advanceUntilIdle()

            verify(
                mock = deleteAccountUseCase,
            ).invoke(TEST_CLICKED_ITEM_ID)
        }

    @Test
    fun `when navigateToAddAccountScreen is called, then navigator navigateToAddAccountScreen is called`() =
        runTestWithTimeout {
            setupSUT(
                coroutineScope = testScope,
            )

            uiStateEvents().navigateToAddAccountScreen()

            verify(
                mock = navigationKit,
            ).navigateToAddAccountScreen()
        }

    @Test
    fun `when navigateToEditAccountScreen is called, then navigator navigateToEditAccountScreen is called`() =
        runTestWithTimeout {
            setupSUT(
                coroutineScope = testScope,
            )

            uiStateEvents().navigateToEditAccountScreen(any())

            verify(
                mock = navigationKit,
            ).navigateToEditAccountScreen(any())
        }

    @Test
    fun `when navigateUp is called, then navigator navigateUp is called`() =
        runTestWithTimeout {
            setupSUT(
                coroutineScope = testScope,
            )

            uiStateEvents().navigateUp()

            verify(
                mock = navigationKit,
            ).navigateUp()
        }
    // endregion

    // region setupSUT
    private fun setupSUT(
        coroutineScope: CoroutineScope,
    ) {
        val screenUIStateRefresh = ScreenUIStateRefreshImpl(
            coroutineScope = coroutineScope,
        )
        val screenUIStateLoading = ScreenUIStateLoadingImpl(
            screenUIStateRefresh = screenUIStateRefresh,
        )
        val screenUICommonState = ScreenUICommonStateImpl(
            screenUIStateLoading = screenUIStateLoading,
        )

        accountsScreenViewModel = AccountsScreenViewModel(
            coroutineScope = coroutineScope,
            deleteAccountUseCase = deleteAccountUseCase,
            getAccountsTotalBalanceAmountValueUseCase = getAccountsTotalBalanceAmountValueUseCase,
            getAccountsTotalMinimumBalanceAmountValueUseCase = getAccountsTotalMinimumBalanceAmountValueUseCase,
            getAllAccountsFlowUseCase = getAllAccountsFlowUseCase,
            getAllAccountsListItemDataListUseCase = getAllAccountsListItemDataListUseCase,
            myPreferencesRepository = myPreferencesRepository,
            getDefaultAccountIdFlowUseCase = getDefaultAccountIdFlowUseCase,
            navigationKit = navigationKit,
            screenUICommonState = screenUICommonState,
        )
    }

    private fun refreshSignal(): MutableSharedFlow<Unit> {
        return accountsScreenViewModel.refreshSignal
    }

    private fun uiState(): AccountsScreenUIState {
        return accountsScreenViewModel.uiState.value
    }

    private fun initViewModel() {
        accountsScreenViewModel.initViewModel()
    }

    private fun uiStateEvents(): AccountsScreenUIStateEvents {
        return accountsScreenViewModel.uiStateEvents
    }
    // endregion

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

    private suspend fun TestScope.setUp() {
        standardTestDispatcher = StandardTestDispatcher(
            scheduler = testScheduler,
        )
        testScope = TestScope(
            context = standardTestDispatcher,
        )
    }

    private suspend fun TestScope.tearDown() {
    }
    // endregion

    companion object {
        const val EMPTY_STRING = ""
        const val TEST_CLICKED_ITEM_ID = 1
        const val TEST_DEFAULT_ACCOUNT_ID = 123
        const val TEST_TOTAL_BALANCE_AMOUNT_VALUE = 10_000L
        const val TEST_TOTAL_MINIMUM_BALANCE_AMOUNT_VALUE = 100L

        private val testBankAccount = Account(
            type = AccountType.BANK,
            name = "Test Bank",
            minimumAccountBalanceAmount = Amount(
                value = 1_000L,
            ),
            balanceAmount = Amount(
                value = 10_000L,
            ),
        )
        private val testEWalletAccount = Account(
            type = AccountType.E_WALLET,
            name = "Test Wallet",
            balanceAmount = Amount(
                value = 10_000L,
            ),
        )
        val testAllAccounts = persistentListOf(testBankAccount, testEWalletAccount)
        val testAllAccountsListItemData = persistentListOf(
            AccountsListItemHeaderData(
                name = "Bank",
            ),
            AccountsListItemContentData(
                name = "Test Bank",
            ),
        )
    }
}

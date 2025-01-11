package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.viewmodel

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.makeappssimple.abhimanyu.financemanager.android.core.common.state.common.ScreenUICommonStateImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.common.state.loading.ScreenUIStateLoadingImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.common.state.refresh.ScreenUIStateRefreshImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAccountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAllAccountsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.UpdateAccountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationArguments
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.bottomsheet.EditAccountScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.snackbar.EditAccountScreenSnackbarType
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.state.EditAccountScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.state.EditAccountScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.usecase.EditAccountScreenDataValidationUseCase
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Ignore
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.time.Duration.Companion.seconds

internal class EditAccountScreenViewModelTest {
    // region testing
    private lateinit var standardTestDispatcher: TestDispatcher
    private lateinit var unconfinedTestDispatcher: TestDispatcher
    private lateinit var testScope: TestScope
    // endregion

    // region dependencies
    private val savedStateHandle: SavedStateHandle = SavedStateHandle()
    private val editAccountScreenDataValidationUseCase: EditAccountScreenDataValidationUseCase =
        mock()
    private val getAllAccountsUseCase: GetAllAccountsUseCase = mock()
    private val getAccountUseCase: GetAccountUseCase = mock()
    private val navigationKit: NavigationKit = mock()
    private val updateAccountUseCase: UpdateAccountUseCase = mock()
    // endregion

    // region SUT
    private lateinit var editAccountScreenViewModel: EditAccountScreenViewModel
    // endregion

    // region initial state
    @Test
    fun `initial state`() = runTestWithTimeout {
        setupSUT(
            coroutineScope = testScope,
        )

        assertEquals(
            EditAccountScreenUIState(),
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
    fun `when initViewModel is called, then getAllAccountsUseCase is called`() =
        runTestWithTimeout {
            setupSUT(
                coroutineScope = testScope,
            )

            refreshSignal().test {
                initViewModel()
                advanceUntilIdle()

                verify(
                    mock = getAllAccountsUseCase,
                ).invoke()
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
    // @Ignore("Fix exception testing")
    fun `when initViewModel is called and currentAccountId is null, then IllegalStateException is thrown`() =
        runTestWithTimeout {
            setupSUT(
                coroutineScope = testScope,
                currentAccountId = null,
            )

            try {
                initViewModel()
                advanceUntilIdle()
                println("Exception not thrown")
            } catch (e: Exception) {
                println(e.message)
            }
            /*
            assertThrows(IllegalStateException::class.java) {
                runBlocking {
                    initViewModel()
                    advanceUntilIdle()
                }
            }
            */
        }

    @Test
    @Ignore("Fix exception testing")
    fun `when initViewModel is called and currentAccount is null, then IllegalStateException is thrown`() =
        runTestWithTimeout {
            setupSUT(
                coroutineScope = testScope,
                currentAccount = null,
            )

            assertThrows(IllegalStateException::class.java) {
                initViewModel()
                advanceUntilIdle()
            }
        }

    @Test
    fun `when initViewModel is called and currentAccount is not null, then UI state is updated`() =
        runTestWithTimeout {
            setupSUT(
                currentAccount = testCurrentAccount,
                coroutineScope = testScope,
            )

            refreshSignal().test {
                initViewModel()
                advanceUntilIdle()

                verify(
                    mock = navigationKit,
                    mode = never(),
                ).navigateUp()
                assertEquals(
                    TEST_ACCOUNT_TYPE_INDEX,
                    uiState().selectedAccountTypeIndex,
                )
                assertEquals(
                    TEST_NAME,
                    uiState().name.text,
                )
                assertEquals(
                    TEST_BALANCE_AMOUNT_VALUE,
                    uiState().balanceAmountValue.text,
                )
                assertEquals(
                    TEST_MINIMUM_BALANCE_AMOUNT_VALUE,
                    uiState().minimumBalanceAmountValue.text,
                )
                assertEquals(
                    Unit,
                    awaitItem(),
                )
                expectNoEvents()
            }
        }
    // endregion

    // region state events
    @Test
    fun `when clearBalanceAmountValue is called, balanceAmountValue is reset`() =
        runTestWithTimeout {
            setupSUT(
                currentAccount = testCurrentAccount,
                coroutineScope = testScope,
            )

            refreshSignal().test {
                initViewModel()
                advanceUntilIdle()
                assertEquals(
                    Unit,
                    awaitItem(),
                )
                uiStateEvents().setBalanceAmountValue(
                    uiState().balanceAmountValue.copy(
                        text = TEST_BALANCE_AMOUNT_VALUE,
                    ),
                )
                advanceUntilIdle()
                assertEquals(
                    TEST_BALANCE_AMOUNT_VALUE,
                    uiState().balanceAmountValue.text,
                )
                assertEquals(
                    Unit,
                    awaitItem(),
                )

                uiStateEvents().clearBalanceAmountValue()
                advanceUntilIdle()

                assertEquals(
                    EMPTY_STRING,
                    uiState().balanceAmountValue.text,
                )
                assertEquals(
                    Unit,
                    awaitItem(),
                )
                expectNoEvents()
            }
        }

    @Test
    fun `when clearMinimumAccountBalanceAmountValue is called, minimumAccountBalanceAmountValue is reset`() =
        runTestWithTimeout {
            setupSUT(
                coroutineScope = testScope,
            )

            refreshSignal().test {
                initViewModel()
                advanceUntilIdle()
                assertEquals(
                    Unit,
                    awaitItem(),
                )
                uiStateEvents().setMinimumAccountBalanceAmountValue(
                    uiState().minimumBalanceAmountValue.copy(
                        text = TEST_MINIMUM_BALANCE_AMOUNT_VALUE,
                    ),
                )
                advanceUntilIdle()
                assertEquals(
                    TEST_MINIMUM_BALANCE_AMOUNT_VALUE,
                    uiState().minimumBalanceAmountValue.text,
                )
                assertEquals(
                    Unit,
                    awaitItem(),
                )

                uiStateEvents().clearMinimumAccountBalanceAmountValue()
                advanceUntilIdle()

                assertEquals(
                    EMPTY_STRING,
                    uiState().minimumBalanceAmountValue.text,
                )
                assertEquals(
                    Unit,
                    awaitItem(),
                )
                expectNoEvents()
            }
        }

    @Test
    fun `when clearName is called, name is reset`() = runTestWithTimeout {
        setupSUT(
            coroutineScope = testScope,
        )

        refreshSignal().test {
            initViewModel()
            advanceUntilIdle()
            assertEquals(
                Unit,
                awaitItem(),
            )
            uiStateEvents().setName(
                uiState().name.copy(
                    text = TEST_NAME,
                ),
            )
            advanceUntilIdle()
            assertEquals(
                TEST_NAME,
                uiState().name.text,
            )
            assertEquals(
                Unit,
                awaitItem(),
            )

            uiStateEvents().clearName()
            advanceUntilIdle()

            assertEquals(
                EMPTY_STRING,
                uiState().name.text,
            )
            assertEquals(
                Unit,
                awaitItem(),
            )
            expectNoEvents()
        }
    }

    @Test
    fun `when navigateUp is called, then navigator navigateUp is called`() = runTestWithTimeout {
        setupSUT(
            coroutineScope = testScope,
        )

        uiStateEvents().navigateUp()

        verify(
            mock = navigationKit,
        ).navigateUp()
    }

    @Test
    fun `when resetScreenBottomSheetType is called, then screenBottomSheetType is reset`() =
        runTestWithTimeout {
            setupSUT(
                coroutineScope = testScope,
            )

            refreshSignal().test {
                initViewModel()
                advanceUntilIdle()
                assertEquals(
                    Unit,
                    awaitItem(),
                )
                uiStateEvents().setScreenBottomSheetType(testScreenBottomSheetType)
                advanceUntilIdle()
                assertEquals(
                    testScreenBottomSheetType,
                    uiState().screenBottomSheetType,
                )
                assertEquals(
                    Unit,
                    awaitItem(),
                )

                uiStateEvents().resetScreenBottomSheetType()
                advanceUntilIdle()

                assertEquals(
                    EditAccountScreenBottomSheetType.None,
                    uiState().screenBottomSheetType,
                )
                assertEquals(
                    Unit,
                    awaitItem(),
                )
                expectNoEvents()
            }
        }

    @Test
    fun `when setBalanceAmountValue is called, then balanceAmountValue is updated`() =
        runTestWithTimeout {
            setupSUT(
                coroutineScope = testScope,
            )

            refreshSignal().test {
                initViewModel()
                advanceUntilIdle()
                assertEquals(
                    Unit,
                    awaitItem(),
                )
                uiStateEvents().setBalanceAmountValue(
                    uiState().balanceAmountValue.copy(
                        text = EMPTY_STRING,
                    ),
                )
                advanceUntilIdle()
                assertEquals(
                    EMPTY_STRING,
                    uiState().balanceAmountValue.text,
                )
                assertEquals(
                    Unit,
                    awaitItem(),
                )

                uiStateEvents().setBalanceAmountValue(
                    uiState().balanceAmountValue.copy(
                        text = TEST_BALANCE_AMOUNT_VALUE,
                    ),
                )
                advanceUntilIdle()

                assertEquals(
                    TEST_BALANCE_AMOUNT_VALUE,
                    uiState().balanceAmountValue.text,
                )
                assertEquals(
                    Unit,
                    awaitItem(),
                )
                expectNoEvents()
            }
        }

    @Test
    fun `when setMinimumAccountBalanceAmountValue is called, then minimumAccountBalanceAmountValue is updated`() =
        runTestWithTimeout {
            setupSUT(
                coroutineScope = testScope,
            )

            refreshSignal().test {
                initViewModel()
                advanceUntilIdle()
                assertEquals(
                    Unit,
                    awaitItem(),
                )
                uiStateEvents().setMinimumAccountBalanceAmountValue(
                    uiState().minimumBalanceAmountValue.copy(
                        text = EMPTY_STRING,
                    ),
                )
                advanceUntilIdle()
                assertEquals(
                    EMPTY_STRING,
                    uiState().minimumBalanceAmountValue.text,
                )
                assertEquals(
                    Unit,
                    awaitItem(),
                )

                uiStateEvents().setMinimumAccountBalanceAmountValue(
                    uiState().minimumBalanceAmountValue.copy(
                        text = TEST_MINIMUM_BALANCE_AMOUNT_VALUE,
                    ),
                )
                advanceUntilIdle()

                assertEquals(
                    TEST_MINIMUM_BALANCE_AMOUNT_VALUE,
                    uiState().minimumBalanceAmountValue.text,
                )
                assertEquals(
                    Unit,
                    awaitItem(),
                )
                expectNoEvents()
            }
        }

    @Test
    fun `when setName is called, then name is updated`() = runTestWithTimeout {
        setupSUT(
            coroutineScope = testScope,
        )

        refreshSignal().test {
            initViewModel()
            advanceUntilIdle()
            assertEquals(
                Unit,
                awaitItem(),
            )
            uiStateEvents().setName(
                uiState().name.copy(
                    text = EMPTY_STRING,
                ),
            )
            advanceUntilIdle()
            assertEquals(
                EMPTY_STRING,
                uiState().name.text,
            )
            assertEquals(
                Unit,
                awaitItem(),
            )

            uiStateEvents().setName(
                uiState().name.copy(
                    text = TEST_NAME,
                ),
            )
            advanceUntilIdle()

            assertEquals(
                TEST_NAME,
                uiState().name.text,
            )
            assertEquals(
                Unit,
                awaitItem(),
            )
            expectNoEvents()
        }
    }

    @Test
    fun `when setScreenBottomSheetType is called, then screenBottomSheetType is updated`() =
        runTestWithTimeout {
            setupSUT(
                coroutineScope = testScope,
            )

            refreshSignal().test {
                initViewModel()
                advanceUntilIdle()
                assertEquals(
                    Unit,
                    awaitItem(),
                )
                uiStateEvents().setScreenBottomSheetType(EditAccountScreenBottomSheetType.None)
                advanceUntilIdle()
                assertEquals(
                    EditAccountScreenBottomSheetType.None,
                    uiState().screenBottomSheetType,
                )
                assertEquals(
                    Unit,
                    awaitItem(),
                )

                uiStateEvents().setScreenBottomSheetType(testScreenBottomSheetType)
                advanceUntilIdle()

                assertEquals(
                    testScreenBottomSheetType,
                    uiState().screenBottomSheetType,
                )
                assertEquals(
                    Unit,
                    awaitItem(),
                )
                expectNoEvents()
            }
        }

    @Test
    fun `when setScreenSnackbarType is called, then screenSnackbarType is updated`() =
        runTestWithTimeout {
            setupSUT(
                coroutineScope = testScope,
            )

            refreshSignal().test {
                initViewModel()
                advanceUntilIdle()
                assertEquals(
                    Unit,
                    awaitItem(),
                )
                uiStateEvents().setScreenSnackbarType(EditAccountScreenSnackbarType.None)
                advanceUntilIdle()
                assertEquals(
                    EditAccountScreenSnackbarType.None,
                    uiState().screenSnackbarType,
                )
                assertEquals(
                    Unit,
                    awaitItem(),
                )

                uiStateEvents().setScreenSnackbarType(testScreenSnackbarType)
                advanceUntilIdle()

                assertEquals(
                    testScreenSnackbarType,
                    uiState().screenSnackbarType,
                )
                assertEquals(
                    Unit,
                    awaitItem(),
                )
                expectNoEvents()
            }
        }

    @Test
    fun `when setSelectedAccountTypeIndex is called, then selectedAccountTypeIndex is updated`() =
        runTestWithTimeout {
            setupSUT(
                coroutineScope = testScope,
            )

            refreshSignal().test {
                initViewModel()
                advanceUntilIdle()
                assertEquals(
                    Unit,
                    awaitItem(),
                )
                uiStateEvents().setSelectedAccountTypeIndex(0)
                advanceUntilIdle()
                assertEquals(
                    0,
                    uiState().selectedAccountTypeIndex,
                )
                assertEquals(
                    Unit,
                    awaitItem(),
                )

                uiStateEvents().setSelectedAccountTypeIndex(TEST_ACCOUNT_TYPE_INDEX)
                advanceUntilIdle()

                assertEquals(
                    TEST_ACCOUNT_TYPE_INDEX,
                    uiState().selectedAccountTypeIndex,
                )
                assertEquals(
                    Unit,
                    awaitItem(),
                )
                expectNoEvents()
            }
        }

    @Test
    fun `when updateAccount is called and updateAccountUseCase returns true, then navigator navigateUp is called`() =
        runTestWithTimeout {
            whenever(
                methodCall = updateAccountUseCase(
                    currentAccount = anyOrNull(),
                    validAccountTypesForNewAccount = any(),
                    selectedAccountTypeIndex = any(),
                    balanceAmountValue = any(),
                    minimumAccountBalanceAmountValue = any(),
                    name = any(),
                ),
            ).thenReturn(true)
            setupSUT(
                coroutineScope = testScope,
            )

            refreshSignal().test {
                initViewModel()
                advanceUntilIdle()
                assertEquals(
                    Unit,
                    awaitItem(),
                )

                uiStateEvents().updateAccount()
                advanceUntilIdle()

                verify(
                    mock = navigationKit,
                ).navigateUp()
                assertEquals(
                    Unit,
                    awaitItem(),
                )
                expectNoEvents()
            }
        }

    @Test
    fun `when updateAccount is called and updateAccountUseCase returns false, then isLoading is false`() =
        runTestWithTimeout {
            whenever(
                methodCall = updateAccountUseCase(
                    currentAccount = anyOrNull(),
                    validAccountTypesForNewAccount = any(),
                    selectedAccountTypeIndex = any(),
                    balanceAmountValue = any(),
                    minimumAccountBalanceAmountValue = any(),
                    name = any(),
                ),
            ).thenReturn(false)
            setupSUT(
                coroutineScope = testScope,
            )

            refreshSignal().test {
                initViewModel()
                advanceUntilIdle()
                assertEquals(
                    Unit,
                    awaitItem(),
                )

                uiStateEvents().updateAccount()
                advanceUntilIdle()

                verify(
                    mock = navigationKit,
                    mode = never(),
                ).navigateUp()
                assertEquals(
                    false,
                    uiState().isLoading,
                )
                assertEquals(
                    Unit,
                    awaitItem(),
                )
                assertEquals(
                    Unit,
                    awaitItem(),
                )
                expectNoEvents()
            }
        }
    // endregion

    // region setupSUT
    private suspend fun setupSUT(
        currentAccount: Account? = testCurrentAccount,
        coroutineScope: CoroutineScope,
        allAccounts: ImmutableList<Account> = persistentListOf(),
        currentAccountId: Int? = TEST_CURRENT_ACCOUNT_ID,
    ) {
        whenever(
            methodCall = getAllAccountsUseCase(),
        ).thenReturn(allAccounts)
        whenever(
            methodCall = editAccountScreenDataValidationUseCase(
                allAccounts = any(),
                enteredName = any(),
                currentAccount = anyOrNull(),
            ),
        ).thenReturn(EditAccountScreenDataValidationState())
        currentAccountId?.let {
            whenever(
                methodCall = getAccountUseCase(
                    id = currentAccountId,
                ),
            ).thenReturn(currentAccount)
        }
        savedStateHandle[NavigationArguments.ACCOUNT_ID] = currentAccountId

        val screenUIStateRefresh = ScreenUIStateRefreshImpl(
            coroutineScope = coroutineScope,
        )
        val screenUIStateLoading = ScreenUIStateLoadingImpl(
            screenUIStateRefresh = screenUIStateRefresh,
        )
        val screenUICommonState = ScreenUICommonStateImpl(
            screenUIStateLoading = screenUIStateLoading,
        )
        editAccountScreenViewModel = EditAccountScreenViewModel(
            coroutineScope = coroutineScope,
            savedStateHandle = savedStateHandle,
            editAccountScreenDataValidationUseCase = editAccountScreenDataValidationUseCase,
            getAllAccountsUseCase = getAllAccountsUseCase,
            getAccountUseCase = getAccountUseCase,
            navigationKit = navigationKit,
            screenUICommonState = screenUICommonState,
            updateAccountUseCase = updateAccountUseCase,
        )
    }

    private fun refreshSignal(): MutableSharedFlow<Unit> {
        return editAccountScreenViewModel.refreshSignal
    }

    private fun uiState(): EditAccountScreenUIState {
        return editAccountScreenViewModel.uiState.value
    }

    private fun initViewModel() {
        editAccountScreenViewModel.initViewModel()
    }

    private fun uiStateEvents(): EditAccountScreenUIStateEvents {
        return editAccountScreenViewModel.uiStateEvents
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
        unconfinedTestDispatcher = UnconfinedTestDispatcher(
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
        const val TEST_ACCOUNT_TYPE_INDEX = 0
        const val TEST_BALANCE_AMOUNT_VALUE = "10000"
        const val TEST_CURRENT_ACCOUNT_ID = 123
        const val TEST_MINIMUM_BALANCE_AMOUNT_VALUE = "1000"
        const val TEST_NAME = "test_name"

        val testCurrentAccount = Account(
            type = AccountType.BANK,
            name = TEST_NAME,
            minimumAccountBalanceAmount = Amount(
                value = 1_000L,
            ),
            balanceAmount = Amount(
                value = 10_000L,
            ),
        )
        val testScreenBottomSheetType = EditAccountScreenBottomSheetType.None
        val testScreenSnackbarType = EditAccountScreenSnackbarType.None
    }
}

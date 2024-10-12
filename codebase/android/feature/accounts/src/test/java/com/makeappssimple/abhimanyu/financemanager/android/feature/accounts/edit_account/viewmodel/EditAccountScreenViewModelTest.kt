package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import app.cash.turbine.turbineScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.state.common.ScreenUICommonStateImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.common.state.loading.ScreenUIStateLoadingImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.common.state.refresh.ScreenUIStateRefreshImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAccountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAllAccountsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.UpdateAccountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavArgs
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.bottomsheet.EditAccountScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.snackbar.EditAccountScreenSnackbarType
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.state.EditAccountScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.usecase.EditAccountScreenDataValidationUseCase
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
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
    private val navigator: Navigator = mock()
    private val updateAccountUseCase: UpdateAccountUseCase = mock()
    // endregion

    // region SUT
    private lateinit var editAccountScreenViewModel: EditAccountScreenViewModel
    // endregion

    @Test
    fun `initial state`() = runTestWithTimeout {
        initViewModel(
            coroutineScope = testScope,
        )

        assertEquals(
            EditAccountScreenUIState(),
            editAccountScreenViewModel.uiState.value,
        )
        assertEquals(
            null,
            editAccountScreenViewModel.currentAccount,
        )
        assertEquals(
            persistentListOf(AccountType.BANK, AccountType.E_WALLET),
            editAccountScreenViewModel.validAccountTypesForNewAccount,
        )
        assertEquals(
            true,
            editAccountScreenViewModel.isLoading,
        )
        assertEquals(
            TextFieldValue(),
            editAccountScreenViewModel.minimumAccountBalanceAmountValue,
        )
        assertEquals(
            TextFieldValue(),
            editAccountScreenViewModel.name,
        )
        assertEquals(
            TextFieldValue(),
            editAccountScreenViewModel.balanceAmountValue,
        )
        assertEquals(
            EditAccountScreenBottomSheetType.None,
            editAccountScreenViewModel.screenBottomSheetType,
        )
        assertEquals(
            EditAccountScreenSnackbarType.None,
            editAccountScreenViewModel.screenSnackbarType,
        )
        assertEquals(
            0,
            editAccountScreenViewModel.selectedAccountTypeIndex,
        )
    }

    @Test
    fun `when initViewModel is completed, then isLoading is false`() = runTestWithTimeout {
        whenever(
            methodCall = getAllAccountsUseCase(),
        ).thenReturn(persistentListOf())
        whenever(
            methodCall = editAccountScreenDataValidationUseCase(
                allAccounts = any(),
                enteredName = any(),
                currentAccount = anyOrNull(),
            ),
        ).thenReturn(EditAccountScreenDataValidationState())
        savedStateHandle[NavArgs.ACCOUNT_ID] = null
        initViewModel(
            coroutineScope = testScope,
        )

        editAccountScreenViewModel.initViewModel()
        advanceUntilIdle()

        assertEquals(
            false,
            editAccountScreenViewModel.uiState.value.isLoading,
        )
    }

    @Test
    fun `when initViewModel is called, then getAllAccountsUseCase is called`() =
        runTestWithTimeout {
            whenever(
                methodCall = getAllAccountsUseCase(),
            ).thenReturn(persistentListOf())
            whenever(
                methodCall = editAccountScreenDataValidationUseCase(
                    allAccounts = any(),
                    enteredName = any(),
                    currentAccount = anyOrNull(),
                ),
            ).thenReturn(EditAccountScreenDataValidationState())
            savedStateHandle[NavArgs.ACCOUNT_ID] = null
            initViewModel(
                coroutineScope = testScope,
            )

            editAccountScreenViewModel.initViewModel()
            advanceUntilIdle()

            verify(
                mock = getAllAccountsUseCase,
            ).invoke()
            assertEquals(
                false,
                editAccountScreenViewModel.uiState.value.isLoading,
            )
        }

    @Test
    fun `when initViewModel is called and currentAccountId is null, then navigateUp is called`() =
        runTestWithTimeout {
            whenever(
                methodCall = getAllAccountsUseCase(),
            ).thenReturn(persistentListOf())
            whenever(
                methodCall = editAccountScreenDataValidationUseCase(
                    allAccounts = any(),
                    enteredName = any(),
                    currentAccount = anyOrNull(),
                ),
            ).thenReturn(EditAccountScreenDataValidationState())
            savedStateHandle[NavArgs.ACCOUNT_ID] = null
            initViewModel(
                coroutineScope = testScope,
            )

            editAccountScreenViewModel.initViewModel()
            advanceUntilIdle()

            verify(
                mock = navigator,
            ).navigateUp()
        }

    @Test
    fun `when initViewModel is called and currentAccount is null, then navigateUp is called`() =
        runTestWithTimeout {
            whenever(
                methodCall = getAllAccountsUseCase(),
            ).thenReturn(persistentListOf())
            whenever(
                methodCall = editAccountScreenDataValidationUseCase(
                    allAccounts = any(),
                    enteredName = any(),
                    currentAccount = anyOrNull(),
                ),
            ).thenReturn(EditAccountScreenDataValidationState())
            whenever(
                methodCall = getAccountUseCase(
                    id = TEST_CURRENT_ACCOUNT_ID,
                ),
            ).thenReturn(null)
            savedStateHandle[NavArgs.ACCOUNT_ID] = TEST_CURRENT_ACCOUNT_ID
            initViewModel(
                coroutineScope = testScope,
            )

            editAccountScreenViewModel.initViewModel()
            advanceUntilIdle()

            verify(
                mock = getAllAccountsUseCase,
            ).invoke()
            verify(
                mock = navigator,
            ).navigateUp()
        }

    @Test
    fun `when initViewModel is called and currentAccount is not null, then ui state is updated`() =
        runTestWithTimeout {
            whenever(
                methodCall = getAllAccountsUseCase(),
            ).thenReturn(persistentListOf())
            whenever(
                methodCall = editAccountScreenDataValidationUseCase(
                    allAccounts = any(),
                    enteredName = any(),
                    currentAccount = anyOrNull(),
                ),
            ).thenReturn(EditAccountScreenDataValidationState())
            whenever(
                methodCall = getAccountUseCase(
                    id = TEST_CURRENT_ACCOUNT_ID,
                ),
            ).thenReturn(testCurrentAccount)
            savedStateHandle[NavArgs.ACCOUNT_ID] = TEST_CURRENT_ACCOUNT_ID
            initViewModel(
                coroutineScope = testScope,
            )

            editAccountScreenViewModel.refreshSignal.test {
                editAccountScreenViewModel.initViewModel()
                advanceUntilIdle()

                verify(
                    mock = navigator,
                    mode = never(),
                ).navigateUp()
                assertEquals(
                    TEST_ACCOUNT_TYPE_INDEX,
                    editAccountScreenViewModel.uiState.value.selectedAccountTypeIndex,
                )
                assertEquals(
                    TEST_NAME,
                    editAccountScreenViewModel.uiState.value.name.text,
                )
                assertEquals(
                    TEST_BALANCE_AMOUNT_VALUE,
                    editAccountScreenViewModel.uiState.value.balanceAmountValue.text,
                )
                assertEquals(
                    TEST_MINIMUM_BALANCE_AMOUNT_VALUE,
                    editAccountScreenViewModel.uiState.value.minimumBalanceAmountValue.text,
                )
                assertEquals(
                    Unit,
                    awaitItem(),
                )
                expectNoEvents()
            }
        }

    @Test
    fun `when clearBalanceAmountValue is called, balanceAmountValue is reset`() =
        runTestWithTimeout {
            whenever(
                methodCall = getAllAccountsUseCase(),
            ).thenReturn(persistentListOf())
            whenever(
                methodCall = editAccountScreenDataValidationUseCase(
                    allAccounts = any(),
                    enteredName = any(),
                    currentAccount = anyOrNull(),
                ),
            ).thenReturn(EditAccountScreenDataValidationState())
            whenever(
                methodCall = getAccountUseCase(
                    id = TEST_CURRENT_ACCOUNT_ID,
                ),
            ).thenReturn(testCurrentAccount)
            savedStateHandle[NavArgs.ACCOUNT_ID] = TEST_CURRENT_ACCOUNT_ID
            initViewModel(
                coroutineScope = testScope,
            )

            turbineScope {
                val refreshSignalTurbine = editAccountScreenViewModel.refreshSignal.testIn(
                    scope = this,
                )
                editAccountScreenViewModel.updateBalanceAmountValue(
                    updatedBalanceAmountValue = editAccountScreenViewModel.balanceAmountValue.copy(
                        text = TEST_BALANCE_AMOUNT_VALUE,
                    ),
                    shouldRefresh = false,
                )
                assertEquals(
                    TEST_BALANCE_AMOUNT_VALUE,
                    editAccountScreenViewModel.uiState.value.balanceAmountValue.text,
                )

                editAccountScreenViewModel.clearBalanceAmountValue()
                advanceUntilIdle()

                assertEquals(
                    Unit,
                    refreshSignalTurbine.awaitItem(),
                )
                assertEquals(
                    EMPTY_STRING,
                    editAccountScreenViewModel.uiState.value.balanceAmountValue.text,
                )
                refreshSignalTurbine.awaitComplete()
                refreshSignalTurbine.expectNoEvents()
            }
        }

    @Test
    fun `when clearMinimumAccountBalanceAmountValue is called, minimumAccountBalanceAmountValue is reset`() =
        runTestWithTimeout {
            initViewModel(
                coroutineScope = testScope,
            )

            turbineScope {
                val refreshSignalTurbine = editAccountScreenViewModel.refreshSignal.testIn(
                    scope = this,
                )
                editAccountScreenViewModel.updateMinimumAccountBalanceAmountValue(
                    updatedMinimumAccountBalanceAmountValue = editAccountScreenViewModel.minimumAccountBalanceAmountValue.copy(
                        text = TEST_MINIMUM_BALANCE_AMOUNT_VALUE,
                    ),
                    shouldRefresh = false,
                )
                assertEquals(
                    TEST_MINIMUM_BALANCE_AMOUNT_VALUE,
                    editAccountScreenViewModel.uiState.value.minimumBalanceAmountValue.text,
                )

                editAccountScreenViewModel.clearMinimumAccountBalanceAmountValue()
                advanceUntilIdle()

                assertEquals(
                    Unit,
                    refreshSignalTurbine.awaitItem(),
                )
                assertEquals(
                    EMPTY_STRING,
                    editAccountScreenViewModel.uiState.value.minimumBalanceAmountValue.text,
                )
                refreshSignalTurbine.awaitComplete()
                refreshSignalTurbine.expectNoEvents()
            }
        }

    @Test
    fun `when clearName is called, name is reset`() = runTestWithTimeout {
        whenever(
            methodCall = getAllAccountsUseCase(),
        ).thenReturn(persistentListOf())
        whenever(
            methodCall = editAccountScreenDataValidationUseCase(
                allAccounts = any(),
                enteredName = any(),
                currentAccount = anyOrNull(),
            ),
        ).thenReturn(EditAccountScreenDataValidationState())
        savedStateHandle[NavArgs.ACCOUNT_ID] = null
        initViewModel(
            coroutineScope = testScope,
        )

        turbineScope {
            editAccountScreenViewModel.initViewModel()
            val refreshSignalTurbine = editAccountScreenViewModel.refreshSignal.testIn(
                scope = this,
            )
            assertEquals(
                Unit,
                refreshSignalTurbine.awaitItem(),
            )
            editAccountScreenViewModel.updateName(
                updatedName = editAccountScreenViewModel.name.copy(
                    text = TEST_NAME,
                ),
            )
            assertEquals(
                Unit,
                refreshSignalTurbine.awaitItem(),
            )
            assertEquals(
                TEST_NAME,
                editAccountScreenViewModel.uiState.value.name.text,
            )

            editAccountScreenViewModel.clearName()
            advanceUntilIdle()

            assertEquals(
                Unit,
                refreshSignalTurbine.awaitItem(),
            )
            assertEquals(
                EMPTY_STRING,
                editAccountScreenViewModel.uiState.value.name.text,
            )
            refreshSignalTurbine.awaitComplete()
            refreshSignalTurbine.expectNoEvents()
        }
    }

    @Test
    fun `completeLoading with refresh = true`() = runTestWithTimeout {
        initViewModel(
            coroutineScope = testScope,
        )

        editAccountScreenViewModel.refreshSignal.test {
            editAccountScreenViewModel.startLoading(
                shouldRefresh = false,
            )

            editAccountScreenViewModel.completeLoading(
                shouldRefresh = true,
            )
            advanceUntilIdle()

            assertEquals(
                Unit,
                awaitItem(),
            )
            assertEquals(
                false,
                editAccountScreenViewModel.isLoading,
            )
            awaitComplete()
            expectNoEvents()
        }
    }

    @Test
    fun `completeLoading with refresh = false`() = runTestWithTimeout {
        initViewModel(
            coroutineScope = testScope,
        )

        editAccountScreenViewModel.startLoading(
            shouldRefresh = false,
        )

        editAccountScreenViewModel.refreshSignal.test {
            editAccountScreenViewModel.completeLoading(
                shouldRefresh = false,
            )

            expectNoEvents()
        }

        assertEquals(
            false,
            editAccountScreenViewModel.isLoading,
        )
    }

    @Test
    fun navigateUp() = runTestWithTimeout {
        initViewModel(
            coroutineScope = testScope,
        )

        editAccountScreenViewModel.navigateUp()

        verify(
            mock = navigator,
        ).navigateUp()
    }

    @Test
    fun resetScreenBottomSheetType() = runTestWithTimeout {
        initViewModel(
            coroutineScope = testScope,
        )

        editAccountScreenViewModel.refreshSignal.test {
            editAccountScreenViewModel.resetScreenBottomSheetType()

            assertEquals(
                Unit,
                awaitItem(),
            )
        }

        assertEquals(
            EditAccountScreenBottomSheetType.None,
            editAccountScreenViewModel.screenBottomSheetType,
        )
    }

    @Test
    fun `startLoading with refresh = true`() = runTestWithTimeout {
        initViewModel(
            coroutineScope = testScope,
        )

        editAccountScreenViewModel.refreshSignal.test {
            editAccountScreenViewModel.startLoading(
                shouldRefresh = true,
            )

            assertEquals(
                Unit,
                awaitItem(),
            )
        }

        assertEquals(
            true,
            editAccountScreenViewModel.isLoading,
        )
    }

    @Test
    fun `startLoading with refresh = false`() = runTestWithTimeout {
        initViewModel(
            coroutineScope = testScope,
        )

        editAccountScreenViewModel.refreshSignal.test {
            editAccountScreenViewModel.startLoading(
                shouldRefresh = false,
            )

            expectNoEvents()
        }

        assertEquals(
            true,
            editAccountScreenViewModel.isLoading,
        )
    }

    @Test
    fun `updateAccount when updateAccountUseCase returns true`() = runTestWithTimeout {
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
        initViewModel(
            coroutineScope = testScope,
        )

        editAccountScreenViewModel.refreshSignal.test {
            editAccountScreenViewModel.updateAccount()

            assertEquals(
                Unit,
                awaitItem(),
            )
            assertEquals(
                true,
                editAccountScreenViewModel.isLoading,
            )

            advanceUntilIdle()

            verify(
                mock = navigator,
            ).navigateUp()
            expectNoEvents()
        }
    }

    @Test
    fun `updateAccount when updateAccountUseCase returns false`() = runTestWithTimeout {
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
        initViewModel(
            coroutineScope = testScope,
        )

        editAccountScreenViewModel.refreshSignal.test {
            editAccountScreenViewModel.updateAccount()

            assertEquals(
                Unit,
                awaitItem(),
            )
            // TODO(Abhi): The isLoading should be true here, but it is reset before we assert that.
            assertEquals(
                false,
                editAccountScreenViewModel.isLoading,
            )

            assertEquals(
                Unit,
                awaitItem(),
            )
            assertEquals(
                false,
                editAccountScreenViewModel.isLoading,
            )

            verify(
                mock = navigator,
                mode = never(),
            ).navigateUp()
            expectNoEvents()
        }
    }

    @Test
    fun `updateBalanceAmountValue with refresh = true`() = runTestWithTimeout {
        initViewModel(
            coroutineScope = testScope,
        )

        editAccountScreenViewModel.refreshSignal.test {
            editAccountScreenViewModel.updateBalanceAmountValue(
                updatedBalanceAmountValue = editAccountScreenViewModel.balanceAmountValue.copy(
                    text = TEST_BALANCE_AMOUNT_VALUE,
                ),
            )

            assertEquals(
                Unit,
                awaitItem(),
            )
        }

        assertEquals(
            TEST_BALANCE_AMOUNT_VALUE,
            editAccountScreenViewModel.balanceAmountValue.text,
        )
    }

    @Test
    fun `updateBalanceAmountValue with refresh = false`() = runTestWithTimeout {
        initViewModel(
            coroutineScope = testScope,
        )

        editAccountScreenViewModel.refreshSignal.test {
            editAccountScreenViewModel.updateBalanceAmountValue(
                updatedBalanceAmountValue = editAccountScreenViewModel.balanceAmountValue.copy(
                    text = TEST_BALANCE_AMOUNT_VALUE,
                ),
                shouldRefresh = false,
            )

            expectNoEvents()
        }

        assertEquals(
            TEST_BALANCE_AMOUNT_VALUE,
            editAccountScreenViewModel.balanceAmountValue.text,
        )
    }

    @Test
    fun `updateMinimumAccountBalanceAmountValue with refresh = true`() = runTestWithTimeout {
        initViewModel(
            coroutineScope = testScope,
        )

        editAccountScreenViewModel.refreshSignal.test {
            editAccountScreenViewModel.updateMinimumAccountBalanceAmountValue(
                updatedMinimumAccountBalanceAmountValue = editAccountScreenViewModel.minimumAccountBalanceAmountValue.copy(
                    text = TEST_MINIMUM_BALANCE_AMOUNT_VALUE,
                ),
                shouldRefresh = true,
            )

            assertEquals(
                Unit,
                awaitItem(),
            )
        }

        assertEquals(
            TEST_MINIMUM_BALANCE_AMOUNT_VALUE,
            editAccountScreenViewModel.minimumAccountBalanceAmountValue.text,
        )
    }

    @Test
    fun `updateMinimumAccountBalanceAmountValue with refresh = false`() = runTestWithTimeout {
        initViewModel(
            coroutineScope = testScope,
        )

        editAccountScreenViewModel.refreshSignal.test {
            editAccountScreenViewModel.updateMinimumAccountBalanceAmountValue(
                updatedMinimumAccountBalanceAmountValue = editAccountScreenViewModel.minimumAccountBalanceAmountValue.copy(
                    text = TEST_MINIMUM_BALANCE_AMOUNT_VALUE,
                ),
                shouldRefresh = false,
            )

            expectNoEvents()
        }

        assertEquals(
            TEST_MINIMUM_BALANCE_AMOUNT_VALUE,
            editAccountScreenViewModel.minimumAccountBalanceAmountValue.text,
        )
    }

    @Test
    fun `updateName with refresh = true`() = runTestWithTimeout {
        initViewModel(
            coroutineScope = testScope,
        )

        editAccountScreenViewModel.refreshSignal.test {
            editAccountScreenViewModel.updateName(
                updatedName = editAccountScreenViewModel.name.copy(
                    text = TEST_NAME,
                ),
                shouldRefresh = true,
            )

            assertEquals(
                Unit,
                awaitItem(),
            )
            expectNoEvents()
        }

        assertEquals(
            TEST_NAME,
            editAccountScreenViewModel.name.text,
        )
    }

    @Test
    fun `updateName with refresh = false`() = runTestWithTimeout {
        initViewModel(
            coroutineScope = testScope,
        )

        editAccountScreenViewModel.refreshSignal.test {
            editAccountScreenViewModel.updateName(
                updatedName = editAccountScreenViewModel.name.copy(
                    text = TEST_NAME,
                ),
                shouldRefresh = false,
            )

            expectNoEvents()
        }

        assertEquals(
            TEST_NAME,
            editAccountScreenViewModel.name.text,
        )
    }

    @Test
    fun `updateScreenBottomSheetType with refresh = true`() = runTestWithTimeout {
        initViewModel(
            coroutineScope = testScope,
        )

        editAccountScreenViewModel.refreshSignal.test {
            editAccountScreenViewModel.updateScreenBottomSheetType(
                updatedEditAccountScreenBottomSheetType = testScreenBottomSheetType,
                shouldRefresh = true,
            )

            assertEquals(
                Unit,
                awaitItem(),
            )
        }

        assertEquals(
            testScreenBottomSheetType,
            editAccountScreenViewModel.screenBottomSheetType,
        )
    }

    @Test
    fun `updateScreenBottomSheetType with refresh = false`() = runTestWithTimeout {
        initViewModel(
            coroutineScope = testScope,
        )

        editAccountScreenViewModel.refreshSignal.test {
            editAccountScreenViewModel.updateScreenBottomSheetType(
                updatedEditAccountScreenBottomSheetType = testScreenBottomSheetType,
                shouldRefresh = false,
            )

            expectNoEvents()
        }

        assertEquals(
            testScreenBottomSheetType,
            editAccountScreenViewModel.screenBottomSheetType,
        )
    }

    @Test
    fun `updateScreenSnackbarType with refresh = true`() = runTestWithTimeout {
        initViewModel(
            coroutineScope = testScope,
        )

        editAccountScreenViewModel.refreshSignal.test {
            editAccountScreenViewModel.updateScreenSnackbarType(
                updatedEditAccountScreenSnackbarType = testScreenSnackbarType,
                shouldRefresh = true,
            )

            assertEquals(
                Unit,
                awaitItem(),
            )
        }

        assertEquals(
            testScreenSnackbarType,
            editAccountScreenViewModel.screenSnackbarType,
        )
    }

    @Test
    fun `updateScreenSnackbarType with refresh = false`() = runTestWithTimeout {
        initViewModel(
            coroutineScope = testScope,
        )

        editAccountScreenViewModel.refreshSignal.test {
            editAccountScreenViewModel.updateScreenSnackbarType(
                updatedEditAccountScreenSnackbarType = testScreenSnackbarType,
                shouldRefresh = false,
            )

            expectNoEvents()
        }

        assertEquals(
            testScreenSnackbarType,
            editAccountScreenViewModel.screenSnackbarType,
        )
    }

    @Test
    fun `updateSelectedAccountTypeIndex with refresh = true`() = runTestWithTimeout {
        initViewModel(
            coroutineScope = testScope,
        )

        editAccountScreenViewModel.refreshSignal.test {
            editAccountScreenViewModel.updateSelectedAccountTypeIndex(
                updatedSelectedAccountTypeIndex = TEST_ACCOUNT_TYPE_INDEX,
                shouldRefresh = true,
            )

            assertEquals(
                Unit,
                awaitItem(),
            )
        }

        assertEquals(
            TEST_ACCOUNT_TYPE_INDEX,
            editAccountScreenViewModel.selectedAccountTypeIndex,
        )
    }

    @Test
    fun `updateSelectedAccountTypeIndex with refresh = false`() = runTestWithTimeout {
        initViewModel(
            coroutineScope = testScope,
        )

        editAccountScreenViewModel.refreshSignal.test {
            editAccountScreenViewModel.updateSelectedAccountTypeIndex(
                updatedSelectedAccountTypeIndex = TEST_ACCOUNT_TYPE_INDEX,
                shouldRefresh = false,
            )

            expectNoEvents()
        }

        assertEquals(
            TEST_ACCOUNT_TYPE_INDEX,
            editAccountScreenViewModel.selectedAccountTypeIndex,
        )
    }

    private fun initViewModel(
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
        editAccountScreenViewModel = EditAccountScreenViewModel(
            coroutineScope = coroutineScope,
            savedStateHandle = savedStateHandle,
            editAccountScreenDataValidationUseCase = editAccountScreenDataValidationUseCase,
            getAllAccountsUseCase = getAllAccountsUseCase,
            getAccountUseCase = getAccountUseCase,
            navigator = navigator,
            screenUICommonState = screenUICommonState,
            updateAccountUseCase = updateAccountUseCase,
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
        const val TEST_NAME = "ABCBC"

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

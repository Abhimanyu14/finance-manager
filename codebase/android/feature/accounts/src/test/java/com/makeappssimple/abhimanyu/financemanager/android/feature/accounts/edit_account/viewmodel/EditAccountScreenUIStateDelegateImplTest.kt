package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import app.cash.turbine.test
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.UpdateAccountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.bottomsheet.EditAccountScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.snackbar.EditAccountScreenSnackbarType
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class EditAccountScreenUIStateDelegateImplTest {
    // region testing
    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(testDispatcher)
    // endregion

    // region dependencies
    private val navigator: Navigator = mock()
    private val updateAccountUseCase: UpdateAccountUseCase = mock()
    // endregion

    // region SUT
    private lateinit var editAccountScreenUIStateDelegate: EditAccountScreenUIStateDelegate
    // endregion

    @Before
    fun setUp() {
        editAccountScreenUIStateDelegate = EditAccountScreenUIStateDelegateImpl(
            coroutineScope = testScope,
            navigator = navigator,
            updateAccountUseCase = updateAccountUseCase,
        )
    }

    @After
    fun tearDown() {
    }

    // region initial state
    @Test
    fun `initial state`() {
        assertEquals(
            null,
            editAccountScreenUIStateDelegate.currentAccount
        )
        assertEquals(
            persistentListOf(AccountType.BANK, AccountType.E_WALLET),
            editAccountScreenUIStateDelegate.validAccountTypesForNewAccount
        )
        assertEquals(
            true,
            editAccountScreenUIStateDelegate.isLoading
        )
        assertEquals(
            TextFieldValue(),
            editAccountScreenUIStateDelegate.minimumAccountBalanceAmountValue
        )
        assertEquals(
            TextFieldValue(),
            editAccountScreenUIStateDelegate.name
        )
        assertEquals(
            TextFieldValue(),
            editAccountScreenUIStateDelegate.balanceAmountValue
        )
        assertEquals(
            EditAccountScreenBottomSheetType.None,
            editAccountScreenUIStateDelegate.screenBottomSheetType
        )
        assertEquals(
            EditAccountScreenSnackbarType.None,
            editAccountScreenUIStateDelegate.screenSnackbarType
        )
        assertEquals(
            0,
            editAccountScreenUIStateDelegate.selectedAccountTypeIndex
        )
    }
    // endregion

    // region refresh
    @Test
    fun refresh() = testScope.runTest {
        editAccountScreenUIStateDelegate.refreshSignal.test {
            editAccountScreenUIStateDelegate.refresh()
            assertEquals(
                Unit,
                awaitItem(),
            )
        }
    }
    // endregion

    // region state events
    @Test
    fun clearBalanceAmountValue() = testScope.runTest {
        editAccountScreenUIStateDelegate.updateBalanceAmountValue(
            updatedBalanceAmountValue = editAccountScreenUIStateDelegate.balanceAmountValue.copy(
                text = TEST_BALANCE_AMOUNT_VALUE,
            ),
            refresh = false,
        )

        editAccountScreenUIStateDelegate.refreshSignal.test {
            editAccountScreenUIStateDelegate.clearBalanceAmountValue()

            assertEquals(
                Unit,
                awaitItem(),
            )
        }

        assertEquals(
            EMPTY_STRING,
            editAccountScreenUIStateDelegate.balanceAmountValue.text,
        )
    }

    @Test
    fun clearMinimumAccountBalanceAmountValue() = testScope.runTest {
        editAccountScreenUIStateDelegate.updateMinimumAccountBalanceAmountValue(
            updatedMinimumAccountBalanceAmountValue = editAccountScreenUIStateDelegate.minimumAccountBalanceAmountValue.copy(
                text = TEST_MINIMUM_BALANCE_AMOUNT_VALUE,
            ),
            refresh = false,
        )

        editAccountScreenUIStateDelegate.refreshSignal.test {
            editAccountScreenUIStateDelegate.clearMinimumAccountBalanceAmountValue()

            assertEquals(
                Unit,
                awaitItem(),
            )
        }

        assertEquals(
            EMPTY_STRING,
            editAccountScreenUIStateDelegate.minimumAccountBalanceAmountValue.text,
        )
    }

    @Test
    fun clearName() = testScope.runTest {
        editAccountScreenUIStateDelegate.updateName(
            updatedName = editAccountScreenUIStateDelegate.name.copy(
                text = TEST_NAME,
            ),
            refresh = false,
        )

        editAccountScreenUIStateDelegate.refreshSignal.test {
            editAccountScreenUIStateDelegate.clearName()

            assertEquals(
                Unit,
                awaitItem(),
            )
        }

        assertEquals(
            EMPTY_STRING,
            editAccountScreenUIStateDelegate.name.text,
        )
    }

    @Test
    fun `completeLoading with refresh = true`() = testScope.runTest {
        editAccountScreenUIStateDelegate.startLoading(
            refresh = false,
        )

        editAccountScreenUIStateDelegate.refreshSignal.test {
            editAccountScreenUIStateDelegate.completeLoading(
                refresh = true,
            )

            assertEquals(
                Unit,
                awaitItem(),
            )
        }

        assertFalse(editAccountScreenUIStateDelegate.isLoading)
    }

    @Test
    fun `completeLoading with refresh = false`() = testScope.runTest {
        editAccountScreenUIStateDelegate.startLoading(
            refresh = false,
        )

        editAccountScreenUIStateDelegate.refreshSignal.test {
            editAccountScreenUIStateDelegate.completeLoading(
                refresh = false,
            )

            expectNoEvents()
        }

        assertFalse(editAccountScreenUIStateDelegate.isLoading)
    }

    @Test
    fun navigateUp() = testScope.runTest {
        editAccountScreenUIStateDelegate.navigateUp()

        verify(
            mock = navigator,
        ).navigateUp()
    }

    @Test
    fun resetScreenBottomSheetType() = testScope.runTest {
        editAccountScreenUIStateDelegate.refreshSignal.test {
            editAccountScreenUIStateDelegate.resetScreenBottomSheetType()

            assertEquals(
                Unit,
                awaitItem(),
            )
        }

        assertEquals(
            EditAccountScreenBottomSheetType.None,
            editAccountScreenUIStateDelegate.screenBottomSheetType,
        )
    }

    @Test
    fun `startLoading with refresh = true`() = testScope.runTest {
        editAccountScreenUIStateDelegate.refreshSignal.test {
            editAccountScreenUIStateDelegate.startLoading(
                refresh = true,
            )

            assertEquals(
                Unit,
                awaitItem(),
            )
        }

        assertTrue(editAccountScreenUIStateDelegate.isLoading)
    }

    @Test
    fun `startLoading with refresh = false`() = testScope.runTest {
        editAccountScreenUIStateDelegate.refreshSignal.test {
            editAccountScreenUIStateDelegate.startLoading(
                refresh = false,
            )

            expectNoEvents()
        }

        assertTrue(editAccountScreenUIStateDelegate.isLoading)
    }

    @Test
    fun `updateAccount when updateAccountUseCase returns true`() = testScope.runTest {
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

        editAccountScreenUIStateDelegate.refreshSignal.test {
            editAccountScreenUIStateDelegate.updateAccount()

            assertEquals(
                Unit,
                awaitItem(),
            )
            assertEquals(
                true,
                editAccountScreenUIStateDelegate.isLoading,
            )
            expectNoEvents()
        }

        verify(
            mock = navigator,
        ).navigateUp()
    }

    @Test
    @Ignore("Fix this")
    fun `updateAccount when updateAccountUseCase returns false`() = testScope.runTest {
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

        editAccountScreenUIStateDelegate.updateAccount()

        editAccountScreenUIStateDelegate.refreshSignal.test {
            assertEquals(
                true,
                editAccountScreenUIStateDelegate.isLoading,
            )
            assertEquals(
                Unit,
                awaitItem(),
            )
            assertEquals(
                false,
                editAccountScreenUIStateDelegate.isLoading,
            )
            assertEquals(
                Unit,
                awaitItem(),
            )
            expectNoEvents()
        }
    }

    @Test
    fun `updateBalanceAmountValue with refresh = true`() = testScope.runTest {
        editAccountScreenUIStateDelegate.refreshSignal.test {
            editAccountScreenUIStateDelegate.updateBalanceAmountValue(
                updatedBalanceAmountValue = editAccountScreenUIStateDelegate.balanceAmountValue.copy(
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
            editAccountScreenUIStateDelegate.balanceAmountValue.text,
        )
    }

    @Test
    fun `updateBalanceAmountValue with refresh = false`() = testScope.runTest {
        editAccountScreenUIStateDelegate.refreshSignal.test {
            editAccountScreenUIStateDelegate.updateBalanceAmountValue(
                updatedBalanceAmountValue = editAccountScreenUIStateDelegate.balanceAmountValue.copy(
                    text = TEST_BALANCE_AMOUNT_VALUE,
                ),
                refresh = false,
            )

            expectNoEvents()
        }

        assertEquals(
            TEST_BALANCE_AMOUNT_VALUE,
            editAccountScreenUIStateDelegate.balanceAmountValue.text,
        )
    }

    @Test
    fun `updateMinimumAccountBalanceAmountValue with refresh = true`() = testScope.runTest {
        editAccountScreenUIStateDelegate.refreshSignal.test {
            editAccountScreenUIStateDelegate.updateMinimumAccountBalanceAmountValue(
                updatedMinimumAccountBalanceAmountValue = editAccountScreenUIStateDelegate.minimumAccountBalanceAmountValue.copy(
                    text = TEST_MINIMUM_BALANCE_AMOUNT_VALUE,
                ),
                refresh = true,
            )

            assertEquals(
                Unit,
                awaitItem(),
            )
        }

        assertEquals(
            TEST_MINIMUM_BALANCE_AMOUNT_VALUE,
            editAccountScreenUIStateDelegate.minimumAccountBalanceAmountValue.text,
        )
    }

    @Test
    fun `updateMinimumAccountBalanceAmountValue with refresh = false`() = testScope.runTest {
        editAccountScreenUIStateDelegate.refreshSignal.test {
            editAccountScreenUIStateDelegate.updateMinimumAccountBalanceAmountValue(
                updatedMinimumAccountBalanceAmountValue = editAccountScreenUIStateDelegate.minimumAccountBalanceAmountValue.copy(
                    text = TEST_MINIMUM_BALANCE_AMOUNT_VALUE,
                ),
                refresh = false,
            )

            expectNoEvents()
        }

        assertEquals(
            TEST_MINIMUM_BALANCE_AMOUNT_VALUE,
            editAccountScreenUIStateDelegate.minimumAccountBalanceAmountValue.text,
        )
    }

    @Test
    fun `updateName with refresh = true`() = testScope.runTest {
        editAccountScreenUIStateDelegate.refreshSignal.test {
            editAccountScreenUIStateDelegate.updateName(
                updatedName = editAccountScreenUIStateDelegate.name.copy(
                    text = TEST_NAME,
                ),
                refresh = true,
            )

            assertEquals(
                Unit,
                awaitItem(),
            )
        }

        assertEquals(
            TEST_NAME,
            editAccountScreenUIStateDelegate.name.text,
        )
    }

    @Test
    fun `updateName with refresh = false`() = testScope.runTest {
        editAccountScreenUIStateDelegate.refreshSignal.test {
            editAccountScreenUIStateDelegate.updateName(
                updatedName = editAccountScreenUIStateDelegate.name.copy(
                    text = TEST_NAME,
                ),
                refresh = false,
            )

            expectNoEvents()
        }

        assertEquals(
            TEST_NAME,
            editAccountScreenUIStateDelegate.name.text,
        )
    }

    @Test
    fun `updateScreenBottomSheetType with refresh = true`() = testScope.runTest {
        editAccountScreenUIStateDelegate.refreshSignal.test {
            editAccountScreenUIStateDelegate.updateScreenBottomSheetType(
                updatedEditAccountScreenBottomSheetType = testScreenBottomSheetType,
                refresh = true,
            )

            assertEquals(
                Unit,
                awaitItem(),
            )
        }

        assertEquals(
            testScreenBottomSheetType,
            editAccountScreenUIStateDelegate.screenBottomSheetType,
        )
    }

    @Test
    fun `updateScreenBottomSheetType with refresh = false`() = testScope.runTest {
        editAccountScreenUIStateDelegate.refreshSignal.test {
            editAccountScreenUIStateDelegate.updateScreenBottomSheetType(
                updatedEditAccountScreenBottomSheetType = testScreenBottomSheetType,
                refresh = false,
            )

            expectNoEvents()
        }

        assertEquals(
            testScreenBottomSheetType,
            editAccountScreenUIStateDelegate.screenBottomSheetType,
        )
    }

    @Test
    fun `updateScreenSnackbarType with refresh = true`() = testScope.runTest {
        editAccountScreenUIStateDelegate.refreshSignal.test {
            editAccountScreenUIStateDelegate.updateScreenSnackbarType(
                updatedEditAccountScreenSnackbarType = testScreenSnackbarType,
                refresh = true,
            )

            assertEquals(
                Unit,
                awaitItem(),
            )
        }

        assertEquals(
            testScreenSnackbarType,
            editAccountScreenUIStateDelegate.screenSnackbarType,
        )
    }

    @Test
    fun `updateScreenSnackbarType with refresh = false`() = testScope.runTest {
        editAccountScreenUIStateDelegate.refreshSignal.test {
            editAccountScreenUIStateDelegate.updateScreenSnackbarType(
                updatedEditAccountScreenSnackbarType = testScreenSnackbarType,
                refresh = false,
            )

            expectNoEvents()
        }

        assertEquals(
            testScreenSnackbarType,
            editAccountScreenUIStateDelegate.screenSnackbarType,
        )
    }

    @Test
    fun `updateSelectedAccountTypeIndex with refresh = true`() = testScope.runTest {
        editAccountScreenUIStateDelegate.refreshSignal.test {
            editAccountScreenUIStateDelegate.updateSelectedAccountTypeIndex(
                updatedSelectedAccountTypeIndex = TEST_ACCOUNT_TYPE_INDEX,
                refresh = true,
            )

            assertEquals(
                Unit,
                awaitItem(),
            )
        }

        assertEquals(
            TEST_ACCOUNT_TYPE_INDEX,
            editAccountScreenUIStateDelegate.selectedAccountTypeIndex,
        )
    }

    @Test
    fun `updateSelectedAccountTypeIndex with refresh = false`() = testScope.runTest {
        editAccountScreenUIStateDelegate.refreshSignal.test {
            editAccountScreenUIStateDelegate.updateSelectedAccountTypeIndex(
                updatedSelectedAccountTypeIndex = TEST_ACCOUNT_TYPE_INDEX,
                refresh = false,
            )

            expectNoEvents()
        }

        assertEquals(
            TEST_ACCOUNT_TYPE_INDEX,
            editAccountScreenUIStateDelegate.selectedAccountTypeIndex,
        )
    }
    // endregion

    companion object {
        const val EMPTY_STRING = ""
        const val TEST_BALANCE_AMOUNT_VALUE = "100"
        const val TEST_MINIMUM_BALANCE_AMOUNT_VALUE = "100"
        const val TEST_NAME = "100"
        const val TEST_ACCOUNT_TYPE_INDEX = 1

        val testScreenBottomSheetType = EditAccountScreenBottomSheetType.None
        val testScreenSnackbarType = EditAccountScreenSnackbarType.None
    }
}

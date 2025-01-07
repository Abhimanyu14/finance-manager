package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.extensions.filter
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.state.common.ScreenUICommonState
import com.makeappssimple.abhimanyu.financemanager.android.cre.data.usecase.account.UpdateAccountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.AccountType
import com.makeappssimple.abhimanyu.financemanager.android.cre.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.bottomsheet.EditAccountScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.snackbar.EditAccountScreenSnackbarType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class EditAccountScreenUIStateDelegateImpl(
    private val coroutineScope: CoroutineScope,
    private val navigator: Navigator,
    private val screenUICommonState: ScreenUICommonState,
    private val updateAccountUseCase: UpdateAccountUseCase,
) : EditAccountScreenUIStateDelegate, ScreenUICommonState by screenUICommonState {
    // region initial data
    override var currentAccount: Account? = null
    override val validAccountTypesForNewAccount: ImmutableList<AccountType> =
        AccountType.entries.filter {
            it != AccountType.CASH
        }
    // endregion

    // region UI state
    // override var isLoading: Boolean = true
    override var minimumAccountBalanceAmountValue: TextFieldValue = TextFieldValue()
    override var name = TextFieldValue()
    override var balanceAmountValue = TextFieldValue()
    override var screenBottomSheetType: EditAccountScreenBottomSheetType =
        EditAccountScreenBottomSheetType.None
    override var screenSnackbarType: EditAccountScreenSnackbarType =
        EditAccountScreenSnackbarType.None
    override var selectedAccountTypeIndex: Int = validAccountTypesForNewAccount
        .indexOf(
            element = AccountType.BANK,
        )
    // endregion

    // region state events
    override fun clearBalanceAmountValue(): Job? {
        return updateBalanceAmountValue(
            updatedBalanceAmountValue = balanceAmountValue
                .copy(
                    text = "",
                ),
        )
    }

    override fun clearMinimumAccountBalanceAmountValue(): Job? {
        return updateMinimumAccountBalanceAmountValue(
            updatedMinimumAccountBalanceAmountValue = minimumAccountBalanceAmountValue
                .copy(
                    text = "",
                ),
        )
    }

    override fun clearName(): Job? {
        return updateName(
            updatedName = name
                .copy(
                    text = "",
                ),
        )
    }

    override fun navigateUp(): Job {
        return navigator.navigateUp()
    }

    override fun resetScreenBottomSheetType(): Job? {
        return updateScreenBottomSheetType(
            updatedEditAccountScreenBottomSheetType = EditAccountScreenBottomSheetType.None,
        )
    }

    override fun updateAccount(): Job {
        return coroutineScope.launch {
            startLoading()

            val isAccountUpdated = updateAccountUseCase(
                currentAccount = currentAccount,
                validAccountTypesForNewAccount = validAccountTypesForNewAccount,
                selectedAccountTypeIndex = selectedAccountTypeIndex,
                balanceAmountValue = balanceAmountValue.text,
                minimumAccountBalanceAmountValue = minimumAccountBalanceAmountValue.text,
                name = name.text,
            )
            if (isAccountUpdated) {
                navigator.navigateUp()
            } else {
                completeLoading()
                // TODO: Show Error
            }
        }
    }

    override fun updateBalanceAmountValue(
        updatedBalanceAmountValue: TextFieldValue,
        shouldRefresh: Boolean,
    ): Job? {
        balanceAmountValue = updatedBalanceAmountValue
        if (shouldRefresh) {
            return refresh()
        }
        return null
    }

    override fun updateMinimumAccountBalanceAmountValue(
        updatedMinimumAccountBalanceAmountValue: TextFieldValue,
        shouldRefresh: Boolean,
    ): Job? {
        minimumAccountBalanceAmountValue = updatedMinimumAccountBalanceAmountValue
        if (shouldRefresh) {
            return refresh()
        }
        return null
    }

    override fun updateName(
        updatedName: TextFieldValue,
        shouldRefresh: Boolean,
    ): Job? {
        name = updatedName
        if (shouldRefresh) {
            return refresh()
        }
        return null
    }

    override fun updateScreenBottomSheetType(
        updatedEditAccountScreenBottomSheetType: EditAccountScreenBottomSheetType,
        shouldRefresh: Boolean,
    ): Job? {
        screenBottomSheetType = updatedEditAccountScreenBottomSheetType
        if (shouldRefresh) {
            return refresh()
        }
        return null
    }

    override fun updateScreenSnackbarType(
        updatedEditAccountScreenSnackbarType: EditAccountScreenSnackbarType,
        shouldRefresh: Boolean,
    ): Job? {
        screenSnackbarType = updatedEditAccountScreenSnackbarType
        if (shouldRefresh) {
            return refresh()
        }
        return null
    }

    override fun updateSelectedAccountTypeIndex(
        updatedSelectedAccountTypeIndex: Int,
        shouldRefresh: Boolean,
    ): Job? {
        selectedAccountTypeIndex = updatedSelectedAccountTypeIndex
        if (shouldRefresh) {
            return refresh()
        }
        return null
    }
    // endregion
}

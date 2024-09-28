package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.filter
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.UpdateAccountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.bottomsheet.EditAccountScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.snackbar.EditAccountScreenSnackbarType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

internal class EditAccountScreenUIStateDelegateImpl(
    private val coroutineScope: CoroutineScope,
    private val navigator: Navigator,
    private val updateAccountUseCase: UpdateAccountUseCase,
) : EditAccountScreenUIStateDelegate {
    // region initial data
    override var currentAccount: Account? = null
    override val validAccountTypesForNewAccount: ImmutableList<AccountType> =
        AccountType.entries.filter {
            it != AccountType.CASH
        }
    // endregion

    // region UI state
    override val refreshSignal: MutableSharedFlow<Unit> = MutableSharedFlow(
        replay = 0,
        extraBufferCapacity = 2,
    )
    override var isLoading: Boolean = true
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

    // region refresh
    override fun refresh() {
        refreshSignal.tryEmit(Unit)
    }
    // endregion

    // region state events
    override fun clearBalanceAmountValue() {
        updateBalanceAmountValue(
            updatedBalanceAmountValue = balanceAmountValue
                .copy(
                    text = "",
                ),
        )
    }

    override fun clearMinimumAccountBalanceAmountValue() {
        updateMinimumAccountBalanceAmountValue(
            updatedMinimumAccountBalanceAmountValue = minimumAccountBalanceAmountValue
                .copy(
                    text = "",
                ),
        )
    }

    override fun clearName() {
        updateName(
            updatedName = name
                .copy(
                    text = "",
                ),
        )
    }

    override fun completeLoading(
        refresh: Boolean,
    ) {
        isLoading = false
        if (refresh) {
            refresh()
        }
    }

    override fun navigateUp() {
        navigator.navigateUp()
    }

    override fun resetScreenBottomSheetType() {
        updateScreenBottomSheetType(
            updatedEditAccountScreenBottomSheetType = EditAccountScreenBottomSheetType.None,
        )
    }

    override fun startLoading(
        refresh: Boolean,
    ) {
        isLoading = true
        if (refresh) {
            refresh()
        }
    }

    override fun updateAccount() {
        coroutineScope.launch {
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
        refresh: Boolean,
    ) {
        balanceAmountValue = updatedBalanceAmountValue
        if (refresh) {
            refresh()
        }
    }

    override fun updateMinimumAccountBalanceAmountValue(
        updatedMinimumAccountBalanceAmountValue: TextFieldValue,
        refresh: Boolean,
    ) {
        minimumAccountBalanceAmountValue = updatedMinimumAccountBalanceAmountValue
        if (refresh) {
            refresh()
        }
    }

    override fun updateName(
        updatedName: TextFieldValue,
        refresh: Boolean,
    ) {
        name = updatedName
        if (refresh) {
            refresh()
        }
    }

    override fun updateScreenBottomSheetType(
        updatedEditAccountScreenBottomSheetType: EditAccountScreenBottomSheetType,
        refresh: Boolean,
    ) {
        screenBottomSheetType = updatedEditAccountScreenBottomSheetType
        if (refresh) {
            refresh()
        }
    }

    override fun updateScreenSnackbarType(
        updatedEditAccountScreenSnackbarType: EditAccountScreenSnackbarType,
        refresh: Boolean,
    ) {
        screenSnackbarType = updatedEditAccountScreenSnackbarType
        if (refresh) {
            refresh()
        }
    }

    override fun updateSelectedAccountTypeIndex(
        updatedSelectedAccountTypeIndex: Int,
        refresh: Boolean,
    ) {
        selectedAccountTypeIndex = updatedSelectedAccountTypeIndex
        if (refresh) {
            refresh()
        }
    }
    // endregion
}

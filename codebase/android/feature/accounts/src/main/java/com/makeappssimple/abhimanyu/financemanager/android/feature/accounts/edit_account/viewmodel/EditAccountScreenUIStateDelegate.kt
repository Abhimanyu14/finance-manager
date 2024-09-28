package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.bottomsheet.EditAccountScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.snackbar.EditAccountScreenSnackbarType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.MutableSharedFlow

internal interface EditAccountScreenUIStateDelegate {
    // region initial data
    var currentAccount: Account?
    val validAccountTypesForNewAccount: ImmutableList<AccountType>
    // endregion

    // region UI state
    val refreshSignal: MutableSharedFlow<Unit>
    val isLoading: Boolean
    val balanceAmountValue: TextFieldValue
    val minimumAccountBalanceAmountValue: TextFieldValue
    val name: TextFieldValue
    val screenBottomSheetType: EditAccountScreenBottomSheetType
    val screenSnackbarType: EditAccountScreenSnackbarType
    val selectedAccountTypeIndex: Int
    // endregion

    // region refresh
    fun refresh()
    // endregion

    // region state events
    fun clearBalanceAmountValue()

    fun clearMinimumAccountBalanceAmountValue()

    fun clearName()

    fun completeLoading(
        refresh: Boolean = true,
    )

    fun navigateUp()

    fun resetScreenBottomSheetType()

    fun startLoading(
        refresh: Boolean = true,
    )

    fun updateAccount()

    fun updateBalanceAmountValue(
        updatedBalanceAmountValue: TextFieldValue,
        refresh: Boolean = true,
    )

    fun updateMinimumAccountBalanceAmountValue(
        updatedMinimumAccountBalanceAmountValue: TextFieldValue,
        refresh: Boolean = true,
    )

    fun updateName(
        updatedName: TextFieldValue,
        refresh: Boolean = true,
    )

    fun updateScreenBottomSheetType(
        updatedEditAccountScreenBottomSheetType: EditAccountScreenBottomSheetType,
        refresh: Boolean = true,
    )

    fun updateScreenSnackbarType(
        updatedEditAccountScreenSnackbarType: EditAccountScreenSnackbarType,
        refresh: Boolean = true,
    )

    fun updateSelectedAccountTypeIndex(
        updatedSelectedAccountTypeIndex: Int,
        refresh: Boolean = true,
    )
    // endregion
}

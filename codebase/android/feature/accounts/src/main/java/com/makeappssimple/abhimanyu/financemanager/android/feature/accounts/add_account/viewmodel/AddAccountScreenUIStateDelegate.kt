package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.AccountType
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.bottomsheet.AddAccountScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.snackbar.AddAccountScreenSnackbarType
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.state.AddAccountScreenUIState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.MutableSharedFlow

internal interface AddAccountScreenUIStateDelegate {
    // region initial data
    val validAccountTypesForNewAccount: ImmutableList<AccountType>
    // endregion

    // region UI state
    val refreshSignal: MutableSharedFlow<Unit>
    val isLoading: Boolean
    val screenBottomSheetType: AddAccountScreenBottomSheetType
    val screenSnackbarType: AddAccountScreenSnackbarType
    val selectedAccountTypeIndex: Int
    val name: TextFieldValue
    val minimumAccountBalanceAmountValue: TextFieldValue
    // endregion

    // region refresh
    fun refresh()
    // endregion

    // region state events
    fun clearMinimumAccountBalanceAmountValue(
        refresh: Boolean = true,
    )

    fun clearName(
        refresh: Boolean = true,
    )

    fun completeLoading(
        refresh: Boolean = true,
    )

    fun insertAccount(
        uiState: AddAccountScreenUIState,
    )

    fun navigateUp()

    fun resetScreenBottomSheetType()

    fun resetScreenSnackbarType()

    fun startLoading(
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
        updatedAddAccountScreenBottomSheetType: AddAccountScreenBottomSheetType,
        refresh: Boolean = true,
    )

    fun updateScreenSnackbarType(
        updatedAddAccountScreenSnackbarType: AddAccountScreenSnackbarType,
        refresh: Boolean = true,
    )

    fun updateSelectedAccountTypeIndex(
        updatedSelectedAccountTypeIndex: Int,
        refresh: Boolean = true,
    )
    // endregion
}

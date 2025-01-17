package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateDelegate
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.bottomsheet.AddAccountScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.snackbar.AddAccountScreenSnackbarType
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.state.AddAccountScreenUIState
import kotlinx.collections.immutable.ImmutableList

internal interface AddAccountScreenUIStateDelegate : ScreenUIStateDelegate {
    // region initial data
    val validAccountTypesForNewAccount: ImmutableList<AccountType>
    // endregion

    // region UI state
    val screenBottomSheetType: AddAccountScreenBottomSheetType
    val screenSnackbarType: AddAccountScreenSnackbarType
    val selectedAccountTypeIndex: Int
    val name: TextFieldValue
    val minimumAccountBalanceAmountValue: TextFieldValue
    // endregion

    // region state events
    fun clearMinimumAccountBalanceAmountValue(
        refresh: Boolean = true,
    )

    fun clearName(
        refresh: Boolean = true,
    )

    fun insertAccount(
        uiState: AddAccountScreenUIState,
    )

    fun navigateUp()

    fun resetScreenBottomSheetType()

    fun resetScreenSnackbarType()

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

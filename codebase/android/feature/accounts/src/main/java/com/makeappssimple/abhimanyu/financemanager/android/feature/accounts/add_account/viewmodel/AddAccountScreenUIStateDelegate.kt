package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.bottomsheet.AddAccountScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.snackbar.AddAccountScreenSnackbarType
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.state.AddAccountScreenUIState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.MutableStateFlow

internal interface AddAccountScreenUIStateDelegate {
    // region initial data
    val validAccountTypesForNewAccount: ImmutableList<AccountType>
    // endregion

    // region UI state
    val isLoading: MutableStateFlow<Boolean>
    val screenBottomSheetType: MutableStateFlow<AddAccountScreenBottomSheetType>
    val screenSnackbarType: MutableStateFlow<AddAccountScreenSnackbarType>
    val selectedAccountTypeIndex: MutableStateFlow<Int>
    val name: MutableStateFlow<TextFieldValue>
    val minimumAccountBalanceAmountValue: MutableStateFlow<TextFieldValue>
    // endregion

    // region loading
    fun startLoading()

    fun completeLoading()
    // endregion

    // region state events
    fun clearMinimumAccountBalanceAmountValue()

    fun clearName()

    fun insertAccount(
        uiState: AddAccountScreenUIState,
    )

    fun navigateUp()

    fun resetScreenBottomSheetType()

    fun resetScreenSnackbarType()

    fun setMinimumAccountBalanceAmountValue(
        updatedMinimumAccountBalanceAmountValue: TextFieldValue,
    )

    fun setName(
        updatedName: TextFieldValue,
    )

    fun setScreenBottomSheetType(
        updatedAddAccountScreenBottomSheetType: AddAccountScreenBottomSheetType,
    )

    fun setScreenSnackbarType(
        updatedAddAccountScreenSnackbarType: AddAccountScreenSnackbarType,
    )

    fun setSelectedAccountTypeIndex(
        updatedSelectedAccountTypeIndex: Int,
    )
    // endregion
}

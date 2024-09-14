package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.bottomsheet.EditAccountScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.snackbar.EditAccountScreenSnackbarType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.MutableStateFlow

internal interface EditAccountScreenUIStateDelegate {
    // region initial data
    var currentAccount: Account?
    val validAccountTypesForNewAccount: ImmutableList<AccountType>
    // endregion

    // region UI state
    val isLoading: MutableStateFlow<Boolean>
    val balanceAmountValue: MutableStateFlow<TextFieldValue>
    val minimumAccountBalanceAmountValue: MutableStateFlow<TextFieldValue>
    val name: MutableStateFlow<TextFieldValue>
    val screenBottomSheetType: MutableStateFlow<EditAccountScreenBottomSheetType>
    val screenSnackbarType: MutableStateFlow<EditAccountScreenSnackbarType>
    val selectedAccountTypeIndex: MutableStateFlow<Int>
    // endregion

    // region loading
    fun startLoading()

    fun completeLoading()
    // endregion

    // region state events
    fun clearBalanceAmountValue()

    fun clearMinimumAccountBalanceAmountValue()

    fun clearName()

    fun navigateUp()

    fun resetScreenBottomSheetType()

    fun setMinimumAccountBalanceAmountValue(
        updatedMinimumAccountBalanceAmountValue: TextFieldValue,
    )

    fun setBalanceAmountValue(
        updatedBalanceAmountValue: TextFieldValue,
    )

    fun setName(
        updatedName: TextFieldValue,
    )

    fun setScreenBottomSheetType(
        updatedEditAccountScreenBottomSheetType: EditAccountScreenBottomSheetType,
    )

    fun setScreenSnackbarType(
        updatedEditAccountScreenSnackbarType: EditAccountScreenSnackbarType,
    )

    fun setSelectedAccountTypeIndex(
        updatedSelectedAccountTypeIndex: Int,
    )

    fun updateAccount()
    // endregion
}

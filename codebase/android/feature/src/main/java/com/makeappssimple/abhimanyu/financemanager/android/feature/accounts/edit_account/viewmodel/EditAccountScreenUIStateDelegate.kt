package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.state.common.ScreenUICommonState
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.bottomsheet.EditAccountScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.snackbar.EditAccountScreenSnackbarType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.Job

internal interface EditAccountScreenUIStateDelegate : ScreenUICommonState {
    // region initial data
    var currentAccount: Account?
    val validAccountTypesForNewAccount: ImmutableList<AccountType>
    // endregion

    // region UI state
    val balanceAmountValue: TextFieldValue
    val minimumAccountBalanceAmountValue: TextFieldValue
    val name: TextFieldValue
    val screenBottomSheetType: EditAccountScreenBottomSheetType
    val screenSnackbarType: EditAccountScreenSnackbarType
    val selectedAccountTypeIndex: Int
    // endregion

    // region state events
    fun clearBalanceAmountValue(): Job?

    fun clearMinimumAccountBalanceAmountValue(): Job?

    fun clearName(): Job?

    fun navigateUp(): Job

    fun resetScreenBottomSheetType(): Job?

    fun updateAccount(): Job

    fun updateBalanceAmountValue(
        updatedBalanceAmountValue: TextFieldValue,
        shouldRefresh: Boolean = true,
    ): Job?

    fun updateMinimumAccountBalanceAmountValue(
        updatedMinimumAccountBalanceAmountValue: TextFieldValue,
        shouldRefresh: Boolean = true,
    ): Job?

    fun updateName(
        updatedName: TextFieldValue,
        shouldRefresh: Boolean = true,
    ): Job?

    fun updateScreenBottomSheetType(
        updatedEditAccountScreenBottomSheetType: EditAccountScreenBottomSheetType,
        shouldRefresh: Boolean = true,
    ): Job?

    fun updateScreenSnackbarType(
        updatedEditAccountScreenSnackbarType: EditAccountScreenSnackbarType,
        shouldRefresh: Boolean = true,
    ): Job?

    fun updateSelectedAccountTypeIndex(
        updatedSelectedAccountTypeIndex: Int,
        shouldRefresh: Boolean = true,
    ): Job?
    // endregion
}

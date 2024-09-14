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
    var screenBottomSheetType: AddAccountScreenBottomSheetType
    var screenSnackbarType: AddAccountScreenSnackbarType
    var selectedAccountTypeIndex: Int
    var name: TextFieldValue
    var minimumAccountBalanceAmountValue: TextFieldValue
    // endregion

    // region loading
    fun startLoading()

    fun completeLoading()

    fun <T> withLoading(
        block: () -> T,
    ): T

    suspend fun <T> withLoadingSuspend(
        block: suspend () -> T,
    ): T
    // endregion

    // region state events
    fun insertAccount(
        uiState: AddAccountScreenUIState,
    )

    fun navigateUp()
    // endregion
}

package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.filter
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toLongOrZero
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.InsertAccountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.bottomsheet.AddAccountScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.snackbar.AddAccountScreenSnackbarType
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.state.AddAccountScreenUIState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

internal class AddAccountScreenUIStateDelegateImpl(
    private val coroutineScope: CoroutineScope,
    private val insertAccountUseCase: InsertAccountUseCase,
    private val navigationKit: NavigationKit,
) : AddAccountScreenUIStateDelegate {
    // region initial data
    override val validAccountTypesForNewAccount: ImmutableList<AccountType> =
        AccountType.entries.filter {
            it != AccountType.CASH
        }
    // endregion

    // region UI state
    override val refreshSignal: MutableSharedFlow<Unit> = MutableSharedFlow(
        replay = 0,
        extraBufferCapacity = 1,
    )
    override var isLoading: Boolean = true
    override var screenBottomSheetType: AddAccountScreenBottomSheetType =
        AddAccountScreenBottomSheetType.None
    override var screenSnackbarType: AddAccountScreenSnackbarType =
        AddAccountScreenSnackbarType.None
    override var selectedAccountTypeIndex = validAccountTypesForNewAccount
        .indexOf(
            element = AccountType.BANK,
        )
    override var name = TextFieldValue()
    override var minimumAccountBalanceAmountValue = TextFieldValue()
    // endregion

    // region refresh
    override fun refresh() {
        refreshSignal.tryEmit(Unit)
    }
    // endregion

    // region state events
    override fun clearMinimumAccountBalanceAmountValue(
        refresh: Boolean,
    ) {
        minimumAccountBalanceAmountValue = minimumAccountBalanceAmountValue.copy(
            text = "",
        )
        if (refresh) {
            refresh()
        }
    }

    override fun clearName(
        refresh: Boolean,
    ) {
        name = name.copy(
            text = "",
        )
        if (refresh) {
            refresh()
        }
    }

    override fun completeLoading(
        refresh: Boolean,
    ) {
        isLoading = false
        if (refresh) {
            refresh()
        }
    }

    override fun insertAccount(
        uiState: AddAccountScreenUIState,
    ) {
        startLoading()
        coroutineScope.launch {
            val isAccountInserted = insertAccountUseCase(
                accountType = uiState.selectedAccountType,
                minimumAccountBalanceAmountValue = minimumAccountBalanceAmountValue.text.toLongOrZero(),
                name = name.text,
            )
            if (isAccountInserted == -1L) {
                // TODO(Abhi): Show error
            } else {
                navigateUp()
            }
        }
        completeLoading()
    }

    override fun navigateUp() {
        navigationKit.navigateUp()
    }

    override fun resetScreenBottomSheetType() {
        updateScreenBottomSheetType(
            updatedAddAccountScreenBottomSheetType = AddAccountScreenBottomSheetType.None,
        )
    }

    override fun resetScreenSnackbarType() {
        updateScreenSnackbarType(
            updatedAddAccountScreenSnackbarType = AddAccountScreenSnackbarType.None,
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
        updatedAddAccountScreenBottomSheetType: AddAccountScreenBottomSheetType,
        refresh: Boolean,
    ) {
        screenBottomSheetType = updatedAddAccountScreenBottomSheetType
        if (refresh) {
            refresh()
        }
    }

    override fun updateScreenSnackbarType(
        updatedAddAccountScreenSnackbarType: AddAccountScreenSnackbarType,
        refresh: Boolean,
    ) {
        screenSnackbarType = updatedAddAccountScreenSnackbarType
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

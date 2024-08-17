package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.filter
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toLongOrZero
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.InsertAccountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.bottomsheet.AddAccountScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.snackbar.AddAccountScreenSnackbarType
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.state.AddAccountScreenUIState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class AddAccountScreenUIStateDelegateImpl(
    private val coroutineScope: CoroutineScope,
    private val insertAccountUseCase: InsertAccountUseCase,
    private val navigator: Navigator,
) : AddAccountScreenUIStateDelegate {
    // region initial data
    override val validAccountTypesForNewAccount: ImmutableList<AccountType> =
        AccountType.entries.filter {
            it != AccountType.CASH
        }
    // endregion

    // region UI state
    override val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(
        value = true,
    )
    override val screenBottomSheetType: MutableStateFlow<AddAccountScreenBottomSheetType> =
        MutableStateFlow(
            value = AddAccountScreenBottomSheetType.None,
        )
    override val screenSnackbarType: MutableStateFlow<AddAccountScreenSnackbarType> =
        MutableStateFlow(
            value = AddAccountScreenSnackbarType.None,
        )
    override val selectedAccountTypeIndex: MutableStateFlow<Int> =
        MutableStateFlow(
            value = validAccountTypesForNewAccount.indexOf(
                element = AccountType.BANK,
            ),
        )
    override val name: MutableStateFlow<TextFieldValue> = MutableStateFlow(
        value = TextFieldValue(),
    )
    override val minimumAccountBalanceAmountValue: MutableStateFlow<TextFieldValue> =
        MutableStateFlow(
            value = TextFieldValue(),
        )
    // endregion

    // region loading
    override fun startLoading() {
        isLoading.update {
            true
        }
    }

    override fun completeLoading() {
        isLoading.update {
            false
        }
    }
    // endregion

    // region state events
    override fun clearMinimumAccountBalanceAmountValue() {
        setMinimumAccountBalanceAmountValue(
            updatedMinimumAccountBalanceAmountValue = minimumAccountBalanceAmountValue.value.copy(
                text = "",
            ),
        )
    }

    override fun clearName() {
        setName(
            updatedName = name.value.copy(
                text = "",
            ),
        )
    }

    override fun insertAccount(
        uiState: AddAccountScreenUIState,
    ) {
        coroutineScope.launch {
            val isAccountInserted = insertAccountUseCase(
                accountType = uiState.selectedAccountType,
                minimumAccountBalanceAmountValue = uiState.minimumAccountBalanceTextFieldValue.text.toLongOrZero(),
                name = uiState.nameTextFieldValue.text,
            )
            if (isAccountInserted == -1L) {
                // TODO(Abhi): Show error
            } else {
                navigator.navigateUp()
            }
        }
    }

    override fun navigateUp() {
        navigator.navigateUp()
    }

    override fun resetScreenBottomSheetType() {
        setScreenBottomSheetType(
            updatedAddAccountScreenBottomSheetType = AddAccountScreenBottomSheetType.None,
        )
    }

    override fun resetScreenSnackbarType() {
        setScreenSnackbarType(
            updatedAddAccountScreenSnackbarType = AddAccountScreenSnackbarType.None,
        )
    }

    override fun setMinimumAccountBalanceAmountValue(
        updatedMinimumAccountBalanceAmountValue: TextFieldValue,
    ) {
        minimumAccountBalanceAmountValue.update {
            updatedMinimumAccountBalanceAmountValue
        }
    }

    override fun setName(
        updatedName: TextFieldValue,
    ) {
        name.update {
            updatedName
        }
    }

    override fun setScreenBottomSheetType(
        updatedAddAccountScreenBottomSheetType: AddAccountScreenBottomSheetType,
    ) {
        screenBottomSheetType.update {
            updatedAddAccountScreenBottomSheetType
        }
    }

    override fun setScreenSnackbarType(
        updatedAddAccountScreenSnackbarType: AddAccountScreenSnackbarType,
    ) {
        screenSnackbarType.update {
            updatedAddAccountScreenSnackbarType
        }
    }

    override fun setSelectedAccountTypeIndex(
        updatedSelectedAccountTypeIndex: Int,
    ) {
        selectedAccountTypeIndex.update {
            updatedSelectedAccountTypeIndex
        }
    }
    // endregion
}

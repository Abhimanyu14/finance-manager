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
    override var screenBottomSheetType: AddAccountScreenBottomSheetType =
        AddAccountScreenBottomSheetType.None
        set(value) {
            withLoading {
                field = value
            }
        }
    override var screenSnackbarType: AddAccountScreenSnackbarType =
        AddAccountScreenSnackbarType.None
        set(value) {
            withLoading {
                field = value
            }
        }
    override var selectedAccountTypeIndex = validAccountTypesForNewAccount
        .indexOf(
            element = AccountType.BANK,
        )
        set(value) {
            withLoading {
                field = value
            }
        }
    override var name = TextFieldValue()
        set(value) {
            withLoading {
                field = value
            }
        }
    override var minimumAccountBalanceAmountValue = TextFieldValue()
        set(value) {
            withLoading {
                field = value
            }
        }
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

    override fun <T> withLoading(
        block: () -> T,
    ): T {
        startLoading()
        val result = block()
        completeLoading()
        return result
    }

    override suspend fun <T> withLoadingSuspend(
        block: suspend () -> T,
    ): T {
        startLoading()
        try {
            return block()
        } finally {
            completeLoading()
        }
    }
    // endregion

    // region state events
    override fun insertAccount(
        uiState: AddAccountScreenUIState,
    ) {
        withLoading {
            coroutineScope.launch {
                val isAccountInserted = insertAccountUseCase(
                    accountType = uiState.selectedAccountType,
                    minimumAccountBalanceAmountValue = uiState.minimumAccountBalanceTextFieldValue.text.toLongOrZero(),
                    name = uiState.nameTextFieldValue.text,
                )
                if (isAccountInserted == -1L) {
                    // TODO(Abhi): Show error
                } else {
                    navigateUp()
                }
            }
        }
    }

    override fun navigateUp() {
        navigator.navigateUp()
    }
    // endregion
}

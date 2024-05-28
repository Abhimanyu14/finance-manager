package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.filterDigits
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LocalMyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.viewmodel.EditAccountScreenViewModel
import kotlinx.collections.immutable.ImmutableList

@Composable
public fun EditAccountScreen(
    screenViewModel: EditAccountScreenViewModel = hiltViewModel(),
) {
    val viewModel = remember {
        screenViewModel
    }
    val myLogger = LocalMyLogger.current
    myLogger.logError(
        message = "Inside EditAccountScreen",
    )

    // region view model data
    val accounts: ImmutableList<Account> by viewModel.accounts.collectAsStateWithLifecycle()
    val originalAccount: Account? by viewModel.originalAccount.collectAsStateWithLifecycle()
    val validAccountTypes: ImmutableList<AccountType> = viewModel.validAccountTypes
    // endregion

    val uiState = rememberEditAccountScreenUIStateAndEvents(
        accounts = accounts,
        originalAccount = originalAccount,
        validAccountTypes = validAccountTypes,
    )
    val handleUIEvent = remember(
        key1 = viewModel,
        key2 = uiState,
    ) {
        { uiEvent: EditAccountScreenUIEvent ->
            when (uiEvent) {
                is EditAccountScreenUIEvent.OnCtaButtonClick -> {
                    viewModel.updateAccount(
                        selectedAccountTypeIndex = uiState.state.selectedAccountTypeIndex,
                        name = uiState.state.name.text,
                        balanceAmountValue = uiState.state.balanceAmountValue.text,
                        minimumAccountBalanceAmountValue = uiState.state.minimumBalanceAmountValue.text,
                    )
                }

                is EditAccountScreenUIEvent.OnBalanceAmountValueUpdated -> {
                    uiState.events.setBalanceAmountValue(
                        uiEvent.updatedBalanceAmountValue.copy(
                            text = uiEvent.updatedBalanceAmountValue.text.filterDigits(),
                        )
                    )
                }

                is EditAccountScreenUIEvent.OnNavigationBackButtonClick -> {
                    uiState.events.resetScreenBottomSheetType()
                }

                is EditAccountScreenUIEvent.OnClearBalanceAmountValueButtonClick -> {
                    uiState.events.setBalanceAmountValue(
                        uiState.state.balanceAmountValue.copy(
                            text = "",
                        )
                    )
                }

                is EditAccountScreenUIEvent.OnClearMinimumAccountBalanceAmountValueButtonClick -> {
                    uiState.events.setMinimumAccountBalanceAmountValue(
                        uiState.state.minimumBalanceAmountValue.copy(
                            text = "",
                        )
                    )
                }

                is EditAccountScreenUIEvent.OnClearNameButtonClick -> {
                    uiState.events.setName(
                        uiState.state.name.copy(
                            text = "",
                        )
                    )
                }

                is EditAccountScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                    viewModel.navigateUp()
                }

                is EditAccountScreenUIEvent.OnMinimumAccountBalanceAmountValueUpdated -> {
                    uiState.events.setMinimumAccountBalanceAmountValue(uiEvent.updatedMinimumAccountBalanceAmountValue)
                }

                is EditAccountScreenUIEvent.OnNameUpdated -> {
                    uiState.events.setName(uiEvent.updatedName)
                }

                is EditAccountScreenUIEvent.OnSelectedAccountTypeIndexUpdated -> {
                    uiState.events.setSelectedAccountTypeIndex(uiEvent.updatedIndex)
                }
            }
        }
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        viewModel.initViewModel()
    }

    LaunchedEffect(
        originalAccount,
    ) {
        originalAccount?.let { originalAccount ->
            uiState.events.setSelectedAccountTypeIndex(
                validAccountTypes.indexOf(
                    element = originalAccount.type,
                )
            )
            uiState.events.setName(
                uiState.state.name.copy(
                    text = originalAccount.name,
                )
            )
            uiState.events.setBalanceAmountValue(
                TextFieldValue(
                    text = originalAccount.balanceAmount.value.toString(),
                    selection = TextRange(originalAccount.balanceAmount.value.toString().length),
                )
            )
            originalAccount.minimumAccountBalanceAmount?.let { minimumAccountBalanceAmount ->
                uiState.events.setMinimumAccountBalanceAmountValue(
                    TextFieldValue(
                        text = minimumAccountBalanceAmount.value.toString(),
                        selection = TextRange(minimumAccountBalanceAmount.value.toString().length),
                    )
                )
            }
        }
    }

    EditAccountScreenUI(
        uiState = uiState.state,
        handleUIEvent = handleUIEvent,
    )
}

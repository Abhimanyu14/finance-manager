package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.screen

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toLongOrZero
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LocalMyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.viewmodel.AddAccountScreenViewModel

@Composable
public fun AddAccountScreen(
    screenViewModel: AddAccountScreenViewModel = hiltViewModel(),
) {
    val viewModel: AddAccountScreenViewModel = remember {
        screenViewModel
    }
    val myLogger = LocalMyLogger.current
    myLogger.logError(
        message = "Inside AddAccountScreen",
    )

    val focusedView = LocalView.current
    val isKeyboardOpen = WindowInsets.isImeVisible

    // region view model data
    val validAccountTypes: List<AccountType> = viewModel.validAccountTypes
    val accounts: List<Account> by viewModel.accounts.collectAsStateWithLifecycle()
    // endregion

    val uiStateAndEvents = rememberAddAccountScreenUIStateAndEvents(
        accounts = accounts,
        validAccountTypes = validAccountTypes,
    )
    val handleUIEvent = remember(
        key1 = viewModel,
        key2 = uiStateAndEvents,
    ) {
        { uiEvent: AddAccountScreenUIEvent ->
            when (uiEvent) {
                is AddAccountScreenUIEvent.OnCtaButtonClick -> {
                    uiStateAndEvents.state.selectedAccountType?.let { accountType ->
                        val minimumAccountBalanceAmount =
                            if (accountType == AccountType.BANK) {
                                Amount(
                                    value = uiStateAndEvents.state.minimumAccountBalanceTextFieldValue.text.toLongOrZero(),
                                )
                            } else {
                                null
                            }

                        viewModel.insertAccount(
                            account = Account(
                                balanceAmount = Amount(
                                    value = 0L,
                                ),
                                type = accountType,
                                minimumAccountBalanceAmount = minimumAccountBalanceAmount,
                                name = uiStateAndEvents.state.nameTextFieldValue.text,
                            ),
                        )
                    }
                    Unit
                }

                is AddAccountScreenUIEvent.OnNavigationBackButtonClick -> {
                    uiStateAndEvents.events.resetScreenBottomSheetType()
                }

                is AddAccountScreenUIEvent.OnClearMinimumAccountBalanceAmountValueButtonClick -> {
                    uiStateAndEvents.events.clearMinimumAccountBalanceAmountValue()
                }

                is AddAccountScreenUIEvent.OnClearNameButtonClick -> {
                    uiStateAndEvents.events.clearName()
                }

                is AddAccountScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                    viewModel.navigateUp()
                }

                is AddAccountScreenUIEvent.OnMinimumAccountBalanceAmountValueUpdated -> {
                    uiStateAndEvents.events.updateMinimumAccountBalanceAmountValue(
                        uiEvent.updatedMinimumAccountBalanceAmountValue,
                    )
                }

                is AddAccountScreenUIEvent.OnNameUpdated -> {
                    uiStateAndEvents.events.updateName(
                        uiEvent.updatedName,
                    )
                }

                is AddAccountScreenUIEvent.OnSelectedAccountTypeIndexUpdated -> {
                    uiStateAndEvents.events.updateSelectedAccountTypeIndex(
                        uiEvent.updatedIndex,
                    )
                }

                else -> {
                    // No-op
                }
            }
        }
    }

    LaunchedEffect(
        key1 = isKeyboardOpen,
    ) {
        myLogger.logError(
            message = "isKeyboardOpen $isKeyboardOpen $focusedView ${focusedView.isFocused}",
        )
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        viewModel.initViewModel()
    }

    AddAccountScreenUI(
        uiState = uiStateAndEvents.state,
        handleUIEvent = handleUIEvent,
    )
}

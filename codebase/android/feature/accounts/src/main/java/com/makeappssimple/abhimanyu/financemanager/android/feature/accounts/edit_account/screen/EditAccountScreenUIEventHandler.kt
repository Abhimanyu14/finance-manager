package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.screen

import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.filterDigits
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.viewmodel.EditAccountScreenViewModel

public class EditAccountScreenUIEventHandler internal constructor(
    private val viewModel: EditAccountScreenViewModel,
    private val uiStateAndEvents: EditAccountScreenUIStateAndEvents,
) {
    public fun handleUIEvent(
        uiEvent: EditAccountScreenUIEvent,
    ) {
        when (uiEvent) {
            is EditAccountScreenUIEvent.OnCtaButtonClick -> {
                viewModel.updateAccount(
                    selectedAccountTypeIndex = uiStateAndEvents.state.selectedAccountTypeIndex,
                    name = uiStateAndEvents.state.name.text,
                    balanceAmountValue = uiStateAndEvents.state.balanceAmountValue.text,
                    minimumAccountBalanceAmountValue = uiStateAndEvents.state.minimumBalanceAmountValue.text,
                )
            }

            is EditAccountScreenUIEvent.OnBalanceAmountValueUpdated -> {
                uiStateAndEvents.events.setBalanceAmountValue(
                    uiEvent.updatedBalanceAmountValue.copy(
                        text = uiEvent.updatedBalanceAmountValue.text.filterDigits(),
                    )
                )
            }

            is EditAccountScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateAndEvents.events.resetScreenBottomSheetType()
            }

            is EditAccountScreenUIEvent.OnClearBalanceAmountValueButtonClick -> {
                uiStateAndEvents.events.setBalanceAmountValue(
                    uiStateAndEvents.state.balanceAmountValue.copy(
                        text = "",
                    )
                )
            }

            is EditAccountScreenUIEvent.OnClearMinimumAccountBalanceAmountValueButtonClick -> {
                uiStateAndEvents.events.setMinimumAccountBalanceAmountValue(
                    uiStateAndEvents.state.minimumBalanceAmountValue.copy(
                        text = "",
                    )
                )
            }

            is EditAccountScreenUIEvent.OnClearNameButtonClick -> {
                uiStateAndEvents.events.setName(
                    uiStateAndEvents.state.name.copy(
                        text = "",
                    )
                )
            }

            is EditAccountScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                viewModel.navigateUp()
            }

            is EditAccountScreenUIEvent.OnMinimumAccountBalanceAmountValueUpdated -> {
                uiStateAndEvents.events.setMinimumAccountBalanceAmountValue(uiEvent.updatedMinimumAccountBalanceAmountValue)
            }

            is EditAccountScreenUIEvent.OnNameUpdated -> {
                uiStateAndEvents.events.setName(uiEvent.updatedName)
            }

            is EditAccountScreenUIEvent.OnSelectedAccountTypeIndexUpdated -> {
                uiStateAndEvents.events.setSelectedAccountTypeIndex(uiEvent.updatedIndex)
            }
        }
    }
}

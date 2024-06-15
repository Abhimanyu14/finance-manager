package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.event

import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.filterDigits
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.state.EditAccountScreenUIStateAndStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.viewmodel.EditAccountScreenViewModel

public class EditAccountScreenUIEventHandler internal constructor(
    private val viewModel: EditAccountScreenViewModel,
    private val uiStateAndStateEvents: EditAccountScreenUIStateAndStateEvents,
) {
    public fun handleUIEvent(
        uiEvent: EditAccountScreenUIEvent,
    ) {
        when (uiEvent) {
            is EditAccountScreenUIEvent.OnCtaButtonClick -> {
                viewModel.updateAccount(
                    selectedAccountTypeIndex = uiStateAndStateEvents.state.selectedAccountTypeIndex,
                    name = uiStateAndStateEvents.state.name.text,
                    balanceAmountValue = uiStateAndStateEvents.state.balanceAmountValue.text,
                    minimumAccountBalanceAmountValue = uiStateAndStateEvents.state.minimumBalanceAmountValue.text,
                )
            }

            is EditAccountScreenUIEvent.OnBalanceAmountValueUpdated -> {
                uiStateAndStateEvents.events.setBalanceAmountValue(
                    uiEvent.updatedBalanceAmountValue.copy(
                        text = uiEvent.updatedBalanceAmountValue.text.filterDigits(),
                    )
                )
            }

            is EditAccountScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateAndStateEvents.events.resetScreenBottomSheetType()
            }

            is EditAccountScreenUIEvent.OnClearBalanceAmountValueButtonClick -> {
                uiStateAndStateEvents.events.setBalanceAmountValue(
                    uiStateAndStateEvents.state.balanceAmountValue.copy(
                        text = "",
                    )
                )
            }

            is EditAccountScreenUIEvent.OnClearMinimumAccountBalanceAmountValueButtonClick -> {
                uiStateAndStateEvents.events.setMinimumAccountBalanceAmountValue(
                    uiStateAndStateEvents.state.minimumBalanceAmountValue.copy(
                        text = "",
                    )
                )
            }

            is EditAccountScreenUIEvent.OnClearNameButtonClick -> {
                uiStateAndStateEvents.events.setName(
                    uiStateAndStateEvents.state.name.copy(
                        text = "",
                    )
                )
            }

            is EditAccountScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                viewModel.navigateUp()
            }

            is EditAccountScreenUIEvent.OnMinimumAccountBalanceAmountValueUpdated -> {
                uiStateAndStateEvents.events.setMinimumAccountBalanceAmountValue(uiEvent.updatedMinimumAccountBalanceAmountValue)
            }

            is EditAccountScreenUIEvent.OnNameUpdated -> {
                uiStateAndStateEvents.events.setName(uiEvent.updatedName)
            }

            is EditAccountScreenUIEvent.OnSelectedAccountTypeIndexUpdated -> {
                uiStateAndStateEvents.events.setSelectedAccountTypeIndex(uiEvent.updatedIndex)
            }
        }
    }
}

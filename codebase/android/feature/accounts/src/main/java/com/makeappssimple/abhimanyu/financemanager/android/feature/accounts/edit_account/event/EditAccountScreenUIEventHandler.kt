package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.event

import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.filterDigits
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.state.EditAccountScreenUIStateAndStateEvents

internal class EditAccountScreenUIEventHandler internal constructor(
    private val uiStateAndStateEvents: EditAccountScreenUIStateAndStateEvents,
) {
    fun handleUIEvent(
        uiEvent: EditAccountScreenUIEvent,
    ) {
        when (uiEvent) {
            is EditAccountScreenUIEvent.OnCtaButtonClick -> {
                uiStateAndStateEvents.events.updateAccount(
                    uiStateAndStateEvents.state.selectedAccountTypeIndex,
                    uiStateAndStateEvents.state.name.text,
                    uiStateAndStateEvents.state.balanceAmountValue.text,
                    uiStateAndStateEvents.state.minimumBalanceAmountValue.text,
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
                uiStateAndStateEvents.events.clearBalanceAmountValue()
            }

            is EditAccountScreenUIEvent.OnClearMinimumAccountBalanceAmountValueButtonClick -> {
                uiStateAndStateEvents.events.clearMinimumAccountBalanceAmountValue()
            }

            is EditAccountScreenUIEvent.OnClearNameButtonClick -> {
                uiStateAndStateEvents.events.clearName()
            }

            is EditAccountScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                uiStateAndStateEvents.events.navigateUp()
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

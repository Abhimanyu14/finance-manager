package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.event

import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.state.AddAccountScreenUIStateAndStateEvents

internal class AddAccountScreenUIEventHandler internal constructor(
    private val uiStateAndStateEvents: AddAccountScreenUIStateAndStateEvents,
) {
    fun handleUIEvent(
        uiEvent: AddAccountScreenUIEvent,
    ) {
        when (uiEvent) {
            is AddAccountScreenUIEvent.OnCtaButtonClick -> {
                uiStateAndStateEvents.events.insertAccount()
            }

            is AddAccountScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateAndStateEvents.events.resetScreenBottomSheetType()
            }

            is AddAccountScreenUIEvent.OnClearMinimumAccountBalanceAmountValueButtonClick -> {
                uiStateAndStateEvents.events.clearMinimumAccountBalanceAmountValue()
            }

            is AddAccountScreenUIEvent.OnClearNameButtonClick -> {
                uiStateAndStateEvents.events.clearName()
            }

            is AddAccountScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                uiStateAndStateEvents.events.navigateUp()
            }

            is AddAccountScreenUIEvent.OnMinimumAccountBalanceAmountValueUpdated -> {
                uiStateAndStateEvents.events.setMinimumAccountBalanceAmountValue(
                    uiEvent.updatedMinimumAccountBalanceAmountValue,
                )
            }

            is AddAccountScreenUIEvent.OnNameUpdated -> {
                uiStateAndStateEvents.events.setName(uiEvent.updatedName)
            }

            is AddAccountScreenUIEvent.OnSelectedAccountTypeIndexUpdated -> {
                uiStateAndStateEvents.events.setSelectedAccountTypeIndex(uiEvent.updatedIndex)
            }

            else -> {
                // No-op
            }
        }
    }
}

package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.event

import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEventHandler
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.state.AddAccountScreenUIStateEvents

internal class AddAccountScreenUIEventHandler internal constructor(
    private val uiStateEvents: AddAccountScreenUIStateEvents,
) : ScreenUIEventHandler<AddAccountScreenUIEvent> {
    override fun handleUIEvent(
        uiEvent: AddAccountScreenUIEvent,
    ) {
        when (uiEvent) {
            is AddAccountScreenUIEvent.OnCtaButtonClick -> {
                uiStateEvents.insertAccount()
            }

            is AddAccountScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateEvents.resetScreenBottomSheetType()
            }

            is AddAccountScreenUIEvent.OnClearMinimumAccountBalanceAmountValueButtonClick -> {
                uiStateEvents.clearMinimumAccountBalanceAmountValue()
            }

            is AddAccountScreenUIEvent.OnClearNameButtonClick -> {
                uiStateEvents.clearName()
            }

            is AddAccountScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                uiStateEvents.navigateUp()
            }

            is AddAccountScreenUIEvent.OnMinimumAccountBalanceAmountValueUpdated -> {
                uiStateEvents.setMinimumAccountBalanceAmountValue(
                    uiEvent.updatedMinimumAccountBalanceAmountValue,
                )
            }

            is AddAccountScreenUIEvent.OnNameUpdated -> {
                uiStateEvents.setName(uiEvent.updatedName)
            }

            is AddAccountScreenUIEvent.OnSelectedAccountTypeIndexUpdated -> {
                uiStateEvents.setSelectedAccountTypeIndex(uiEvent.updatedIndex)
            }

            else -> {
                // No-op
            }
        }
    }
}

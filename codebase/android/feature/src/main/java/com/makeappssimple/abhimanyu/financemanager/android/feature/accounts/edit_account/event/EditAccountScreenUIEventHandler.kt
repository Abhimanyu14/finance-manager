package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.event

import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.filterDigits
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEventHandler
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.state.EditAccountScreenUIStateEvents

internal class EditAccountScreenUIEventHandler internal constructor(
    private val uiStateEvents: EditAccountScreenUIStateEvents,
) : ScreenUIEventHandler<EditAccountScreenUIEvent> {
    override fun handleUIEvent(
        uiEvent: EditAccountScreenUIEvent,
    ) {
        when (uiEvent) {
            is EditAccountScreenUIEvent.OnCtaButtonClick -> {
                uiStateEvents.updateAccount()
            }

            is EditAccountScreenUIEvent.OnBalanceAmountValueUpdated -> {
                uiStateEvents.setBalanceAmountValue(
                    uiEvent.updatedBalanceAmountValue.copy(
                        text = uiEvent.updatedBalanceAmountValue.text.filterDigits(),
                    )
                )
            }

            is EditAccountScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateEvents.resetScreenBottomSheetType()
            }

            is EditAccountScreenUIEvent.OnClearBalanceAmountValueButtonClick -> {
                uiStateEvents.clearBalanceAmountValue()
            }

            is EditAccountScreenUIEvent.OnClearMinimumAccountBalanceAmountValueButtonClick -> {
                uiStateEvents.clearMinimumAccountBalanceAmountValue()
            }

            is EditAccountScreenUIEvent.OnClearNameButtonClick -> {
                uiStateEvents.clearName()
            }

            is EditAccountScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                uiStateEvents.navigateUp()
            }

            is EditAccountScreenUIEvent.OnMinimumAccountBalanceAmountValueUpdated -> {
                uiStateEvents.setMinimumAccountBalanceAmountValue(uiEvent.updatedMinimumAccountBalanceAmountValue)
            }

            is EditAccountScreenUIEvent.OnNameUpdated -> {
                uiStateEvents.setName(uiEvent.updatedName)
            }

            is EditAccountScreenUIEvent.OnSelectedAccountTypeIndexUpdated -> {
                uiStateEvents.setSelectedAccountTypeIndex(uiEvent.updatedIndex)
            }
        }
    }
}

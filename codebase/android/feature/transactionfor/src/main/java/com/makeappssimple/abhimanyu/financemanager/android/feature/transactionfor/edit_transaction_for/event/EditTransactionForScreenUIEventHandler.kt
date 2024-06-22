package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.edit_transaction_for.event

import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.edit_transaction_for.state.EditTransactionForScreenUIStateAndStateEvents

internal class EditTransactionForScreenUIEventHandler internal constructor(
    private val uiStateAndStateEvents: EditTransactionForScreenUIStateAndStateEvents,
) {
    fun handleUIEvent(
        uiEvent: EditTransactionForScreenUIEvent,
    ) {
        when (uiEvent) {
            is EditTransactionForScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateAndStateEvents.events.resetScreenBottomSheetType()
            }

            is EditTransactionForScreenUIEvent.OnCtaButtonClick -> {
                uiStateAndStateEvents.events.updateTransactionFor(uiStateAndStateEvents.state.title.text)
            }

            is EditTransactionForScreenUIEvent.OnClearTitleButtonClick -> {
                uiStateAndStateEvents.events.setTitle(
                    uiStateAndStateEvents.state.title.copy(
                        text = "",
                    )
                )
            }

            is EditTransactionForScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                uiStateAndStateEvents.events.navigateUp()
            }

            is EditTransactionForScreenUIEvent.OnTitleUpdated -> {
                uiStateAndStateEvents.events.setTitle(uiEvent.updatedTitle)
            }
        }
    }
}

package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.event

import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.state.AddTransactionForScreenUIStateAndStateEvents

internal class AddTransactionForScreenUIEventHandler internal constructor(
    private val uiStateAndStateEvents: AddTransactionForScreenUIStateAndStateEvents,
) {
    fun handleUIEvent(
        uiEvent: AddTransactionForScreenUIEvent,
    ) {
        when (uiEvent) {
            is AddTransactionForScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateAndStateEvents.events.resetScreenBottomSheetType()
            }

            is AddTransactionForScreenUIEvent.OnCtaButtonClick -> {
                uiStateAndStateEvents.events.insertTransactionFor()
            }

            is AddTransactionForScreenUIEvent.OnClearTitleButtonClick -> {
                uiStateAndStateEvents.events.clearTitle()
            }

            is AddTransactionForScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                uiStateAndStateEvents.events.navigateUp()
            }

            is AddTransactionForScreenUIEvent.OnTitleUpdated -> {
                uiStateAndStateEvents.events.setTitle(uiEvent.updatedTitle)
            }
        }
    }
}

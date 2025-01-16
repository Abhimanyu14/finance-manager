package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.event

import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEventHandler
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.state.AddTransactionForScreenUIStateEvents

internal class AddTransactionForScreenUIEventHandler internal constructor(
    private val uiStateEvents: AddTransactionForScreenUIStateEvents,
) : ScreenUIEventHandler<AddTransactionForScreenUIEvent> {
    override fun handleUIEvent(
        uiEvent: AddTransactionForScreenUIEvent,
    ) {
        when (uiEvent) {
            is AddTransactionForScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateEvents.resetScreenBottomSheetType()
            }

            is AddTransactionForScreenUIEvent.OnCtaButtonClick -> {
                uiStateEvents.insertTransactionFor()
            }

            is AddTransactionForScreenUIEvent.OnClearTitleButtonClick -> {
                uiStateEvents.clearTitle()
            }

            is AddTransactionForScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                uiStateEvents.navigateUp()
            }

            is AddTransactionForScreenUIEvent.OnTitleUpdated -> {
                uiStateEvents.setTitle(uiEvent.updatedTitle)
            }
        }
    }
}

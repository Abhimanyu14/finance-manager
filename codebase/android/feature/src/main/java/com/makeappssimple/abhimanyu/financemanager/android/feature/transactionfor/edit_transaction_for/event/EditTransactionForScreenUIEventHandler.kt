package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.edit_transaction_for.event

import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEventHandler
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.edit_transaction_for.state.EditTransactionForScreenUIStateEvents

internal class EditTransactionForScreenUIEventHandler internal constructor(
    private val uiStateEvents: EditTransactionForScreenUIStateEvents,
) : ScreenUIEventHandler<EditTransactionForScreenUIEvent> {
    override fun handleUIEvent(
        uiEvent: EditTransactionForScreenUIEvent,
    ) {
        when (uiEvent) {
            is EditTransactionForScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateEvents.resetScreenBottomSheetType()
            }

            is EditTransactionForScreenUIEvent.OnCtaButtonClick -> {
                uiStateEvents.updateTransactionFor()
            }

            is EditTransactionForScreenUIEvent.OnClearTitleButtonClick -> {
                uiStateEvents.clearTitle()
            }

            is EditTransactionForScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                uiStateEvents.navigateUp()
            }

            is EditTransactionForScreenUIEvent.OnTitleUpdated -> {
                uiStateEvents.updateTitle(uiEvent.updatedTitle)
            }
        }
    }
}

package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.edit_transaction_for.event

import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.edit_transaction_for.state.EditTransactionForScreenUIStateAndStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.edit_transaction_for.viewmodel.EditTransactionForScreenViewModel

public class EditTransactionForScreenUIEventHandler internal constructor(
    private val viewModel: EditTransactionForScreenViewModel,
    private val uiStateAndStateEvents: EditTransactionForScreenUIStateAndStateEvents,
) {
    public fun handleUIEvent(
        uiEvent: EditTransactionForScreenUIEvent,
    ) {
        when (uiEvent) {
            is EditTransactionForScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateAndStateEvents.events.resetScreenBottomSheetType()
            }

            is EditTransactionForScreenUIEvent.OnCtaButtonClick -> {
                viewModel.updateTransactionFor(
                    title = uiStateAndStateEvents.state.title.text,
                )
            }

            is EditTransactionForScreenUIEvent.OnClearTitleButtonClick -> {
                uiStateAndStateEvents.events.setTitle(
                    uiStateAndStateEvents.state.title.copy(
                        text = "",
                    )
                )
            }

            is EditTransactionForScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                viewModel.navigateUp()
            }

            is EditTransactionForScreenUIEvent.OnTitleUpdated -> {
                uiStateAndStateEvents.events.setTitle(uiEvent.updatedTitle)
            }
        }
    }
}

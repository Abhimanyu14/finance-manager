package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.edit_transaction_for.screen

import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.edit_transaction_for.viewmodel.EditTransactionForScreenViewModel

public class EditTransactionForScreenUIEventHandler internal constructor(
    private val viewModel: EditTransactionForScreenViewModel,
    private val uiStateAndEvents: EditTransactionForScreenUIStateAndEvents,
) {
    public fun handleUIEvent(
        uiEvent: EditTransactionForScreenUIEvent,
    ) {
        when (uiEvent) {
            is EditTransactionForScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateAndEvents.events.resetScreenBottomSheetType()
            }

            is EditTransactionForScreenUIEvent.OnCtaButtonClick -> {
                viewModel.updateTransactionFor(
                    title = uiStateAndEvents.state.title.text,
                )
            }

            is EditTransactionForScreenUIEvent.OnClearTitleButtonClick -> {
                uiStateAndEvents.events.setTitle(
                    uiStateAndEvents.state.title.copy(
                        text = "",
                    )
                )
            }

            is EditTransactionForScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                viewModel.navigateUp()
            }

            is EditTransactionForScreenUIEvent.OnTitleUpdated -> {
                uiStateAndEvents.events.setTitle(uiEvent.updatedTitle)
            }
        }
    }
}

package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.screen

import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.viewmodel.AddTransactionForScreenViewModel

public class AddTransactionForScreenUIEventHandler internal constructor(
    private val viewModel: AddTransactionForScreenViewModel,
    private val uiStateAndEvents: AddTransactionForScreenUIStateAndEvents,
) {
    public fun handleUIEvent(
        uiEvent: AddTransactionForScreenUIEvent,
    ) {
        when (uiEvent) {
            is AddTransactionForScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateAndEvents.events.resetScreenBottomSheetType()
            }

            is AddTransactionForScreenUIEvent.OnBottomSheetDismissed -> {
                uiStateAndEvents.events.resetScreenBottomSheetType()
            }

            is AddTransactionForScreenUIEvent.OnCtaButtonClick -> {
                viewModel.insertTransactionFor(
                    TransactionFor(
                        title = uiStateAndEvents.state.title?.text.orEmpty(),
                    )
                )
            }

            is AddTransactionForScreenUIEvent.OnClearTitleButtonClick -> {
                uiStateAndEvents.state.title?.let { title ->
                    uiStateAndEvents.events.setTitle(
                        title.copy(
                            text = "",
                        )
                    )
                }
                Unit
            }

            is AddTransactionForScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                viewModel.navigateUp()
            }

            is AddTransactionForScreenUIEvent.OnTitleUpdated -> {
                uiStateAndEvents.events.setTitle(uiEvent.updatedTitle)
            }
        }
    }
}

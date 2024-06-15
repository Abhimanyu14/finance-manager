package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.screen

import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.viewmodel.AddTransactionForScreenViewModel

public class AddTransactionForScreenUIEventHandler internal constructor(
    private val viewModel: AddTransactionForScreenViewModel,
    private val uiStateAndStateEvents: AddTransactionForScreenUIStateAndEvents,
) {
    public fun handleUIEvent(
        uiEvent: AddTransactionForScreenUIEvent,
    ) {
        when (uiEvent) {
            is AddTransactionForScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateAndStateEvents.events.resetScreenBottomSheetType()
            }

            is AddTransactionForScreenUIEvent.OnCtaButtonClick -> {
                viewModel.insertTransactionFor(
                    TransactionFor(
                        title = uiStateAndStateEvents.state.title?.text.orEmpty(),
                    )
                )
            }

            is AddTransactionForScreenUIEvent.OnClearTitleButtonClick -> {
                uiStateAndStateEvents.state.title?.let { title ->
                    uiStateAndStateEvents.events.setTitle(
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
                uiStateAndStateEvents.events.setTitle(uiEvent.updatedTitle)
            }
        }
    }
}

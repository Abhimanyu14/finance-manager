package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.event

import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.state.AddTransactionForScreenUIStateAndStateEvents

public class AddTransactionForScreenUIEventHandler internal constructor(
    private val uiStateAndStateEvents: AddTransactionForScreenUIStateAndStateEvents,
) {
    public fun handleUIEvent(
        uiEvent: AddTransactionForScreenUIEvent,
    ) {
        when (uiEvent) {
            is AddTransactionForScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateAndStateEvents.events.resetScreenBottomSheetType()
            }

            is AddTransactionForScreenUIEvent.OnCtaButtonClick -> {
                uiStateAndStateEvents.events.insertTransactionFor(
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

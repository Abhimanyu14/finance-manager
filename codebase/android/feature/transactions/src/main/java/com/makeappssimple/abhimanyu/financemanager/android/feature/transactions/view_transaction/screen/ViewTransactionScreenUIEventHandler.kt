package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.screen

import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.viewmodel.ViewTransactionScreenViewModel

public class ViewTransactionScreenUIEventHandler internal constructor(
    private val viewModel: ViewTransactionScreenViewModel,
    private val uiStateAndEvents: ViewTransactionScreenUIStateAndEvents,
) {
    public fun handleUIEvent(
        uiEvent: ViewTransactionScreenUIEvent,
    ) {
        when (uiEvent) {
            is ViewTransactionScreenUIEvent.OnBottomSheetDismissed -> {
                uiStateAndEvents.events.resetScreenBottomSheetType()
            }

            is ViewTransactionScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateAndEvents.events.resetScreenBottomSheetType()
            }

            is ViewTransactionScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                viewModel.navigateUp()
            }

            is ViewTransactionScreenUIEvent.OnTransactionDeleteConfirmationBottomSheet.NegativeButtonClick -> {
                uiStateAndEvents.events.resetScreenBottomSheetType()
                uiStateAndEvents.events.setTransactionIdToDelete(null)
            }

            is ViewTransactionScreenUIEvent.OnTransactionDeleteConfirmationBottomSheet.PositiveButtonClick -> {
                uiStateAndEvents.state.transactionIdToDelete?.let { transactionIdToDeleteValue ->
                    viewModel.deleteTransaction(
                        transactionId = transactionIdToDeleteValue,
                    )
                    uiStateAndEvents.events.setTransactionIdToDelete(null)
                }
                uiStateAndEvents.events.resetScreenBottomSheetType()
            }

            is ViewTransactionScreenUIEvent.OnTransactionListItem.Click -> {
                viewModel.navigateToViewTransactionScreen(
                    transactionId = uiEvent.transactionId,
                )
            }

            is ViewTransactionScreenUIEvent.OnTransactionListItem.EditButtonClick -> {
                viewModel.navigateToEditTransactionScreen(
                    transactionId = uiEvent.transactionId,
                )
            }

            is ViewTransactionScreenUIEvent.OnTransactionListItem.DeleteButtonClick -> {
                uiStateAndEvents.events.setTransactionIdToDelete(uiEvent.transactionId)
                uiStateAndEvents.events.setScreenBottomSheetType(
                    ViewTransactionScreenBottomSheetType.DeleteConfirmation
                )
            }

            is ViewTransactionScreenUIEvent.OnTransactionListItem.RefundButtonClick -> {
                viewModel.navigateToAddTransactionScreen(
                    transactionId = uiEvent.transactionId,
                )
            }
        }
    }
}

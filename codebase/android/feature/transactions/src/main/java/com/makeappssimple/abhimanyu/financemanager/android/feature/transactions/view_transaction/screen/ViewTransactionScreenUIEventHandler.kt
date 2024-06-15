package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.screen

import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.viewmodel.ViewTransactionScreenViewModel

public class ViewTransactionScreenUIEventHandler internal constructor(
    private val viewModel: ViewTransactionScreenViewModel,
    private val uiStateAndStateEvents: ViewTransactionScreenUIStateAndStateEvents,
) {
    public fun handleUIEvent(
        uiEvent: ViewTransactionScreenUIEvent,
    ) {
        when (uiEvent) {
            is ViewTransactionScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateAndStateEvents.events.resetScreenBottomSheetType()
            }

            is ViewTransactionScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                viewModel.navigateUp()
            }

            is ViewTransactionScreenUIEvent.OnTransactionDeleteConfirmationBottomSheet.NegativeButtonClick -> {
                uiStateAndStateEvents.events.resetScreenBottomSheetType()
                uiStateAndStateEvents.events.setTransactionIdToDelete(null)
            }

            is ViewTransactionScreenUIEvent.OnTransactionDeleteConfirmationBottomSheet.PositiveButtonClick -> {
                uiStateAndStateEvents.state.transactionIdToDelete?.let { transactionIdToDeleteValue ->
                    viewModel.deleteTransaction(
                        transactionId = transactionIdToDeleteValue,
                    )
                    uiStateAndStateEvents.events.setTransactionIdToDelete(null)
                }
                uiStateAndStateEvents.events.resetScreenBottomSheetType()
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
                uiStateAndStateEvents.events.setTransactionIdToDelete(uiEvent.transactionId)
                uiStateAndStateEvents.events.setScreenBottomSheetType(
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

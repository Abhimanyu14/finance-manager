package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.event

import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.bottomsheet.ViewTransactionScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.state.ViewTransactionScreenUIStateAndStateEvents

internal class ViewTransactionScreenUIEventHandler internal constructor(
    private val uiStateAndStateEvents: ViewTransactionScreenUIStateAndStateEvents,
) {
    fun handleUIEvent(
        uiEvent: ViewTransactionScreenUIEvent,
    ) {
        when (uiEvent) {
            is ViewTransactionScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateAndStateEvents.events.resetScreenBottomSheetType()
            }

            is ViewTransactionScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                uiStateAndStateEvents.events.navigateUp()
            }

            is ViewTransactionScreenUIEvent.OnTransactionDeleteConfirmationBottomSheet.NegativeButtonClick -> {
                uiStateAndStateEvents.events.resetScreenBottomSheetType()
                uiStateAndStateEvents.events.setTransactionIdToDelete(null)
            }

            is ViewTransactionScreenUIEvent.OnTransactionDeleteConfirmationBottomSheet.PositiveButtonClick -> {
                uiStateAndStateEvents.events.deleteTransaction()
                uiStateAndStateEvents.events.setTransactionIdToDelete(null)
                uiStateAndStateEvents.events.resetScreenBottomSheetType()
            }

            is ViewTransactionScreenUIEvent.OnTransactionListItem.Click -> {
                uiStateAndStateEvents.events.navigateToViewTransactionScreen(uiEvent.transactionId)
            }

            is ViewTransactionScreenUIEvent.OnTransactionListItem.EditButtonClick -> {
                uiStateAndStateEvents.events.navigateToEditTransactionScreen(uiEvent.transactionId)
            }

            is ViewTransactionScreenUIEvent.OnTransactionListItem.DeleteButtonClick -> {
                uiStateAndStateEvents.events.setTransactionIdToDelete(uiEvent.transactionId)
                uiStateAndStateEvents.events.setScreenBottomSheetType(
                    ViewTransactionScreenBottomSheetType.DeleteConfirmation
                )
            }

            is ViewTransactionScreenUIEvent.OnTransactionListItem.RefundButtonClick -> {
                uiStateAndStateEvents.events.onRefundButtonClick(uiEvent.transactionId)
            }
        }
    }
}

package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.event

import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEventHandler
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.bottomsheet.ViewTransactionScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.state.ViewTransactionScreenUIStateEvents

internal class ViewTransactionScreenUIEventHandler internal constructor(
    private val uiStateEvents: ViewTransactionScreenUIStateEvents,
) : ScreenUIEventHandler<ViewTransactionScreenUIEvent> {
    override fun handleUIEvent(
        uiEvent: ViewTransactionScreenUIEvent,
    ) {
        when (uiEvent) {
            is ViewTransactionScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateEvents.resetScreenBottomSheetType()
            }

            is ViewTransactionScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                uiStateEvents.navigateUp()
            }

            is ViewTransactionScreenUIEvent.OnTransactionDeleteConfirmationBottomSheet.NegativeButtonClick -> {
                uiStateEvents.resetScreenBottomSheetType()
                uiStateEvents.setTransactionIdToDelete(null)
            }

            is ViewTransactionScreenUIEvent.OnTransactionDeleteConfirmationBottomSheet.PositiveButtonClick -> {
                uiStateEvents.deleteTransaction()
                uiStateEvents.setTransactionIdToDelete(null)
                uiStateEvents.resetScreenBottomSheetType()
            }

            is ViewTransactionScreenUIEvent.OnTransactionListItem.Click -> {
                uiStateEvents.navigateToViewTransactionScreen(uiEvent.transactionId)
            }

            is ViewTransactionScreenUIEvent.OnTransactionListItem.EditButtonClick -> {
                uiStateEvents.navigateToEditTransactionScreen(uiEvent.transactionId)
            }

            is ViewTransactionScreenUIEvent.OnTransactionListItem.DeleteButtonClick -> {
                uiStateEvents.setTransactionIdToDelete(uiEvent.transactionId)
                uiStateEvents.setScreenBottomSheetType(
                    ViewTransactionScreenBottomSheetType.DeleteConfirmation
                )
            }

            is ViewTransactionScreenUIEvent.OnTransactionListItem.RefundButtonClick -> {
                uiStateEvents.onRefundButtonClick(uiEvent.transactionId)
            }
        }
    }
}

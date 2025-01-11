package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.event

import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.bottomsheet.TransactionForValuesScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.state.TransactionForValuesScreenUIStateEvents

internal class TransactionForValuesScreenUIEventHandler internal constructor(
    private val uiStateEvents: TransactionForValuesScreenUIStateEvents,
) {
    fun handleUIEvent(
        uiEvent: TransactionForValuesScreenUIEvent,
    ) {
        when (uiEvent) {
            is TransactionForValuesScreenUIEvent.OnTransactionForValuesDeleteConfirmationBottomSheet.NegativeButtonClick -> {
                uiStateEvents.resetScreenBottomSheetType()
                uiStateEvents.setTransactionForIdToDelete(null)
            }

            is TransactionForValuesScreenUIEvent.OnTransactionForValuesDeleteConfirmationBottomSheet.PositiveButtonClick -> {
                uiStateEvents.deleteTransactionFor()
                uiStateEvents.setTransactionForIdToDelete(null)
                uiStateEvents.resetScreenBottomSheetType()
            }

            is TransactionForValuesScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateEvents.resetScreenBottomSheetType()
            }

            is TransactionForValuesScreenUIEvent.OnFloatingActionButtonClick -> {
                uiStateEvents.navigateToAddTransactionForScreen()
            }

            is TransactionForValuesScreenUIEvent.OnTransactionForValuesMenuBottomSheet.DeleteButtonClick -> {
                uiStateEvents.setTransactionForIdToDelete(uiEvent.transactionForId)
                uiStateEvents.setScreenBottomSheetType(
                    TransactionForValuesScreenBottomSheetType.DeleteConfirmation
                )
            }

            is TransactionForValuesScreenUIEvent.OnTransactionForValuesMenuBottomSheet.EditButtonClick -> {
                uiStateEvents.resetScreenBottomSheetType()
                uiStateEvents.navigateToEditTransactionForScreen(uiEvent.transactionForId)
            }

            is TransactionForValuesScreenUIEvent.OnTransactionForListItem.Click -> {
                uiStateEvents.setScreenBottomSheetType(
                    TransactionForValuesScreenBottomSheetType.Menu(
                        isDeleteVisible = uiEvent.isDeleteVisible,
                        transactionForId = uiEvent.transactionForId,
                    )
                )
            }

            is TransactionForValuesScreenUIEvent.OnTransactionForListItem.MoreOptionsIconButtonClick -> {
                uiStateEvents.setScreenBottomSheetType(
                    TransactionForValuesScreenBottomSheetType.Menu(
                        isDeleteVisible = uiEvent.isDeleteVisible,
                        transactionForId = uiEvent.transactionForId,
                    )
                )
            }

            is TransactionForValuesScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                uiStateEvents.navigateUp()
            }
        }
    }
}

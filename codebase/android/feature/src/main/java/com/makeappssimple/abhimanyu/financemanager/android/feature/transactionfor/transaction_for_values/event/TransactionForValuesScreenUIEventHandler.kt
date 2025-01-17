package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.event

import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEventHandler
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.bottomsheet.TransactionForValuesScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.state.TransactionForValuesScreenUIStateEvents

internal class TransactionForValuesScreenUIEventHandler internal constructor(
    private val uiStateEvents: TransactionForValuesScreenUIStateEvents,
) : ScreenUIEventHandler<TransactionForValuesScreenUIEvent> {
    override fun handleUIEvent(
        uiEvent: TransactionForValuesScreenUIEvent,
    ) {
        when (uiEvent) {
            is TransactionForValuesScreenUIEvent.OnTransactionForValuesDeleteConfirmationBottomSheet.NegativeButtonClick -> {
                uiStateEvents.resetScreenBottomSheetType()
                uiStateEvents.updateTransactionForIdToDelete(null)
            }

            is TransactionForValuesScreenUIEvent.OnTransactionForValuesDeleteConfirmationBottomSheet.PositiveButtonClick -> {
                uiStateEvents.deleteTransactionFor()
                uiStateEvents.updateTransactionForIdToDelete(null)
                uiStateEvents.resetScreenBottomSheetType()
            }

            is TransactionForValuesScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateEvents.resetScreenBottomSheetType()
            }

            is TransactionForValuesScreenUIEvent.OnFloatingActionButtonClick -> {
                uiStateEvents.navigateToAddTransactionForScreen()
            }

            is TransactionForValuesScreenUIEvent.OnTransactionForValuesMenuBottomSheet.DeleteButtonClick -> {
                uiStateEvents.updateTransactionForIdToDelete(uiEvent.transactionForId)
                uiStateEvents.updateScreenBottomSheetType(
                    TransactionForValuesScreenBottomSheetType.DeleteConfirmation
                )
            }

            is TransactionForValuesScreenUIEvent.OnTransactionForValuesMenuBottomSheet.EditButtonClick -> {
                uiStateEvents.resetScreenBottomSheetType()
                uiStateEvents.navigateToEditTransactionForScreen(uiEvent.transactionForId)
            }

            is TransactionForValuesScreenUIEvent.OnTransactionForListItem.Click -> {
                uiStateEvents.updateScreenBottomSheetType(
                    TransactionForValuesScreenBottomSheetType.Menu(
                        isDeleteVisible = uiEvent.isDeleteVisible,
                        transactionForId = uiEvent.transactionForId,
                    )
                )
            }

            is TransactionForValuesScreenUIEvent.OnTransactionForListItem.MoreOptionsIconButtonClick -> {
                uiStateEvents.updateScreenBottomSheetType(
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

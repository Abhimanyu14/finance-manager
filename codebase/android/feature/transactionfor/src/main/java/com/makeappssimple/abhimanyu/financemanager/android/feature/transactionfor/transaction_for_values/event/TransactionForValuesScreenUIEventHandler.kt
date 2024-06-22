package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.event

import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.bottomsheet.TransactionForValuesScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.state.TransactionForValuesScreenUIStateAndStateEvents

internal class TransactionForValuesScreenUIEventHandler internal constructor(
    private val uiStateAndStateEvents: TransactionForValuesScreenUIStateAndStateEvents,
) {
    fun handleUIEvent(
        uiEvent: TransactionForValuesScreenUIEvent,
    ) {
        when (uiEvent) {
            is TransactionForValuesScreenUIEvent.OnTransactionForValuesDeleteConfirmationBottomSheet.NegativeButtonClick -> {
                uiStateAndStateEvents.events.resetScreenBottomSheetType()
                uiStateAndStateEvents.events.setTransactionForIdToDelete(null)
            }

            is TransactionForValuesScreenUIEvent.OnTransactionForValuesDeleteConfirmationBottomSheet.PositiveButtonClick -> {
                uiStateAndStateEvents.state.transactionForIdToDelete?.let { transactionForIdToDeleteValue ->
                    uiStateAndStateEvents.events.deleteTransactionFor(transactionForIdToDeleteValue)
                    uiStateAndStateEvents.events.setTransactionForIdToDelete(null)
                }
                uiStateAndStateEvents.events.resetScreenBottomSheetType()
            }

            is TransactionForValuesScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateAndStateEvents.events.resetScreenBottomSheetType()
            }

            is TransactionForValuesScreenUIEvent.OnFloatingActionButtonClick -> {
                uiStateAndStateEvents.events.navigateToAddTransactionForScreen()
            }

            is TransactionForValuesScreenUIEvent.OnTransactionForValuesMenuBottomSheet.DeleteButtonClick -> {
                uiStateAndStateEvents.events.setTransactionForIdToDelete(uiEvent.transactionForId)
                uiStateAndStateEvents.events.setScreenBottomSheetType(
                    TransactionForValuesScreenBottomSheetType.DeleteConfirmation
                )
            }

            is TransactionForValuesScreenUIEvent.OnTransactionForValuesMenuBottomSheet.EditButtonClick -> {
                uiStateAndStateEvents.events.resetScreenBottomSheetType()
                uiStateAndStateEvents.events.navigateToEditTransactionForScreen(uiEvent.transactionForId)
            }

            is TransactionForValuesScreenUIEvent.OnTransactionForListItem.Click -> {
                uiEvent.transactionForId?.let {
                    uiStateAndStateEvents.events.setScreenBottomSheetType(
                        TransactionForValuesScreenBottomSheetType.Menu(
                            isDeleteVisible = uiEvent.isDeleteVisible,
                            transactionForId = uiEvent.transactionForId,
                        )
                    )
                }
                Unit
            }

            is TransactionForValuesScreenUIEvent.OnTransactionForListItem.MoreOptionsIconButtonClick -> {
                uiEvent.transactionForId?.let {
                    uiStateAndStateEvents.events.setScreenBottomSheetType(
                        TransactionForValuesScreenBottomSheetType.Menu(
                            isDeleteVisible = uiEvent.isDeleteVisible,
                            transactionForId = uiEvent.transactionForId,
                        )
                    )
                }
                Unit
            }

            is TransactionForValuesScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                uiStateAndStateEvents.events.navigateUp()
            }
        }
    }
}

package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.screen

import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.viewmodel.TransactionForValuesScreenViewModel

public class TransactionForValuesScreenUIEventHandler internal constructor(
    private val viewModel: TransactionForValuesScreenViewModel,
    private val uiStateAndEvents: TransactionForValuesScreenUIStateAndEvents,
) {
    public fun handleUIEvent(
        uiEvent: TransactionForValuesScreenUIEvent,
    ) {
        when (uiEvent) {
            is TransactionForValuesScreenUIEvent.OnBottomSheetDismissed -> {
                uiStateAndEvents.events.resetScreenBottomSheetType()
            }

            is TransactionForValuesScreenUIEvent.OnTransactionForValuesDeleteConfirmationBottomSheet.NegativeButtonClick -> {
                uiStateAndEvents.events.resetScreenBottomSheetType()
                uiStateAndEvents.events.setTransactionForIdToDelete(null)
            }

            is TransactionForValuesScreenUIEvent.OnTransactionForValuesDeleteConfirmationBottomSheet.PositiveButtonClick -> {
                uiStateAndEvents.state.transactionForIdToDelete?.let { transactionForIdToDeleteValue ->
                    viewModel.deleteTransactionFor(
                        id = transactionForIdToDeleteValue,
                    )
                    uiStateAndEvents.events.setTransactionForIdToDelete(null)
                }
                uiStateAndEvents.events.resetScreenBottomSheetType()
            }

            is TransactionForValuesScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateAndEvents.events.resetScreenBottomSheetType()
            }

            is TransactionForValuesScreenUIEvent.OnFloatingActionButtonClick -> {
                viewModel.navigateToAddTransactionForScreen()
            }

            is TransactionForValuesScreenUIEvent.OnTransactionForValuesMenuBottomSheet.DeleteButtonClick -> {
                uiStateAndEvents.events.setTransactionForIdToDelete(uiEvent.transactionForId)
                uiStateAndEvents.events.setScreenBottomSheetType(
                    TransactionForValuesScreenBottomSheetType.DeleteConfirmation
                )
            }

            is TransactionForValuesScreenUIEvent.OnTransactionForValuesMenuBottomSheet.EditButtonClick -> {
                uiStateAndEvents.events.resetScreenBottomSheetType()
                viewModel.navigateToEditTransactionForScreen(
                    transactionForId = uiEvent.transactionForId,
                )
            }

            is TransactionForValuesScreenUIEvent.OnTransactionForListItem.Click -> {
                uiEvent.transactionForId?.let {
                    uiStateAndEvents.events.setScreenBottomSheetType(
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
                    uiStateAndEvents.events.setScreenBottomSheetType(
                        TransactionForValuesScreenBottomSheetType.Menu(
                            isDeleteVisible = uiEvent.isDeleteVisible,
                            transactionForId = uiEvent.transactionForId,
                        )
                    )
                }
                Unit
            }

            is TransactionForValuesScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                viewModel.navigateUp()
            }
        }
    }
}

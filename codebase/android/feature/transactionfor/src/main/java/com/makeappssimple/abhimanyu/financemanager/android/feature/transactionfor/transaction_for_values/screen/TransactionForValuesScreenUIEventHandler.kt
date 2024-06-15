package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.screen

import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.viewmodel.TransactionForValuesScreenViewModel

public class TransactionForValuesScreenUIEventHandler internal constructor(
    private val viewModel: TransactionForValuesScreenViewModel,
    private val uiStateAndStateEvents: TransactionForValuesScreenUIStateAndStateEvents,
) {
    public fun handleUIEvent(
        uiEvent: TransactionForValuesScreenUIEvent,
    ) {
        when (uiEvent) {
            is TransactionForValuesScreenUIEvent.OnTransactionForValuesDeleteConfirmationBottomSheet.NegativeButtonClick -> {
                uiStateAndStateEvents.events.resetScreenBottomSheetType()
                uiStateAndStateEvents.events.setTransactionForIdToDelete(null)
            }

            is TransactionForValuesScreenUIEvent.OnTransactionForValuesDeleteConfirmationBottomSheet.PositiveButtonClick -> {
                uiStateAndStateEvents.state.transactionForIdToDelete?.let { transactionForIdToDeleteValue ->
                    viewModel.deleteTransactionFor(
                        id = transactionForIdToDeleteValue,
                    )
                    uiStateAndStateEvents.events.setTransactionForIdToDelete(null)
                }
                uiStateAndStateEvents.events.resetScreenBottomSheetType()
            }

            is TransactionForValuesScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateAndStateEvents.events.resetScreenBottomSheetType()
            }

            is TransactionForValuesScreenUIEvent.OnFloatingActionButtonClick -> {
                viewModel.navigateToAddTransactionForScreen()
            }

            is TransactionForValuesScreenUIEvent.OnTransactionForValuesMenuBottomSheet.DeleteButtonClick -> {
                uiStateAndStateEvents.events.setTransactionForIdToDelete(uiEvent.transactionForId)
                uiStateAndStateEvents.events.setScreenBottomSheetType(
                    TransactionForValuesScreenBottomSheetType.DeleteConfirmation
                )
            }

            is TransactionForValuesScreenUIEvent.OnTransactionForValuesMenuBottomSheet.EditButtonClick -> {
                uiStateAndStateEvents.events.resetScreenBottomSheetType()
                viewModel.navigateToEditTransactionForScreen(
                    transactionForId = uiEvent.transactionForId,
                )
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
                viewModel.navigateUp()
            }
        }
    }
}

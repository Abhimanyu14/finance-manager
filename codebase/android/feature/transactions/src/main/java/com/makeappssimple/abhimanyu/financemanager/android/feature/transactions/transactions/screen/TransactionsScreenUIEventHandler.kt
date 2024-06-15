package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.screen

import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.Filter
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel.TransactionsScreenViewModel

public class TransactionsScreenUIEventHandler internal constructor(
    private val viewModel: TransactionsScreenViewModel,
    private val uiStateAndStateEvents: TransactionsScreenUIStateAndStateEvents,
) {
    public fun handleUIEvent(
        uiEvent: TransactionsScreenUIEvent,
    ) {
        when (uiEvent) {
            is TransactionsScreenUIEvent.OnBottomSheetDismissed -> {
                uiStateAndStateEvents.events.resetScreenBottomSheetType()
            }

            is TransactionsScreenUIEvent.OnSelectionModeTopAppBarMoreOptionsButtonClick -> {
                uiStateAndStateEvents.events.setScreenBottomSheetType(
                    TransactionsScreenBottomSheetType.Menu
                )
            }

            is TransactionsScreenUIEvent.OnSelectionModeTopAppBarNavigationButtonClick -> {
                uiStateAndStateEvents.events.setIsInSelectionMode(false)
                uiStateAndStateEvents.events.clearSelectedTransactions()
            }

            is TransactionsScreenUIEvent.OnFilterActionButtonClick -> {
                uiStateAndStateEvents.events.setScreenBottomSheetType(
                    TransactionsScreenBottomSheetType.Filters
                )
            }

            is TransactionsScreenUIEvent.OnSortActionButtonClick -> {
                uiStateAndStateEvents.events.setScreenBottomSheetType(
                    TransactionsScreenBottomSheetType.Sort
                )
            }

            is TransactionsScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateAndStateEvents.events.setSearchText("")
                uiStateAndStateEvents.events.setSelectedFilter(Filter())
                uiStateAndStateEvents.events.setIsInSelectionMode(false)
                uiStateAndStateEvents.events.clearSelectedTransactions()
                uiStateAndStateEvents.events.resetScreenBottomSheetType()
            }

            is TransactionsScreenUIEvent.OnTransactionListItem.Click -> {
                if (uiEvent.isInSelectionMode) {
                    if (uiEvent.isSelected) {
                        uiStateAndStateEvents.events.removeFromSelectedTransactions(uiEvent.transactionId)
                    } else {
                        uiStateAndStateEvents.events.addToSelectedTransactions(uiEvent.transactionId)
                    }
                } else {
                    viewModel.navigateToViewTransactionScreen(
                        transactionId = uiEvent.transactionId,
                    )
                }
            }

            is TransactionsScreenUIEvent.OnTransactionListItem.LongClick -> {
                if (uiEvent.isInSelectionMode) {
                    if (uiEvent.isSelected) {
                        uiStateAndStateEvents.events.removeFromSelectedTransactions(uiEvent.transactionId)
                    } else {
                        uiStateAndStateEvents.events.addToSelectedTransactions(uiEvent.transactionId)
                    }
                } else {
                    uiStateAndStateEvents.events.setIsInSelectionMode(true)
                    uiStateAndStateEvents.events.addToSelectedTransactions(uiEvent.transactionId)
                }
            }

            is TransactionsScreenUIEvent.OnFloatingActionButtonClick -> {
                viewModel.navigateToAddTransactionScreen()
            }

            is TransactionsScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                viewModel.navigateUp()
            }

            is TransactionsScreenUIEvent.OnTransactionsMenuBottomSheet.SelectAllTransactionsButtonClick -> {
                uiStateAndStateEvents.events.resetScreenBottomSheetType()
                uiStateAndStateEvents.events.selectAllTransactions()
            }

            is TransactionsScreenUIEvent.OnTransactionsMenuBottomSheet.UpdateTransactionForButtonClick -> {
                uiStateAndStateEvents.events.setScreenBottomSheetType(
                    TransactionsScreenBottomSheetType.SelectTransactionFor
                )
            }

            is TransactionsScreenUIEvent.OnSearchTextUpdated -> {
                uiStateAndStateEvents.events.setSearchText(uiEvent.updatedSearchText)
            }

            is TransactionsScreenUIEvent.OnSelectedFilterUpdated -> {
                uiStateAndStateEvents.events.setSelectedFilter(uiEvent.updatedSelectedFilter)
            }

            is TransactionsScreenUIEvent.OnSelectedSortOptionUpdated -> {
                uiStateAndStateEvents.events.setSelectedSortOption(uiEvent.updatedSelectedSortOption)
            }

            is TransactionsScreenUIEvent.OnSelectTransactionForBottomSheet.ItemClick -> {
                uiStateAndStateEvents.events.setIsInSelectionMode(false)
                uiStateAndStateEvents.events.resetScreenBottomSheetType()
                viewModel.updateTransactionForValuesInTransactions(
                    selectedTransactions = uiStateAndStateEvents.state.selectedTransactions,
                    transactionForId = uiEvent.updatedTransactionForValues,
                )
                uiStateAndStateEvents.events.clearSelectedTransactions()
            }
        }
    }
}

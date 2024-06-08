package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.screen

import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.Filter
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel.TransactionsScreenViewModel

public class TransactionsScreenUIEventHandler internal constructor(
    private val viewModel: TransactionsScreenViewModel,
    private val uiStateAndEvents: TransactionsScreenUIStateAndEvents,
) {
    public fun handleUIEvent(
        uiEvent: TransactionsScreenUIEvent,
    ) {
        when (uiEvent) {
            is TransactionsScreenUIEvent.OnBottomSheetDismissed -> {
                uiStateAndEvents.events.resetScreenBottomSheetType()
            }

            is TransactionsScreenUIEvent.OnSelectionModeTopAppBarMoreOptionsButtonClick -> {
                uiStateAndEvents.events.setScreenBottomSheetType(
                    TransactionsScreenBottomSheetType.Menu
                )
            }

            is TransactionsScreenUIEvent.OnSelectionModeTopAppBarNavigationButtonClick -> {
                uiStateAndEvents.events.setIsInSelectionMode(false)
                uiStateAndEvents.events.clearSelectedTransactions()
            }

            is TransactionsScreenUIEvent.OnFilterActionButtonClick -> {
                uiStateAndEvents.events.setScreenBottomSheetType(
                    TransactionsScreenBottomSheetType.Filters
                )
            }

            is TransactionsScreenUIEvent.OnSortActionButtonClick -> {
                uiStateAndEvents.events.setScreenBottomSheetType(
                    TransactionsScreenBottomSheetType.Sort
                )
            }

            is TransactionsScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateAndEvents.events.setSearchText("")
                uiStateAndEvents.events.setSelectedFilter(Filter())
                uiStateAndEvents.events.setIsInSelectionMode(false)
                uiStateAndEvents.events.clearSelectedTransactions()
                uiStateAndEvents.events.resetScreenBottomSheetType()
            }

            is TransactionsScreenUIEvent.OnTransactionListItem.Click -> {
                if (uiEvent.isInSelectionMode) {
                    if (uiEvent.isSelected) {
                        uiStateAndEvents.events.removeFromSelectedTransactions(uiEvent.transactionId)
                    } else {
                        uiStateAndEvents.events.addToSelectedTransactions(uiEvent.transactionId)
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
                        uiStateAndEvents.events.removeFromSelectedTransactions(uiEvent.transactionId)
                    } else {
                        uiStateAndEvents.events.addToSelectedTransactions(uiEvent.transactionId)
                    }
                } else {
                    uiStateAndEvents.events.setIsInSelectionMode(true)
                    uiStateAndEvents.events.addToSelectedTransactions(uiEvent.transactionId)
                }
            }

            is TransactionsScreenUIEvent.OnFloatingActionButtonClick -> {
                viewModel.navigateToAddTransactionScreen()
            }

            is TransactionsScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                viewModel.navigateUp()
            }

            is TransactionsScreenUIEvent.OnTransactionsMenuBottomSheet.SelectAllTransactionsButtonClick -> {
                uiStateAndEvents.events.resetScreenBottomSheetType()
                uiStateAndEvents.events.selectAllTransactions()
            }

            is TransactionsScreenUIEvent.OnTransactionsMenuBottomSheet.UpdateTransactionForButtonClick -> {
                uiStateAndEvents.events.setScreenBottomSheetType(
                    TransactionsScreenBottomSheetType.SelectTransactionFor
                )
            }

            is TransactionsScreenUIEvent.OnSearchTextUpdated -> {
                uiStateAndEvents.events.setSearchText(uiEvent.updatedSearchText)
            }

            is TransactionsScreenUIEvent.OnSelectedFilterUpdated -> {
                uiStateAndEvents.events.setSelectedFilter(uiEvent.updatedSelectedFilter)
            }

            is TransactionsScreenUIEvent.OnSelectedSortOptionUpdated -> {
                uiStateAndEvents.events.setSelectedSortOption(uiEvent.updatedSelectedSortOption)
            }

            is TransactionsScreenUIEvent.OnSelectTransactionForBottomSheet.ItemClick -> {
                uiStateAndEvents.events.setIsInSelectionMode(false)
                uiStateAndEvents.events.resetScreenBottomSheetType()
                viewModel.updateTransactionForValuesInTransactions(
                    selectedTransactions = uiStateAndEvents.state.selectedTransactions,
                    transactionForId = uiEvent.updatedTransactionForValues,
                )
                uiStateAndEvents.events.clearSelectedTransactions()
            }
        }
    }
}

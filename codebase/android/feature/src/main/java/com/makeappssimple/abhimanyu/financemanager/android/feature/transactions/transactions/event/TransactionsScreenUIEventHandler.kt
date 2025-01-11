package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.event

import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.Filter
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.bottomsheet.TransactionsScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.state.TransactionsScreenUIStateEvents

internal class TransactionsScreenUIEventHandler internal constructor(
    private val uiStateEvents: TransactionsScreenUIStateEvents,
) {
    fun handleUIEvent(
        uiEvent: TransactionsScreenUIEvent,
    ) {
        when (uiEvent) {
            is TransactionsScreenUIEvent.OnBottomSheetDismissed -> {
                uiStateEvents.resetScreenBottomSheetType()
            }

            is TransactionsScreenUIEvent.OnSelectionModeTopAppBarMoreOptionsButtonClick -> {
                uiStateEvents.setScreenBottomSheetType(
                    TransactionsScreenBottomSheetType.Menu
                )
            }

            is TransactionsScreenUIEvent.OnSelectionModeTopAppBarNavigationButtonClick -> {
                uiStateEvents.setIsInSelectionMode(false)
                uiStateEvents.clearSelectedTransactions()
            }

            is TransactionsScreenUIEvent.OnFilterActionButtonClick -> {
                uiStateEvents.setScreenBottomSheetType(
                    TransactionsScreenBottomSheetType.Filters
                )
            }

            is TransactionsScreenUIEvent.OnSortActionButtonClick -> {
                uiStateEvents.setScreenBottomSheetType(
                    TransactionsScreenBottomSheetType.Sort
                )
            }

            is TransactionsScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateEvents.setSearchText("")
                uiStateEvents.setSelectedFilter(Filter())
                uiStateEvents.setIsInSelectionMode(false)
                uiStateEvents.clearSelectedTransactions()
                uiStateEvents.resetScreenBottomSheetType()
            }

            is TransactionsScreenUIEvent.OnTransactionListItem.Click -> {
                if (uiEvent.isInSelectionMode) {
                    if (uiEvent.isSelected) {
                        uiStateEvents.removeFromSelectedTransactions(uiEvent.transactionId)
                    } else {
                        uiStateEvents.addToSelectedTransactions(uiEvent.transactionId)
                    }
                } else {
                    uiStateEvents.navigateToViewTransactionScreen(uiEvent.transactionId)
                }
            }

            is TransactionsScreenUIEvent.OnTransactionListItem.LongClick -> {
                if (uiEvent.isInSelectionMode) {
                    if (uiEvent.isSelected) {
                        uiStateEvents.removeFromSelectedTransactions(uiEvent.transactionId)
                    } else {
                        uiStateEvents.addToSelectedTransactions(uiEvent.transactionId)
                    }
                } else {
                    uiStateEvents.setIsInSelectionMode(true)
                    uiStateEvents.addToSelectedTransactions(uiEvent.transactionId)
                }
            }

            is TransactionsScreenUIEvent.OnFloatingActionButtonClick -> {
                uiStateEvents.navigateToAddTransactionScreen()
            }

            is TransactionsScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                uiStateEvents.navigateUp()
            }

            is TransactionsScreenUIEvent.OnTransactionsMenuBottomSheet.SelectAllTransactionsButtonClick -> {
                uiStateEvents.resetScreenBottomSheetType()
                uiStateEvents.selectAllTransactions()
            }

            is TransactionsScreenUIEvent.OnTransactionsMenuBottomSheet.UpdateTransactionForButtonClick -> {
                uiStateEvents.setScreenBottomSheetType(
                    TransactionsScreenBottomSheetType.SelectTransactionFor
                )
            }

            is TransactionsScreenUIEvent.OnSearchTextUpdated -> {
                uiStateEvents.setSearchText(uiEvent.updatedSearchText)
            }

            is TransactionsScreenUIEvent.OnSelectedFilterUpdated -> {
                uiStateEvents.setSelectedFilter(uiEvent.updatedSelectedFilter)
            }

            is TransactionsScreenUIEvent.OnSelectedSortOptionUpdated -> {
                uiStateEvents.setSelectedSortOption(uiEvent.updatedSelectedSortOption)
            }

            is TransactionsScreenUIEvent.OnSelectTransactionForBottomSheet.ItemClick -> {
                uiStateEvents.setIsInSelectionMode(false)
                uiStateEvents.resetScreenBottomSheetType()
                uiStateEvents.updateTransactionForValuesInTransactions(
                    uiEvent.updatedTransactionForValues,
                )
                uiStateEvents.clearSelectedTransactions()
            }
        }
    }
}

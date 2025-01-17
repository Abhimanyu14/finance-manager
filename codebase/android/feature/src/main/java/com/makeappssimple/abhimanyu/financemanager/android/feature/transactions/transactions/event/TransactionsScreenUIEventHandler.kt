package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.event

import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.Filter
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEventHandler
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.bottomsheet.TransactionsScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.state.TransactionsScreenUIStateEvents

internal class TransactionsScreenUIEventHandler internal constructor(
    private val uiStateEvents: TransactionsScreenUIStateEvents,
) : ScreenUIEventHandler<TransactionsScreenUIEvent> {
    override fun handleUIEvent(
        uiEvent: TransactionsScreenUIEvent,
    ) {
        when (uiEvent) {
            is TransactionsScreenUIEvent.OnBottomSheetDismissed -> {
                uiStateEvents.resetScreenBottomSheetType()
            }

            is TransactionsScreenUIEvent.OnSelectionModeTopAppBarMoreOptionsButtonClick -> {
                uiStateEvents.updateScreenBottomSheetType(
                    TransactionsScreenBottomSheetType.Menu
                )
            }

            is TransactionsScreenUIEvent.OnSelectionModeTopAppBarNavigationButtonClick -> {
                uiStateEvents.updateIsInSelectionMode(false)
                uiStateEvents.clearSelectedTransactions()
            }

            is TransactionsScreenUIEvent.OnFilterActionButtonClick -> {
                uiStateEvents.updateScreenBottomSheetType(
                    TransactionsScreenBottomSheetType.Filters
                )
            }

            is TransactionsScreenUIEvent.OnSortActionButtonClick -> {
                uiStateEvents.updateScreenBottomSheetType(
                    TransactionsScreenBottomSheetType.Sort
                )
            }

            is TransactionsScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateEvents.updateSearchText("")
                uiStateEvents.updateSelectedFilter(Filter())
                uiStateEvents.updateIsInSelectionMode(false)
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
                    uiStateEvents.updateIsInSelectionMode(true)
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
                uiStateEvents.updateScreenBottomSheetType(
                    TransactionsScreenBottomSheetType.SelectTransactionFor
                )
            }

            is TransactionsScreenUIEvent.OnSearchTextUpdated -> {
                uiStateEvents.updateSearchText(uiEvent.updatedSearchText)
            }

            is TransactionsScreenUIEvent.OnSelectedFilterUpdated -> {
                uiStateEvents.updateSelectedFilter(uiEvent.updatedSelectedFilter)
            }

            is TransactionsScreenUIEvent.OnSelectedSortOptionUpdated -> {
                uiStateEvents.updateSelectedSortOption(uiEvent.updatedSelectedSortOption)
            }

            is TransactionsScreenUIEvent.OnSelectTransactionForBottomSheet.ItemClick -> {
                uiStateEvents.updateIsInSelectionMode(false)
                uiStateEvents.resetScreenBottomSheetType()
                uiStateEvents.updateTransactionForValuesInTransactions(
                    uiEvent.updatedTransactionForValues,
                )
                uiStateEvents.clearSelectedTransactions()
            }
        }
    }
}

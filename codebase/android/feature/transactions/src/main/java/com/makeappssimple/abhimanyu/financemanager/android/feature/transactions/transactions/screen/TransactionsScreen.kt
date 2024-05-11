package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LocalMyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.Filter
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel.TransactionsScreenViewModel

@Composable
public fun TransactionsScreen(
    screenViewModel: TransactionsScreenViewModel = hiltViewModel(),
) {
    val viewModel = remember {
        screenViewModel
    }
    val myLogger = LocalMyLogger.current
    myLogger.logError(
        message = "Inside TransactionsScreen",
    )

    val screenUIData: MyResult<TransactionsScreenUIData>? by viewModel.screenUIData.collectAsStateWithLifecycle()
    val uiStateAndEvents = rememberTransactionsScreenUIStateAndEvents(
        data = screenUIData,
    )
    val handleUIEvent = remember(
        key1 = viewModel,
        key2 = uiStateAndEvents,
    ) {
        { uiEvent: TransactionsScreenUIEvent ->
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
                    viewModel.clearSelectedTransactions()
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
                    viewModel.updateSearchText(
                        updatedSearchText = "",
                    )
                    viewModel.updateSelectedFilter(
                        updatedSelectedFilter = Filter(),
                    )
                    uiStateAndEvents.events.setIsInSelectionMode(false)
                    viewModel.clearSelectedTransactions()
                    uiStateAndEvents.events.resetScreenBottomSheetType()
                }

                is TransactionsScreenUIEvent.OnTransactionListItem.Click -> {
                    if (uiEvent.isInSelectionMode) {
                        if (uiEvent.isSelected) {
                            viewModel.removeFromSelectedTransactions(
                                transactionId = uiEvent.transactionId,
                            )
                        } else {
                            viewModel.addToSelectedTransactions(
                                transactionId = uiEvent.transactionId,
                            )
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
                            viewModel.removeFromSelectedTransactions(
                                transactionId = uiEvent.transactionId,
                            )
                        } else {
                            viewModel.addToSelectedTransactions(
                                transactionId = uiEvent.transactionId,
                            )
                        }
                    } else {
                        uiStateAndEvents.events.setIsInSelectionMode(true)
                        viewModel.addToSelectedTransactions(
                            transactionId = uiEvent.transactionId,
                        )
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
                    viewModel.selectAllTransactions()
                }

                is TransactionsScreenUIEvent.OnTransactionsMenuBottomSheet.UpdateTransactionForButtonClick -> {
                    uiStateAndEvents.events.setScreenBottomSheetType(
                        TransactionsScreenBottomSheetType.SelectTransactionFor
                    )
                }

                is TransactionsScreenUIEvent.OnSearchTextUpdated -> {
                    viewModel.updateSearchText(
                        updatedSearchText = uiEvent.updatedSearchText,
                    )
                }

                is TransactionsScreenUIEvent.OnSelectedFilterUpdated -> {
                    viewModel.updateSelectedFilter(
                        updatedSelectedFilter = uiEvent.updatedSelectedFilter,
                    )
                }

                is TransactionsScreenUIEvent.OnSelectedSortOptionUpdated -> {
                    viewModel.updateSelectedSortOption(
                        updatedSelectedSortOption = uiEvent.updatedSelectedSortOption,
                    )
                }

                is TransactionsScreenUIEvent.OnSelectTransactionForBottomSheet.ItemClick -> {
                    uiStateAndEvents.events.setIsInSelectionMode(false)
                    uiStateAndEvents.events.resetScreenBottomSheetType()
                    viewModel.updateTransactionForValuesInTransactions(
                        transactionForId = uiEvent.updatedTransactionForValues,
                    )
                }
            }
        }
    }

    TransactionsScreenUI(
        uiState = uiStateAndEvents.state,
        handleUIEvent = handleUIEvent,
    )
}

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
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel.TransactionsScreenViewModelImpl

@Composable
public fun TransactionsScreen(
    screenViewModel: TransactionsScreenViewModel = hiltViewModel<TransactionsScreenViewModelImpl>(),
) {
    val viewModel = remember {
        screenViewModel
    }
    val myLogger = LocalMyLogger.current
    myLogger.logError(
        message = "Inside TransactionsScreen",
    )

    val screenUIData: MyResult<TransactionsScreenUIData>? by viewModel.screenUIData.collectAsStateWithLifecycle()
    val uiState = rememberTransactionsScreenUIState(
        data = screenUIData,
    )
    val handleUIEvent = remember(
        key1 = viewModel,
        key2 = uiState,
    ) {
        { uiEvent: TransactionsScreenUIEvent ->
            when (uiEvent) {
                is TransactionsScreenUIEvent.OnSelectionModeTopAppBarNavigationButtonClick -> {
                    uiState.setIsInSelectionMode(false)
                    viewModel.clearSelectedTransactions()
                }

                is TransactionsScreenUIEvent.OnNavigationBackButtonClick -> {
                    viewModel.handleUIEvent(
                        TransactionsScreenUIEvent.OnSearchTextUpdated(
                            updatedSearchText = "",
                        )
                    )
                    viewModel.handleUIEvent(
                        TransactionsScreenUIEvent.OnSelectedFilterUpdated(
                            updatedSelectedFilter = Filter(),
                        )
                    )
                    uiState.setIsInSelectionMode(false)
                    viewModel.clearSelectedTransactions()
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
                        uiState.setIsInSelectionMode(true)
                        viewModel.addToSelectedTransactions(
                            transactionId = uiEvent.transactionId,
                        )
                    }
                }

                else -> {
                    viewModel.handleUIEvent(
                        uiEvent = uiEvent,
                    )
                }
            }
        }
    }

    TransactionsScreenUI(
        uiState = uiState,
        handleUIEvent = handleUIEvent,
    )
}

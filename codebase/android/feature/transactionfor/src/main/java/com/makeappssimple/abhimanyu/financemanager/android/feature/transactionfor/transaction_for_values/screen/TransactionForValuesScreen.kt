package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LocalMyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.viewmodel.TransactionForValuesScreenViewModel

@Composable
public fun TransactionForValuesScreen(
    screenViewModel: TransactionForValuesScreenViewModel = hiltViewModel(),
) {
    val viewModel = remember {
        screenViewModel
    }
    val myLogger = LocalMyLogger.current
    myLogger.logError(
        message = "Inside TransactionForValuesScreen",
    )

    // region view model data
    val transactionForValues: List<TransactionFor> by viewModel.transactionForValues.collectAsStateWithLifecycle()
    val transactionForValuesIsUsedInTransactions: List<Boolean> by viewModel.transactionForValuesIsUsedInTransactions.collectAsStateWithLifecycle()
    // endregion

    val uiStateAndEvents = rememberTransactionForValuesScreenUIStateAndEvents(
        transactionForValues = transactionForValues,
        transactionForValuesIsUsedInTransactions = transactionForValuesIsUsedInTransactions,
    )
    val handleUIEvent = remember(
        key1 = viewModel,
        key2 = uiStateAndEvents,
    ) {
        { uiEvent: TransactionForValuesScreenUIEvent ->
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

    TransactionForValuesScreenUI(
        uiState = uiStateAndEvents.state,
        handleUIEvent = handleUIEvent,
    )
}

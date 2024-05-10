package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LocalMyLogger
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

    val screenUIData: MyResult<TransactionForValuesScreenUIData>? by viewModel.screenUIData.collectAsStateWithLifecycle()
    val uiState = rememberTransactionForValuesScreenUIState(
        data = screenUIData,
    )
    val handleUIEvent = remember(
        key1 = viewModel,
        key2 = uiState,
    ) {
        { uiEvent: TransactionForValuesScreenUIEvent ->
            when (uiEvent) {
                is TransactionForValuesScreenUIEvent.OnBottomSheetDismissed -> {
                    uiState.resetScreenBottomSheetType()
                }

                is TransactionForValuesScreenUIEvent.OnTransactionForValuesDeleteConfirmationBottomSheet.NegativeButtonClick -> {
                    uiState.resetScreenBottomSheetType()
                    uiState.setTransactionForIdToDelete(null)
                }

                is TransactionForValuesScreenUIEvent.OnTransactionForValuesDeleteConfirmationBottomSheet.PositiveButtonClick -> {
                    uiState.transactionForIdToDelete?.let { transactionForIdToDeleteValue ->
                        viewModel.deleteTransactionFor(
                            id = transactionForIdToDeleteValue,
                        )
                        uiState.setTransactionForIdToDelete(null)
                    }
                    uiState.resetScreenBottomSheetType()
                }

                is TransactionForValuesScreenUIEvent.OnNavigationBackButtonClick -> {
                    uiState.resetScreenBottomSheetType()
                }

                is TransactionForValuesScreenUIEvent.OnFloatingActionButtonClick -> {
                    viewModel.navigateToAddTransactionForScreen()
                }

                is TransactionForValuesScreenUIEvent.OnTransactionForValuesMenuBottomSheet.DeleteButtonClick -> {
                    uiState.setTransactionForIdToDelete(uiEvent.transactionForId)
                    uiState.setScreenBottomSheetType(
                        TransactionForValuesScreenBottomSheetType.DeleteConfirmation
                    )
                }

                is TransactionForValuesScreenUIEvent.OnTransactionForValuesMenuBottomSheet.EditButtonClick -> {
                    uiState.resetScreenBottomSheetType()
                    viewModel.navigateToEditTransactionForScreen(
                        transactionForId = uiEvent.transactionForId,
                    )
                }

                is TransactionForValuesScreenUIEvent.OnTransactionForListItem.Click -> {
                    uiEvent.transactionForId?.let {
                        uiState.setScreenBottomSheetType(
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
                        uiState.setScreenBottomSheetType(
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
        uiState = uiState,
        handleUIEvent = handleUIEvent,
    )
}

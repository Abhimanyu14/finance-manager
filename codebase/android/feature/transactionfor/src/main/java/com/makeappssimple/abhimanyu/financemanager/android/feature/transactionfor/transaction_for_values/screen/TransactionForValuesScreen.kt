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
                is TransactionForValuesScreenUIEvent.OnTransactionForValuesDeleteConfirmationBottomSheet.PositiveButtonClick -> {
                    viewModel.deleteTransactionFor(
                        id = uiEvent.transactionForId,
                    )
                }

                is TransactionForValuesScreenUIEvent.OnFloatingActionButtonClick -> {
                    viewModel.navigateToAddTransactionForScreen()
                }

                is TransactionForValuesScreenUIEvent.OnTransactionForValuesMenuBottomSheet.EditButtonClick -> {
                    viewModel.navigateToEditTransactionForScreen(
                        transactionForId = uiEvent.transactionForId,
                    )
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

package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LocalMyLogger
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.viewmodel.ViewTransactionScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.viewmodel.ViewTransactionScreenViewModelImpl

@Composable
public fun ViewTransactionScreen(
    screenViewModel: ViewTransactionScreenViewModel = hiltViewModel<ViewTransactionScreenViewModelImpl>(),
) {
    val viewModel = remember {
        screenViewModel
    }
    val myLogger = LocalMyLogger.current
    myLogger.logError(
        message = "Inside ViewTransactionScreen",
    )

    val screenUIData: MyResult<ViewTransactionScreenUIData>? by viewModel.screenUIData.collectAsStateWithLifecycle()
    val uiState = rememberViewTransactionScreenUIState(
        data = screenUIData,
    )
    val handleUIEvent = remember(
        key1 = viewModel,
        key2 = uiState,
    ) {
        { uiEvent: ViewTransactionScreenUIEvent ->
            when (uiEvent) {
                is ViewTransactionScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                    viewModel.navigateUp()
                }

                is ViewTransactionScreenUIEvent.OnTransactionDeleteConfirmationBottomSheet.DeleteButtonClick -> {
                    viewModel.deleteTransaction(
                        transactionId = uiEvent.transactionId,
                    )
                }

                is ViewTransactionScreenUIEvent.OnTransactionListItem.Click -> {
                    viewModel.navigateToViewTransactionScreen(
                        transactionId = uiEvent.transactionId,
                    )
                }

                is ViewTransactionScreenUIEvent.OnTransactionListItem.EditButtonClick -> {
                    viewModel.navigateToEditTransactionScreen(
                        transactionId = uiEvent.transactionId,
                    )
                }

                is ViewTransactionScreenUIEvent.OnTransactionListItem.RefundButtonClick -> {
                    viewModel.navigateToAddTransactionScreen(
                        transactionId = uiEvent.transactionId,
                    )
                }
            }
        }
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        viewModel.initViewModel()
    }

    ViewTransactionScreenUI(
        uiState = uiState,
        handleUIEvent = handleUIEvent,
    )
}

package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LocalMyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transaction.TransactionListItemData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.viewmodel.ViewTransactionScreenViewModel
import kotlinx.collections.immutable.ImmutableList

@Composable
public fun ViewTransactionScreen(
    screenViewModel: ViewTransactionScreenViewModel = hiltViewModel(),
) {
    val viewModel = remember {
        screenViewModel
    }
    val myLogger = LocalMyLogger.current
    myLogger.logError(
        message = "Inside ViewTransactionScreen",
    )

    // region view model data
    val currentTransactionListItemData: TransactionListItemData? by viewModel.currentTransactionListItemData.collectAsStateWithLifecycle()
    val originalTransactionListItemData: TransactionListItemData? by viewModel.originalTransactionListItemData.collectAsStateWithLifecycle()
    val refundTransactionListItemData: ImmutableList<TransactionListItemData> by viewModel.refundTransactionListItemData.collectAsStateWithLifecycle()
    // endregion

    val uiStateAndEvents = rememberViewTransactionScreenUIStateAndEvents(
        currentTransactionListItemData = currentTransactionListItemData,
        originalTransactionListItemData = originalTransactionListItemData,
        refundTransactionListItemData = refundTransactionListItemData,
    )
    val handleUIEvent = remember(
        key1 = viewModel,
        key2 = uiStateAndEvents,
    ) {
        { uiEvent: ViewTransactionScreenUIEvent ->
            when (uiEvent) {
                is ViewTransactionScreenUIEvent.OnBottomSheetDismissed -> {
                    uiStateAndEvents.events.resetScreenBottomSheetType()
                }

                is ViewTransactionScreenUIEvent.OnNavigationBackButtonClick -> {
                    uiStateAndEvents.events.resetScreenBottomSheetType()
                }

                is ViewTransactionScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                    viewModel.navigateUp()
                }

                is ViewTransactionScreenUIEvent.OnTransactionDeleteConfirmationBottomSheet.NegativeButtonClick -> {
                    uiStateAndEvents.events.resetScreenBottomSheetType()
                    uiStateAndEvents.events.setTransactionIdToDelete(null)
                }

                is ViewTransactionScreenUIEvent.OnTransactionDeleteConfirmationBottomSheet.PositiveButtonClick -> {
                    uiStateAndEvents.state.transactionIdToDelete?.let { transactionIdToDeleteValue ->
                        viewModel.deleteTransaction(
                            transactionId = transactionIdToDeleteValue,
                        )
                        uiStateAndEvents.events.setTransactionIdToDelete(null)
                    }
                    uiStateAndEvents.events.resetScreenBottomSheetType()
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

                is ViewTransactionScreenUIEvent.OnTransactionListItem.DeleteButtonClick -> {
                    uiStateAndEvents.events.setTransactionIdToDelete(uiEvent.transactionId)
                    uiStateAndEvents.events.setScreenBottomSheetType(
                        ViewTransactionScreenBottomSheetType.DeleteConfirmation
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
        uiState = uiStateAndEvents.state,
        handleUIEvent = handleUIEvent,
    )
}

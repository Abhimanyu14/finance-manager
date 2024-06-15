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
    val myLogger = LocalMyLogger.current
    myLogger.logError(
        message = "Inside ViewTransactionScreen",
    )

    // region view model data
    val currentTransactionListItemData: TransactionListItemData? by screenViewModel.currentTransactionListItemData.collectAsStateWithLifecycle()
    val originalTransactionListItemData: TransactionListItemData? by screenViewModel.originalTransactionListItemData.collectAsStateWithLifecycle()
    val refundTransactionListItemData: ImmutableList<TransactionListItemData> by screenViewModel.refundTransactionListItemData.collectAsStateWithLifecycle()
    // endregion

    val uiStateAndEvents = rememberViewTransactionScreenUIStateAndEvents(
        currentTransactionListItemData = currentTransactionListItemData,
        originalTransactionListItemData = originalTransactionListItemData,
        refundTransactionListItemData = refundTransactionListItemData,
    )
    val screenUIEventHandler = remember(
        key1 = screenViewModel,
        key2 = uiStateAndEvents,
    ) {
        ViewTransactionScreenUIEventHandler(
            viewModel = screenViewModel,
            uiStateAndStateEvents = uiStateAndEvents,
        )
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.initViewModel()
    }

    ViewTransactionScreenUI(
        uiState = uiStateAndEvents.state,
        handleUIEvent = screenUIEventHandler::handleUIEvent,
    )
}

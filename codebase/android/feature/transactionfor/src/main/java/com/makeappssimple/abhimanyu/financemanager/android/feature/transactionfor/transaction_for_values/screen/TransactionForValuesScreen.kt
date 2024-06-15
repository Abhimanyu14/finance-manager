package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LocalMyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.viewmodel.TransactionForValuesScreenViewModel
import kotlinx.collections.immutable.ImmutableList

@Composable
public fun TransactionForValuesScreen(
    screenViewModel: TransactionForValuesScreenViewModel = hiltViewModel(),
) {
    val myLogger = LocalMyLogger.current
    myLogger.logError(
        message = "Inside TransactionForValuesScreen",
    )

    // region view model data
    val transactionForValues: ImmutableList<TransactionFor> by screenViewModel.transactionForValues.collectAsStateWithLifecycle()
    val transactionForValuesIsUsedInTransactions: ImmutableList<Boolean> by screenViewModel.transactionForValuesIsUsedInTransactions.collectAsStateWithLifecycle()
    // endregion

    val uiStateAndEvents = rememberTransactionForValuesScreenUIStateAndEvents(
        transactionForValues = transactionForValues,
        transactionForValuesIsUsedInTransactions = transactionForValuesIsUsedInTransactions,
    )
    val screenUIEventHandler = remember(
        key1 = screenViewModel,
        key2 = uiStateAndEvents,
    ) {
        TransactionForValuesScreenUIEventHandler(
            viewModel = screenViewModel,
            uiStateAndStateEvents = uiStateAndEvents,
        )
    }

    TransactionForValuesScreenUI(
        uiState = uiStateAndEvents.state,
        handleUIEvent = screenUIEventHandler::handleUIEvent,
    )
}

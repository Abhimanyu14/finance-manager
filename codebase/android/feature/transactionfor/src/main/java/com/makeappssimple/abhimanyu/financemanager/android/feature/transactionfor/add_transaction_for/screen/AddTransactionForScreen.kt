package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LocalMyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.viewmodel.AddTransactionForScreenViewModel
import kotlinx.collections.immutable.ImmutableList

@Composable
public fun AddTransactionForScreen(
    screenViewModel: AddTransactionForScreenViewModel = hiltViewModel(),
) {
    val myLogger = LocalMyLogger.current
    myLogger.logError(
        message = "Inside AddTransactionForScreen",
    )

    // region view model data
    val transactionForValues: ImmutableList<TransactionFor> by screenViewModel.transactionForValues.collectAsStateWithLifecycle()
    // endregion

    val uiStateAndEvents = rememberAddTransactionForScreenUIStateAndEvents(
        transactionForValues = transactionForValues,
    )
    val screenUIEventHandler = remember(
        key1 = screenViewModel,
        key2 = uiStateAndEvents,
    ) {
        AddTransactionForScreenUIEventHandler(
            viewModel = screenViewModel,
            uiStateAndStateEvents = uiStateAndEvents,
        )
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.initViewModel()
    }

    AddTransactionForScreenUI(
        uiState = uiStateAndEvents.state,
        handleUIEvent = screenUIEventHandler::handleUIEvent,
    )
}

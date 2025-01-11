package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LocalLogKit
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.event.AddTransactionForScreenUIEventHandler
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.state.AddTransactionForScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.state.AddTransactionForScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.viewmodel.AddTransactionForScreenViewModel

@Composable
public fun AddTransactionForScreen(
    screenViewModel: AddTransactionForScreenViewModel = hiltViewModel(),
) {
    val myLogger = LocalLogKit.current
    myLogger.logInfo(
        message = "Inside AddTransactionForScreen",
    )

    val uiState: AddTransactionForScreenUIState by screenViewModel.uiState.collectAsStateWithLifecycle()
    val uiStateEvents: AddTransactionForScreenUIStateEvents = screenViewModel.uiStateEvents

    val screenUIEventHandler = remember(
        key1 = uiStateEvents,
    ) {
        AddTransactionForScreenUIEventHandler(
            uiStateEvents = uiStateEvents,
        )
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.initViewModel()
    }

    AddTransactionForScreenUI(
        uiState = uiState,
        handleUIEvent = screenUIEventHandler::handleUIEvent,
    )
}

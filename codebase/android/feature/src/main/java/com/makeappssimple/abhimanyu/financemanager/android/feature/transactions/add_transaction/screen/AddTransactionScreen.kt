package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.event.AddTransactionScreenUIEventHandler
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.state.AddTransactionScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.state.AddTransactionScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.viewmodel.AddTransactionScreenViewModel

@Composable
public fun AddTransactionScreen(
    screenViewModel: AddTransactionScreenViewModel = hiltViewModel(),
) {
    screenViewModel.logKit.logInfo(
        message = "Inside AddTransactionScreen",
    )

    val uiState: AddTransactionScreenUIState by screenViewModel.uiState.collectAsStateWithLifecycle()
    val uiStateEvents: AddTransactionScreenUIStateEvents = screenViewModel.uiStateEvents

    val screenUIEventHandler = remember(
        key1 = uiStateEvents,
    ) {
        AddTransactionScreenUIEventHandler(
            uiStateEvents = uiStateEvents,
        )
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.initViewModel()
    }

    AddTransactionScreenUI(
        uiState = uiState,
        handleUIEvent = screenUIEventHandler::handleUIEvent,
    )
}

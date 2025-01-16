package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.event.TransactionsScreenUIEventHandler
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.state.TransactionsScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.state.TransactionsScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel.TransactionsScreenViewModel

@Composable
public fun TransactionsScreen(
    screenViewModel: TransactionsScreenViewModel = hiltViewModel(),
) {
    screenViewModel.logKit.logInfo(
        message = "Inside TransactionsScreen",
    )

    val uiState: TransactionsScreenUIState by screenViewModel.uiState.collectAsStateWithLifecycle()
    val uiStateEvents: TransactionsScreenUIStateEvents = screenViewModel.uiStateEvents

    val screenUIEventHandler = remember(
        key1 = uiStateEvents,
    ) {
        TransactionsScreenUIEventHandler(
            uiStateEvents = uiStateEvents,
        )
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.initViewModel()
    }

    TransactionsScreenUI(
        uiState = uiState,
        handleUIEvent = screenUIEventHandler::handleUIEvent,
    )
}

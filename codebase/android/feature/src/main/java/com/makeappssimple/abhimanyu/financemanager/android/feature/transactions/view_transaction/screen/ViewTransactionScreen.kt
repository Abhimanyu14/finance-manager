package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.event.ViewTransactionScreenUIEventHandler
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.state.ViewTransactionScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.state.ViewTransactionScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.viewmodel.ViewTransactionScreenViewModel

@Composable
public fun ViewTransactionScreen(
    screenViewModel: ViewTransactionScreenViewModel = hiltViewModel(),
) {
    screenViewModel.logKit.logInfo(
        message = "Inside ViewTransactionScreen",
    )

    val uiState: ViewTransactionScreenUIState by screenViewModel.uiState.collectAsStateWithLifecycle()
    val uiStateEvents: ViewTransactionScreenUIStateEvents = screenViewModel.uiStateEvents

    val screenUIEventHandler = remember(
        key1 = uiStateEvents,
    ) {
        ViewTransactionScreenUIEventHandler(
            uiStateEvents = uiStateEvents,
        )
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.initViewModel()
    }

    ViewTransactionScreenUI(
        uiState = uiState,
        handleUIEvent = screenUIEventHandler::handleUIEvent,
    )
}

package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.event.EditTransactionScreenUIEventHandler
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.state.EditTransactionScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.state.EditTransactionScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.viewmodel.EditTransactionScreenViewModel

@Composable
internal fun EditTransactionScreen(
    screenViewModel: EditTransactionScreenViewModel = hiltViewModel(),
) {
    screenViewModel.logKit.logInfo(
        message = "Inside EditTransactionScreen",
    )

    val uiState: EditTransactionScreenUIState by screenViewModel.uiState.collectAsStateWithLifecycle()
    val uiStateEvents: EditTransactionScreenUIStateEvents = screenViewModel.uiStateEvents

    val screenUIEventHandler = remember(
        key1 = uiStateEvents,
    ) {
        EditTransactionScreenUIEventHandler(
            uiStateEvents = uiStateEvents,
        )
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.initViewModel()
    }

    EditTransactionScreenUI(
        uiState = uiState,
        handleUIEvent = screenUIEventHandler::handleUIEvent,
    )
}

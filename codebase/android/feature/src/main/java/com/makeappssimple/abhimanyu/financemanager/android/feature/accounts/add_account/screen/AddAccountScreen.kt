package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.event.AddAccountScreenUIEventHandler
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.state.AddAccountScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.state.AddAccountScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.viewmodel.AddAccountScreenViewModel

@Composable
public fun AddAccountScreen(
    screenViewModel: AddAccountScreenViewModel = hiltViewModel(),
) {
    screenViewModel.logKit.logInfo(
        message = "Inside AddAccountScreen",
    )

    val uiState: AddAccountScreenUIState by screenViewModel.uiState.collectAsStateWithLifecycle()
    val uiStateEvents: AddAccountScreenUIStateEvents = screenViewModel.uiStateEvents

    val screenUIEventHandler = remember(
        key1 = uiStateEvents,
    ) {
        AddAccountScreenUIEventHandler(
            uiStateEvents = uiStateEvents,
        )
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.initViewModel()
    }

    AddAccountScreenUI(
        uiState = uiState,
        handleUIEvent = screenUIEventHandler::handleUIEvent,
    )
}

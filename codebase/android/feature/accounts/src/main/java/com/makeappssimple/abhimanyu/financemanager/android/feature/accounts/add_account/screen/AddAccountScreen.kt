package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LocalMyLogger
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.event.AddAccountScreenUIEventHandler
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.state.AddAccountScreenUIStateAndStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.viewmodel.AddAccountScreenViewModel

@Composable
public fun AddAccountScreen(
    screenViewModel: AddAccountScreenViewModel = hiltViewModel(),
) {
    val myLogger = LocalMyLogger.current
    myLogger.logError(
        message = "Inside AddAccountScreen",
    )

    val uiStateAndStateEvents: AddAccountScreenUIStateAndStateEvents by screenViewModel.uiStateAndStateEvents.collectAsStateWithLifecycle()

    val screenUIEventHandler = remember(
        key1 = uiStateAndStateEvents,
    ) {
        AddAccountScreenUIEventHandler(
            uiStateAndStateEvents = uiStateAndStateEvents,
        )
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.initViewModel()
    }

    AddAccountScreenUI(
        uiState = uiStateAndStateEvents.state,
        handleUIEvent = screenUIEventHandler::handleUIEvent,
    )
}

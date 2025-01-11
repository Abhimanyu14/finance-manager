package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.cre.logger.LocalMyLogger
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.event.EditAccountScreenUIEventHandler
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.state.EditAccountScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.state.EditAccountScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.viewmodel.EditAccountScreenViewModel

@Composable
public fun EditAccountScreen(
    screenViewModel: EditAccountScreenViewModel = hiltViewModel(),
) {
    val myLogger = LocalMyLogger.current
    myLogger.logInfo(
        message = "Inside EditAccountScreen",
    )

    val uiState: EditAccountScreenUIState by screenViewModel.uiState.collectAsStateWithLifecycle()
    val uiStateEvents: EditAccountScreenUIStateEvents = screenViewModel.uiStateEvents

    val screenUIEventHandler = remember(
        key1 = uiStateEvents,
    ) {
        EditAccountScreenUIEventHandler(
            uiStateEvents = uiStateEvents,
        )
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.initViewModel()
    }

    EditAccountScreenUI(
        uiState = uiState,
        handleUIEvent = screenUIEventHandler::handleUIEvent,
    )
}

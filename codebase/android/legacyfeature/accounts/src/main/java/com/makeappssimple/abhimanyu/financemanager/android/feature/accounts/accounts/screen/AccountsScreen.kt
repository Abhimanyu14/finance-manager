package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.cre.logger.LocalMyLogger
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.event.AccountsScreenUIEventHandler
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.state.AccountsScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.state.AccountsScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.viewmodel.AccountsScreenViewModel

@Composable
public fun AccountsScreen(
    screenViewModel: AccountsScreenViewModel = hiltViewModel(),
) {
    val myLogger = LocalMyLogger.current
    myLogger.logInfo(
        message = "Inside AccountsScreen",
    )

    val uiState: AccountsScreenUIState by screenViewModel.uiState.collectAsStateWithLifecycle()
    val uiStateEvents: AccountsScreenUIStateEvents = screenViewModel.uiStateEvents

    val screenUIEventHandler = remember(
        key1 = uiStateEvents,
    ) {
        AccountsScreenUIEventHandler(
            uiStateEvents = uiStateEvents,
        )
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.initViewModel()
    }

    AccountsScreenUI(
        uiState = uiState,
        handleUIEvent = screenUIEventHandler::handleUIEvent,
    )
}

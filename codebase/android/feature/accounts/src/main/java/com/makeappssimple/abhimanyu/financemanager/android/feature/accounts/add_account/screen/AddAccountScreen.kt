package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.screen

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LocalMyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.viewmodel.AddAccountScreenViewModel
import kotlinx.collections.immutable.ImmutableList

@Composable
public fun AddAccountScreen(
    screenViewModel: AddAccountScreenViewModel = hiltViewModel(),
) {
    val myLogger = LocalMyLogger.current
    myLogger.logError(
        message = "Inside AddAccountScreen",
    )

    val focusedView = LocalView.current
    val isKeyboardOpen = WindowInsets.isImeVisible

    // region view model data
    val accounts: ImmutableList<Account> by screenViewModel.accounts.collectAsStateWithLifecycle()
    val validAccountTypes: ImmutableList<AccountType> = screenViewModel.validAccountTypes
    // endregion

    val uiStateAndStateEvents = rememberAddAccountScreenUIStateAndEvents(
        accounts = accounts,
        validAccountTypes = validAccountTypes,
    )
    val screenUIEventHandler = remember(
        key1 = screenViewModel,
        key2 = uiStateAndStateEvents,
    ) {
        AddAccountScreenUIEventHandler(
            viewModel = screenViewModel,
            uiStateAndStateEvents = uiStateAndStateEvents,
        )
    }

    LaunchedEffect(
        key1 = isKeyboardOpen,
    ) {
        myLogger.logError(
            message = "isKeyboardOpen $isKeyboardOpen $focusedView ${focusedView.isFocused}",
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

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
    val viewModel: AddAccountScreenViewModel = remember {
        screenViewModel
    }
    val myLogger = LocalMyLogger.current
    myLogger.logError(
        message = "Inside AddAccountScreen",
    )

    val focusedView = LocalView.current
    val isKeyboardOpen = WindowInsets.isImeVisible

    // region view model data
    val accounts: ImmutableList<Account> by viewModel.accounts.collectAsStateWithLifecycle()
    val validAccountTypes: ImmutableList<AccountType> = viewModel.validAccountTypes
    // endregion

    val uiStateAndEvents = rememberAddAccountScreenUIStateAndEvents(
        accounts = accounts,
        validAccountTypes = validAccountTypes,
    )
    val screenUIEventHandler = remember(
        key1 = viewModel,
        key2 = uiStateAndEvents,
    ) {
        AddAccountScreenUIEventHandler(
            viewModel = viewModel,
            uiStateAndEvents = uiStateAndEvents,
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
        viewModel.initViewModel()
    }

    AddAccountScreenUI(
        uiState = uiStateAndEvents.state,
        handleUIEvent = screenUIEventHandler::handleUIEvent,
    )
}

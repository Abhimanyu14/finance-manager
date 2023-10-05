package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.viewmodel.AccountsScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.viewmodel.AccountsScreenViewModelImpl

@Composable
fun AccountsScreen(
    screenViewModel: AccountsScreenViewModel = hiltViewModel<AccountsScreenViewModelImpl>(),
) {
    val viewModel = remember {
        screenViewModel
    }
    viewModel.myLogger.logError(
        message = "Inside AccountsScreen",
    )

    val screenUIData: MyResult<AccountsScreenUIData>? by viewModel.screenUIData.collectAsStateWithLifecycle()
    val handleUIEvents = remember(
        key1 = viewModel,
    ) {
        { uiEvent: AccountsScreenUIEvent ->
            when (uiEvent) {
                else -> {
                    viewModel.handleUIEvents(
                        uiEvent = uiEvent,
                    )
                }
            }
        }
    }

    AccountsScreenUI(
        uiState = rememberAccountsScreenUIState(
            data = screenUIData,
        ),
        state = rememberCommonScreenUIState(),
        handleUIEvents = handleUIEvents,
    )
}

package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.screen

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LocalMyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_or_edit_account.screen.AddOrEditAccountScreenUI
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_or_edit_account.screen.AddOrEditAccountScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_or_edit_account.screen.AddOrEditAccountScreenUIEvent
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_or_edit_account.screen.rememberAddOrEditAccountScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_or_edit_account.viewmodel.AddOrEditAccountScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_or_edit_account.viewmodel.AddOrEditAccountScreenViewModelImpl

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddAccountScreen(
    screenViewModel: AddOrEditAccountScreenViewModel = hiltViewModel<AddOrEditAccountScreenViewModelImpl>(),
) {
    val viewModel = remember {
        screenViewModel
    }
    val myLogger = LocalMyLogger.current
    myLogger.logError(
        message = "Inside AddAccountScreen",
    )

    val focusRequester = remember { FocusRequester() }
    val focusedView = LocalView.current
    val isKeyboardOpen = WindowInsets.isImeVisible
    LaunchedEffect(
        key1 = isKeyboardOpen,
    ) {
        myLogger.logError(
            message = "isKeyboardOpen $isKeyboardOpen ${focusedView} ${focusedView.isFocused}",
        )
    }

    val screenUIData: MyResult<AddOrEditAccountScreenUIData>? by viewModel.screenUIData.collectAsStateWithLifecycle()
    val handleUIEvents = remember(
        key1 = viewModel,
    ) {
        { uiEvent: AddOrEditAccountScreenUIEvent ->
            when (uiEvent) {
                AddOrEditAccountScreenUIEvent.OnCtaButtonClick -> {
                    viewModel.insertAccount()
                }

                is AddOrEditAccountScreenUIEvent.UpdateBalanceAmountValue -> {

                }

                else -> {
                    viewModel.handleUIEvents(
                        uiEvent = uiEvent,
                    )
                }
            }
        }
    }

    AddOrEditAccountScreenUI(
        uiState = rememberAddOrEditAccountScreenUIState(
            data = screenUIData,
            isEdit = false,
        ),
        state = rememberCommonScreenUIState(),
        handleUIEvents = handleUIEvents,
    )
}

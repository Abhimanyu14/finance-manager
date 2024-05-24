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
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LocalMyLogger
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.viewmodel.AddAccountScreenViewModel

@Composable
public fun AddAccountScreen(
    screenViewModel: AddAccountScreenViewModel = hiltViewModel(),
) {
    val viewModel = remember {
        screenViewModel
    }
    val myLogger = LocalMyLogger.current
    myLogger.logError(
        message = "Inside AddAccountScreen",
    )

    val focusedView = LocalView.current
    val isKeyboardOpen = WindowInsets.isImeVisible

    val screenUIData: MyResult<AddAccountScreenUIData>? by viewModel.screenUIData.collectAsStateWithLifecycle()
    val uiStateAndEvents = rememberAddAccountScreenUIStateAndEvents(
        data = screenUIData,
    )
    val handleUIEvent = remember(
        key1 = viewModel,
        key2 = uiStateAndEvents,
    ) {
        { uiEvent: AddAccountScreenUIEvent ->
            when (uiEvent) {
                is AddAccountScreenUIEvent.OnCtaButtonClick -> {
                    viewModel.insertAccount()
                }

                is AddAccountScreenUIEvent.OnNavigationBackButtonClick -> {
                    uiStateAndEvents.events.resetScreenBottomSheetType()
                }

                is AddAccountScreenUIEvent.OnClearMinimumAccountBalanceAmountValueButtonClick -> {
                    viewModel.clearMinimumAccountBalanceAmountValue()
                }

                is AddAccountScreenUIEvent.OnClearNameButtonClick -> {
                    viewModel.clearName()
                }

                is AddAccountScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                    viewModel.navigateUp()
                }

                is AddAccountScreenUIEvent.OnMinimumAccountBalanceAmountValueUpdated -> {
                    viewModel.updateMinimumAccountBalanceAmountValue(
                        updatedMinimumAccountBalanceAmountValue = uiEvent.updatedMinimumAccountBalanceAmountValue,
                    )
                }

                is AddAccountScreenUIEvent.OnNameUpdated -> {
                    viewModel.updateName(
                        updatedName = uiEvent.updatedName,
                    )
                }

                is AddAccountScreenUIEvent.OnSelectedAccountTypeIndexUpdated -> {
                    viewModel.updateSelectedAccountTypeIndex(
                        updatedIndex = uiEvent.updatedIndex,
                    )
                }

                else -> {
                    // No-op
                }
            }
        }
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
        handleUIEvent = handleUIEvent,
    )
}

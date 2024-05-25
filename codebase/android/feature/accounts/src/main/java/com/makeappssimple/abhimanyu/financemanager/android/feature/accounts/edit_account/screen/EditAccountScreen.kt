package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LocalMyLogger
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.viewmodel.EditAccountScreenViewModel

@Composable
public fun EditAccountScreen(
    screenViewModel: EditAccountScreenViewModel = hiltViewModel(),
) {
    val viewModel = remember {
        screenViewModel
    }
    val myLogger = LocalMyLogger.current
    myLogger.logError(
        message = "Inside EditAccountScreen",
    )

    val screenUIData: MyResult<EditAccountScreenUIData>? by viewModel.screenUIData.collectAsStateWithLifecycle()
    val uiState = rememberEditAccountScreenUIStateAndEvents(
        data = screenUIData,
    )
    val handleUIEvent = remember(
        key1 = viewModel,
        key2 = uiState,
    ) {
        { uiEvent: EditAccountScreenUIEvent ->
            when (uiEvent) {
                is EditAccountScreenUIEvent.OnCtaButtonClick -> {
                    viewModel.updateAccount()
                }

                is EditAccountScreenUIEvent.OnBalanceAmountValueUpdated -> {
                    viewModel.updateBalanceAmountValue(
                        updatedBalanceAmountValue = uiEvent.updatedBalanceAmountValue,
                    )
                }

                is EditAccountScreenUIEvent.OnNavigationBackButtonClick -> {
                    uiState.events.resetScreenBottomSheetType()
                }

                is EditAccountScreenUIEvent.OnClearBalanceAmountValueButtonClick -> {
                    viewModel.clearBalanceAmountValue()
                }

                is EditAccountScreenUIEvent.OnClearMinimumAccountBalanceAmountValueButtonClick -> {
                    viewModel.clearMinimumAccountBalanceAmountValue()
                }

                is EditAccountScreenUIEvent.OnClearNameButtonClick -> {
                    viewModel.clearName()
                }

                is EditAccountScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                    viewModel.navigateUp()
                }

                is EditAccountScreenUIEvent.OnMinimumAccountBalanceAmountValueUpdated -> {
                    viewModel.updateMinimumAccountBalanceAmountValue(
                        updatedMinimumAccountBalanceAmountValue = uiEvent.updatedMinimumAccountBalanceAmountValue,
                    )
                }

                is EditAccountScreenUIEvent.OnNameUpdated -> {
                    viewModel.updateName(
                        updatedName = uiEvent.updatedName,
                    )
                }

                is EditAccountScreenUIEvent.OnSelectedAccountTypeIndexUpdated -> {
                    viewModel.updateSelectedAccountTypeIndex(
                        updatedIndex = uiEvent.updatedIndex,
                    )
                }
            }
        }
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        viewModel.initViewModel()
    }

    EditAccountScreenUI(
        uiState = uiState.state,
        handleUIEvent = handleUIEvent,
    )
}

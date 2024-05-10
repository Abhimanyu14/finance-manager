package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LocalMyLogger
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_or_edit_account.screen.AddOrEditAccountScreenUI
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_or_edit_account.screen.AddOrEditAccountScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_or_edit_account.screen.AddOrEditAccountScreenUIEvent
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_or_edit_account.screen.rememberAddOrEditAccountScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_or_edit_account.viewmodel.AddOrEditAccountScreenViewModel

@Composable
public fun EditAccountScreen(
    screenViewModel: AddOrEditAccountScreenViewModel = hiltViewModel(),
) {
    val viewModel = remember {
        screenViewModel
    }
    val myLogger = LocalMyLogger.current
    myLogger.logError(
        message = "Inside EditAccountScreen",
    )

    val screenUIData: MyResult<AddOrEditAccountScreenUIData>? by viewModel.screenUIData.collectAsStateWithLifecycle()
    val uiState = rememberAddOrEditAccountScreenUIState(
        data = screenUIData,
        isEdit = true,
    )
    val handleUIEvent = remember(
        key1 = viewModel,
        key2 = uiState,
    ) {
        { uiEvent: AddOrEditAccountScreenUIEvent ->
            when (uiEvent) {
                is AddOrEditAccountScreenUIEvent.OnCtaButtonClick -> {
                    viewModel.updateAccount()
                }

                is AddOrEditAccountScreenUIEvent.OnBalanceAmountValueUpdated -> {
                    viewModel.updateBalanceAmountValue(
                        updatedBalanceAmountValue = uiEvent.updatedBalanceAmountValue,
                    )
                }

                is AddOrEditAccountScreenUIEvent.OnNavigationBackButtonClick -> {
                    uiState.resetScreenBottomSheetType()
                }

                is AddOrEditAccountScreenUIEvent.OnClearBalanceAmountValueButtonClick -> {
                    viewModel.clearBalanceAmountValue()
                }

                is AddOrEditAccountScreenUIEvent.OnClearMinimumAccountBalanceAmountValueButtonClick -> {
                    viewModel.clearMinimumAccountBalanceAmountValue()
                }

                is AddOrEditAccountScreenUIEvent.OnClearNameButtonClick -> {
                    viewModel.clearName()
                }

                is AddOrEditAccountScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                    viewModel.navigateUp()
                }

                is AddOrEditAccountScreenUIEvent.OnMinimumAccountBalanceAmountValueUpdated -> {
                    viewModel.updateMinimumAccountBalanceAmountValue(
                        updatedMinimumAccountBalanceAmountValue = uiEvent.updatedMinimumAccountBalanceAmountValue,
                    )
                }

                is AddOrEditAccountScreenUIEvent.OnNameUpdated -> {
                    viewModel.updateName(
                        updatedName = uiEvent.updatedName,
                    )
                }

                is AddOrEditAccountScreenUIEvent.OnSelectedAccountTypeIndexUpdated -> {
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

    AddOrEditAccountScreenUI(
        uiState = uiState,
        handleUIEvent = handleUIEvent,
    )
}

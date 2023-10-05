package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_or_edit_account.screen.AddOrEditAccountScreenUI
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_or_edit_account.screen.AddOrEditAccountScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_or_edit_account.screen.AddOrEditAccountScreenUIEvent
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_or_edit_account.screen.rememberAddOrEditAccountScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_or_edit_account.viewmodel.AddOrEditAccountScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_or_edit_account.viewmodel.AddOrEditAccountScreenViewModelImpl

@Composable
fun EditAccountScreen(
    screenViewModel: AddOrEditAccountScreenViewModel = hiltViewModel<AddOrEditAccountScreenViewModelImpl>(),
) {
    val viewModel = remember {
        screenViewModel
    }
    viewModel.myLogger.logError(
        message = "Inside EditAccountScreen",
    )

    val screenUIData: MyResult<AddOrEditAccountScreenUIData>? by viewModel.screenUIData.collectAsStateWithLifecycle()
    val handleUIEvents = remember(
        key1 = viewModel,
    ) {
        { uiEvent: AddOrEditAccountScreenUIEvent ->
            when (uiEvent) {
                AddOrEditAccountScreenUIEvent.OnCtaButtonClick -> {
                    viewModel.updateAccount()
                }

                is AddOrEditAccountScreenUIEvent.UpdateBalanceAmountValue -> {
                    viewModel.updateBalanceAmountValue(
                        updatedBalanceAmountValue = uiEvent.updatedBalanceAmountValue,
                    )
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
//        events = AddOrEditAccountScreenUIEvent(
//            updateBalanceAmountValue = viewModel::updateBalanceAmountValue,
//            updateMinimumAccountBalanceAmountValue = viewModel::updateMinimumAccountBalanceAmountValue,
//            updateName = viewModel::updateName,
//            updateSelectedAccountTypeIndex = viewModel::updateSelectedAccountTypeIndex,
//        ),
        uiState = rememberAddOrEditAccountScreenUIState(
            data = screenUIData,
            isEdit = true,
        ),
        state = rememberCommonScreenUIState(),
        handleUIEvents = handleUIEvents,
    )
}

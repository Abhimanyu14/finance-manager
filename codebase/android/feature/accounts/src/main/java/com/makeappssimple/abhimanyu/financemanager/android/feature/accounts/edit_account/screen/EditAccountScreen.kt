package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_or_edit_account.screen.AddOrEditAccountScreenUI
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_or_edit_account.screen.AddOrEditAccountScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_or_edit_account.screen.AddOrEditAccountScreenUIEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_or_edit_account.screen.rememberAddOrEditAccountScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_or_edit_account.viewmodel.AddOrEditAccountScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_or_edit_account.viewmodel.AddOrEditAccountScreenViewModelImpl

@Composable
fun EditAccountScreen(
    screenViewModel: AddOrEditAccountScreenViewModel = hiltViewModel<AddOrEditAccountScreenViewModelImpl>(),
) {
    screenViewModel.myLogger.logError(
        message = "Inside EditAccountScreen",
    )

    val screenUIData: MyResult<AddOrEditAccountScreenUIData>? by screenViewModel.screenUIData.collectAsStateWithLifecycle()

    AddOrEditAccountScreenUI(
        events = AddOrEditAccountScreenUIEvents(
            clearBalanceAmountValue = screenViewModel::clearBalanceAmountValue,
            clearMinimumAccountBalanceAmountValue = screenViewModel::clearMinimumAccountBalanceAmountValue,
            clearName = screenViewModel::clearName,
            navigateUp = screenViewModel::navigateUp,
            onCtaButtonClick = screenViewModel::updateAccount,
            updateBalanceAmountValue = screenViewModel::updateBalanceAmountValue,
            updateMinimumAccountBalanceAmountValue = screenViewModel::updateMinimumAccountBalanceAmountValue,
            updateName = screenViewModel::updateName,
            updateSelectedAccountTypeIndex = screenViewModel::updateSelectedAccountTypeIndex,
        ),
        uiState = rememberAddOrEditAccountScreenUIState(
            data = screenUIData,
            isEdit = true,
        ),
        state = rememberCommonScreenUIState(),
    )
}

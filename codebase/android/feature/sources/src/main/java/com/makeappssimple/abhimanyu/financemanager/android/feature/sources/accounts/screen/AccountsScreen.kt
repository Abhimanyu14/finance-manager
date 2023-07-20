package com.makeappssimple.abhimanyu.financemanager.android.feature.sources.accounts.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.accounts.viewmodel.AccountsScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.accounts.viewmodel.AccountsScreenViewModelImpl

@Composable
fun AccountsScreen(
    screenViewModel: AccountsScreenViewModel = hiltViewModel<AccountsScreenViewModelImpl>(),
) {
    screenViewModel.myLogger.logError(
        message = "Inside AccountsScreen",
    )

    val screenUIData: MyResult<AccountsScreenUIData>? by screenViewModel.screenUIData.collectAsStateWithLifecycle()

    AccountsScreenUI(
        events = AccountsScreenUIEvents(
            deleteAccount = screenViewModel::deleteAccount,
            navigateToAddAccountScreen = screenViewModel::navigateToAddAccountScreen,
            navigateToEditAccountScreen = screenViewModel::navigateToEditAccountScreen,
            navigateUp = screenViewModel::navigateUp,
            setDefaultAccountIdInDataStore = screenViewModel::setDefaultAccountIdInDataStore,
        ),
        uiState = rememberAccountsScreenUIState(
            data = screenUIData,
        ),
        state = rememberCommonScreenUIState(),
    )
}

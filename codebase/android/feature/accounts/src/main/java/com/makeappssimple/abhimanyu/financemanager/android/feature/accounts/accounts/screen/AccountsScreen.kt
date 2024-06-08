package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LocalMyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.viewmodel.AccountsScreenViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap

@Composable
public fun AccountsScreen(
    screenViewModel: AccountsScreenViewModel = hiltViewModel(),
) {
    val viewModel = remember {
        screenViewModel
    }
    val myLogger = LocalMyLogger.current
    myLogger.logError(
        message = "Inside AccountsScreen",
    )

    // region view model data
    val defaultAccountId: Int? by viewModel.defaultAccountId.collectAsStateWithLifecycle()
    val allAccounts: ImmutableList<Account> by viewModel.allAccounts.collectAsStateWithLifecycle()
    val isAccountUsedInTransactions: ImmutableMap<Int, Boolean> by viewModel.isAccountUsedInTransactions.collectAsStateWithLifecycle()
    val accountsTotalBalanceAmountValue: Long by viewModel.accountsTotalBalanceAmountValue.collectAsStateWithLifecycle()
    val accountsTotalMinimumBalanceAmountValue: Long by viewModel.accountsTotalMinimumBalanceAmountValue.collectAsStateWithLifecycle()
    // endregion

    val uiStateAndEvents: AccountsScreenUIStateAndEvents = rememberAccountsScreenUIStateAndEvents(
        defaultAccountId = defaultAccountId,
        allAccounts = allAccounts,
        isAccountUsedInTransactions = isAccountUsedInTransactions,
        accountsTotalBalanceAmountValue = accountsTotalBalanceAmountValue,
        accountsTotalMinimumBalanceAmountValue = accountsTotalMinimumBalanceAmountValue,
    )
    val screenUIEventHandler = remember(
        key1 = viewModel,
        key2 = uiStateAndEvents,
    ) {
        AccountsScreenUIEventHandler(
            viewModel = viewModel,
            uiStateAndEvents = uiStateAndEvents,
        )
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        viewModel.initViewModel()
    }

    AccountsScreenUI(
        uiState = uiStateAndEvents.state,
        handleUIEvent = screenUIEventHandler::handleUIEvent,
    )
}

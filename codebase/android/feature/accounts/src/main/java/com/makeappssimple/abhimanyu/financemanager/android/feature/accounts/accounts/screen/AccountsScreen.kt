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
    val allAccounts: List<Account> by viewModel.allAccounts.collectAsStateWithLifecycle()
    val isAccountUsedInTransactions: Map<Int, Boolean> by viewModel.isAccountUsedInTransactions.collectAsStateWithLifecycle(
        initialValue = emptyMap(),
    )
    val accountsTotalBalanceAmountValue: Long by viewModel.accountsTotalBalanceAmountValue.collectAsStateWithLifecycle()
    val accountsTotalMinimumBalanceAmountValue: Long by viewModel.accountsTotalMinimumBalanceAmountValue.collectAsStateWithLifecycle()
    // endregion

    val uiStateAndEvents = rememberAccountsScreenUIStateAndEvents(
        defaultAccountId = defaultAccountId,
        allAccounts = allAccounts,
        isAccountUsedInTransactions = isAccountUsedInTransactions,
        accountsTotalBalanceAmountValue = accountsTotalBalanceAmountValue,
        accountsTotalMinimumBalanceAmountValue = accountsTotalMinimumBalanceAmountValue,
    )
    val handleUIEvent = remember(
        key1 = viewModel,
        key2 = uiStateAndEvents,
    ) {
        { uiEvent: AccountsScreenUIEvent ->
            when (uiEvent) {
                is AccountsScreenUIEvent.OnAccountsDeleteConfirmationBottomSheet.NegativeButtonClick -> {
                    uiStateAndEvents.events.resetScreenBottomSheetType()
                    uiStateAndEvents.events.setClickedItemId(null)
                }

                is AccountsScreenUIEvent.OnAccountsDeleteConfirmationBottomSheet.PositiveButtonClick -> {
                    uiStateAndEvents.state.clickedItemId?.let { accountId ->
                        viewModel.deleteAccount(
                            accountId = accountId,
                        )
                        uiStateAndEvents.events.setClickedItemId(null)
                    }
                    uiStateAndEvents.events.resetScreenBottomSheetType()
                }

                is AccountsScreenUIEvent.OnAccountsMenuBottomSheet.DeleteButtonClick -> {
                    uiStateAndEvents.events.setScreenBottomSheetType(AccountsScreenBottomSheetType.DeleteConfirmation)
                }

                is AccountsScreenUIEvent.OnAccountsMenuBottomSheet.EditButtonClick -> {
                    uiStateAndEvents.events.resetScreenBottomSheetType()
                    viewModel.navigateToEditAccountScreen(
                        accountId = uiEvent.accountId,
                    )
                }

                is AccountsScreenUIEvent.OnAccountsMenuBottomSheet.SetAsDefaultButtonClick -> {
                    uiStateAndEvents.events.setScreenBottomSheetType(AccountsScreenBottomSheetType.SetAsDefaultConfirmation)
                }

                is AccountsScreenUIEvent.OnAccountsSetAsDefaultConfirmationBottomSheet.NegativeButtonClick -> {
                    uiStateAndEvents.events.resetScreenBottomSheetType()
                    uiStateAndEvents.events.setClickedItemId(null)
                }

                is AccountsScreenUIEvent.OnAccountsSetAsDefaultConfirmationBottomSheet.PositiveButtonClick -> {
                    uiStateAndEvents.state.clickedItemId?.let { accountId ->
                        viewModel.setDefaultAccountIdInDataStore(
                            accountId = accountId,
                        )
                        uiStateAndEvents.events.setClickedItemId(null)
                    }
                    uiStateAndEvents.events.resetScreenBottomSheetType()
                }

                is AccountsScreenUIEvent.OnAccountsListItemContent.Click -> {
                    uiEvent.accountId?.let { accountId ->
                        uiStateAndEvents.events.setScreenBottomSheetType(
                            AccountsScreenBottomSheetType.Menu(
                                isDeleteVisible = uiEvent.isDeleteEnabled,
                                isEditVisible = true,
                                isSetAsDefaultVisible = !uiEvent.isDefault,
                                accountId = accountId,
                            )
                        )
                    }
                    uiStateAndEvents.events.setClickedItemId(uiEvent.accountId)
                }

                is AccountsScreenUIEvent.OnBottomSheetDismissed -> {
                    uiStateAndEvents.events.resetScreenBottomSheetType()
                    uiStateAndEvents.events.setClickedItemId(null)
                }

                is AccountsScreenUIEvent.OnFloatingActionButtonClick -> {
                    viewModel.navigateToAddAccountScreen()
                }

                is AccountsScreenUIEvent.OnNavigationBackButtonClick -> {
                    uiStateAndEvents.events.resetScreenBottomSheetType()
                }

                is AccountsScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                    viewModel.navigateUp()
                }
            }
        }
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        viewModel.initViewModel()
    }

    AccountsScreenUI(
        uiState = uiStateAndEvents.state,
        handleUIEvent = handleUIEvent,
    )
}

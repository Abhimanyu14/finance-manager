package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LocalMyLogger
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

    val screenUIData: MyResult<AccountsScreenUIData>? by viewModel.screenUIData.collectAsStateWithLifecycle()
    val uiStateAndEvents = rememberAccountsScreenUIStateAndEvents(
        data = screenUIData,
    )
    val handleUIEvent = remember(
        key1 = viewModel,
        key2 = uiStateAndEvents,
    ) {
        { uiEvent: AccountsScreenUIEvent ->
            when (uiEvent) {
                is AccountsScreenUIEvent.OnBottomSheetDismissed -> {
                    uiStateAndEvents.events.resetScreenBottomSheetType()
                }

                is AccountsScreenUIEvent.OnNavigationBackButtonClick -> {
                    uiStateAndEvents.events.resetScreenBottomSheetType()
                }

                is AccountsScreenUIEvent.OnAccountsMenuBottomSheet.DeleteButtonClick -> {
                    uiStateAndEvents.events.setAccountIdToDelete(uiEvent.accountId)
                    uiStateAndEvents.events.setScreenBottomSheetType(AccountsScreenBottomSheetType.DeleteConfirmation)
                }

                is AccountsScreenUIEvent.OnAccountsMenuBottomSheet.EditButtonClick -> {
                    uiStateAndEvents.events.resetScreenBottomSheetType()
                    viewModel.navigateToEditAccountScreen(
                        accountId = uiEvent.accountId,
                    )
                }

                is AccountsScreenUIEvent.OnAccountsMenuBottomSheet.SetAsDefaultButtonClick -> {
                    uiStateAndEvents.events.setClickedItemId(uiEvent.accountId)
                    uiStateAndEvents.events.setScreenBottomSheetType(AccountsScreenBottomSheetType.SetAsDefaultConfirmation)
                }

                is AccountsScreenUIEvent.OnAccountsDeleteConfirmationBottomSheet.NegativeButtonClick -> {
                    uiStateAndEvents.events.resetScreenBottomSheetType()
                    uiStateAndEvents.events.setAccountIdToDelete(null)
                }

                is AccountsScreenUIEvent.OnAccountsDeleteConfirmationBottomSheet.PositiveButtonClick -> {
                    uiStateAndEvents.state.accountIdToDelete?.let { accountId ->
                        viewModel.deleteAccount(
                            accountId = accountId,
                        )
                        uiStateAndEvents.events.setAccountIdToDelete(null)
                    }
                    uiStateAndEvents.events.resetScreenBottomSheetType()
                }

                is AccountsScreenUIEvent.OnAccountsSetAsDefaultConfirmationBottomSheet.NegativeButtonClick -> {
                    uiStateAndEvents.events.resetScreenBottomSheetType()
                    uiStateAndEvents.events.setClickedItemId(null)
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

                is AccountsScreenUIEvent.OnAccountsSetAsDefaultConfirmationBottomSheet.PositiveButtonClick -> {
                    uiStateAndEvents.state.clickedItemId?.let { clickedItemIdValue ->
                        viewModel.setDefaultAccountIdInDataStore(
                            defaultAccountId = clickedItemIdValue,
                        )
                        uiStateAndEvents.events.setClickedItemId(null)
                    }
                    uiStateAndEvents.events.resetScreenBottomSheetType()
                }

                is AccountsScreenUIEvent.OnFloatingActionButtonClick -> {
                    viewModel.navigateToAddAccountScreen()
                }

                is AccountsScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                    viewModel.navigateUp()
                }
            }
        }
    }

    AccountsScreenUI(
        uiState = uiStateAndEvents.state,
        handleUIEvent = handleUIEvent,
    )
}

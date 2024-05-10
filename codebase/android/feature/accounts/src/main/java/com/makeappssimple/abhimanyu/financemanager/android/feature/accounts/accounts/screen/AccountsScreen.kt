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
    val uiState = rememberAccountsScreenUIState(
        data = screenUIData,
    )
    val handleUIEvent = remember(
        key1 = viewModel,
        key2 = uiState,
    ) {
        { uiEvent: AccountsScreenUIEvent ->
            when (uiEvent) {
                is AccountsScreenUIEvent.OnAccountsMenuBottomSheet.DeleteButtonClick -> {
                    uiState.setAccountIdToDelete(uiEvent.accountId)
                    uiState.setScreenBottomSheetType(AccountsScreenBottomSheetType.DeleteConfirmation)
                }

                is AccountsScreenUIEvent.OnAccountsMenuBottomSheet.EditButtonClick -> {
                    uiState.resetScreenBottomSheetType()
                    viewModel.navigateToEditAccountScreen(
                        accountId = uiEvent.accountId,
                    )
                }

                is AccountsScreenUIEvent.OnAccountsDeleteConfirmationBottomSheet.NegativeButtonClick -> {
                    uiState.resetScreenBottomSheetType()
                    uiState.setAccountIdToDelete(null)
                }

                is AccountsScreenUIEvent.OnAccountsDeleteConfirmationBottomSheet.PositiveButtonClick -> {
                    uiState.accountIdToDelete?.let { accountId ->
                        viewModel.deleteAccount(
                            accountId = accountId,
                        )
                        uiState.setAccountIdToDelete(null)
                    }
                    uiState.resetScreenBottomSheetType()
                }

                is AccountsScreenUIEvent.OnAccountsSetAsDefaultConfirmationBottomSheet.NegativeButtonClick -> {
                    uiState.resetScreenBottomSheetType()
                    uiState.setClickedItemId(null)
                }

                is AccountsScreenUIEvent.OnAccountsSetAsDefaultConfirmationBottomSheet.PositiveButtonClick -> {
                    uiState.clickedItemId?.let { clickedItemIdValue ->
                        viewModel.setDefaultAccountIdInDataStore(
                            defaultAccountId = clickedItemIdValue,
                        )
                        uiState.setClickedItemId(null)
                    }
                    uiState.resetScreenBottomSheetType()
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
        uiState = uiState,
        handleUIEvent = handleUIEvent,
    )
}

package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.screen

import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.viewmodel.AccountsScreenViewModel

public class AccountsScreenUIEventHandler internal constructor(
    private val viewModel: AccountsScreenViewModel,
    private val uiStateAndEvents: AccountsScreenUIStateAndEvents,
) {
    public fun handleUIEvent(
        uiEvent: AccountsScreenUIEvent,
    ) {
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

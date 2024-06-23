package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.event

import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.bottomsheet.AccountsScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.state.AccountsScreenUIStateAndStateEvents

internal class AccountsScreenUIEventHandler internal constructor(
    private val uiStateAndStateEvents: AccountsScreenUIStateAndStateEvents,
) {
    fun handleUIEvent(
        uiEvent: AccountsScreenUIEvent,
    ) {
        when (uiEvent) {
            is AccountsScreenUIEvent.OnAccountsDeleteConfirmationBottomSheet.NegativeButtonClick -> {
                uiStateAndStateEvents.events.resetScreenBottomSheetType()
                uiStateAndStateEvents.events.setClickedItemId(null)
            }

            is AccountsScreenUIEvent.OnAccountsDeleteConfirmationBottomSheet.PositiveButtonClick -> {
                uiStateAndStateEvents.state.clickedItemId?.let { accountId ->
                    uiStateAndStateEvents.events.deleteAccount(accountId)
                    uiStateAndStateEvents.events.setClickedItemId(null)
                }
                uiStateAndStateEvents.events.resetScreenBottomSheetType()
            }

            is AccountsScreenUIEvent.OnAccountsMenuBottomSheet.DeleteButtonClick -> {
                uiStateAndStateEvents.events.setScreenBottomSheetType(AccountsScreenBottomSheetType.DeleteConfirmation)
            }

            is AccountsScreenUIEvent.OnAccountsMenuBottomSheet.EditButtonClick -> {
                uiStateAndStateEvents.events.resetScreenBottomSheetType()
                uiStateAndStateEvents.events.navigateToEditAccountScreen(uiEvent.accountId)
            }

            is AccountsScreenUIEvent.OnAccountsMenuBottomSheet.SetAsDefaultButtonClick -> {
                uiStateAndStateEvents.events.setScreenBottomSheetType(AccountsScreenBottomSheetType.SetAsDefaultConfirmation)
            }

            is AccountsScreenUIEvent.OnAccountsSetAsDefaultConfirmationBottomSheet.NegativeButtonClick -> {
                uiStateAndStateEvents.events.resetScreenBottomSheetType()
                uiStateAndStateEvents.events.setClickedItemId(null)
            }

            is AccountsScreenUIEvent.OnAccountsSetAsDefaultConfirmationBottomSheet.PositiveButtonClick -> {
                uiStateAndStateEvents.state.clickedItemId?.let { accountId ->
                    uiStateAndStateEvents.events.setDefaultAccountIdInDataStore(accountId)
                    uiStateAndStateEvents.events.setClickedItemId(null)
                }
                uiStateAndStateEvents.events.resetScreenBottomSheetType()
            }

            is AccountsScreenUIEvent.OnAccountsListItemContent.Click -> {
                uiEvent.accountId?.let { accountId ->
                    uiStateAndStateEvents.events.setScreenBottomSheetType(
                        AccountsScreenBottomSheetType.Menu(
                            isDeleteVisible = uiEvent.isDeleteEnabled,
                            isEditVisible = true,
                            isSetAsDefaultVisible = !uiEvent.isDefault,
                            accountId = accountId,
                        )
                    )
                }
                uiStateAndStateEvents.events.setClickedItemId(uiEvent.accountId)
            }

            is AccountsScreenUIEvent.OnFloatingActionButtonClick -> {
                uiStateAndStateEvents.events.navigateToAddAccountScreen()
            }

            is AccountsScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateAndStateEvents.events.resetScreenBottomSheetType()
            }

            is AccountsScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                uiStateAndStateEvents.events.navigateUp()
            }
        }
    }
}

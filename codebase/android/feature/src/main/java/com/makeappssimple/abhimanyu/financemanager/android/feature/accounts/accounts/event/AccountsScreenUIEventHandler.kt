package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.event

import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEventHandler
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.bottomsheet.AccountsScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.state.AccountsScreenUIStateEvents

internal class AccountsScreenUIEventHandler internal constructor(
    private val uiStateEvents: AccountsScreenUIStateEvents,
) : ScreenUIEventHandler<AccountsScreenUIEvent> {
    override fun handleUIEvent(
        uiEvent: AccountsScreenUIEvent,
    ) {
        when (uiEvent) {
            is AccountsScreenUIEvent.OnAccountsDeleteConfirmationBottomSheet.NegativeButtonClick -> {
                uiStateEvents.resetScreenBottomSheetType()
                uiStateEvents.setClickedItemId(null)
            }

            is AccountsScreenUIEvent.OnAccountsDeleteConfirmationBottomSheet.PositiveButtonClick -> {
                uiStateEvents.deleteAccount()
                uiStateEvents.setClickedItemId(null)
                uiStateEvents.resetScreenBottomSheetType()
            }

            is AccountsScreenUIEvent.OnAccountsMenuBottomSheet.DeleteButtonClick -> {
                uiStateEvents.setScreenBottomSheetType(AccountsScreenBottomSheetType.DeleteConfirmation)
            }

            is AccountsScreenUIEvent.OnAccountsMenuBottomSheet.EditButtonClick -> {
                uiStateEvents.resetScreenBottomSheetType()
                uiStateEvents.navigateToEditAccountScreen(uiEvent.accountId)
            }

            is AccountsScreenUIEvent.OnAccountsMenuBottomSheet.SetAsDefaultButtonClick -> {
                uiStateEvents.setScreenBottomSheetType(AccountsScreenBottomSheetType.SetAsDefaultConfirmation)
            }

            is AccountsScreenUIEvent.OnAccountsSetAsDefaultConfirmationBottomSheet.NegativeButtonClick -> {
                uiStateEvents.resetScreenBottomSheetType()
                uiStateEvents.setClickedItemId(null)
            }

            is AccountsScreenUIEvent.OnAccountsSetAsDefaultConfirmationBottomSheet.PositiveButtonClick -> {
                uiStateEvents.setDefaultAccountIdInDataStore()
                uiStateEvents.setClickedItemId(null)
                uiStateEvents.resetScreenBottomSheetType()
            }

            is AccountsScreenUIEvent.OnAccountsListItemContent.Click -> {
                uiEvent.accountId?.let { accountId ->
                    uiStateEvents.setScreenBottomSheetType(
                        AccountsScreenBottomSheetType.Menu(
                            isDeleteVisible = uiEvent.isDeleteEnabled,
                            isEditVisible = true,
                            isSetAsDefaultVisible = !uiEvent.isDefault,
                            accountId = accountId,
                        )
                    )
                }
                uiStateEvents.setClickedItemId(uiEvent.accountId)
            }

            is AccountsScreenUIEvent.OnFloatingActionButtonClick -> {
                uiStateEvents.navigateToAddAccountScreen()
            }

            is AccountsScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateEvents.resetScreenBottomSheetType()
            }

            is AccountsScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                uiStateEvents.navigateUp()
            }
        }
    }
}

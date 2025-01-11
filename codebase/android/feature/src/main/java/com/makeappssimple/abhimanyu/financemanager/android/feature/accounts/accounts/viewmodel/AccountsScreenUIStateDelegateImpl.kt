package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.common.state.common.ScreenUICommonState
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.DeleteAccountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.bottomsheet.AccountsScreenBottomSheetType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal class AccountsScreenUIStateDelegateImpl(
    private val coroutineScope: CoroutineScope,
    private val deleteAccountUseCase: DeleteAccountUseCase,
    private val myPreferencesRepository: MyPreferencesRepository,
    private val navigationKit: NavigationKit,
    private val screenUICommonState: ScreenUICommonState,
) : AccountsScreenUIStateDelegate, ScreenUICommonState by screenUICommonState {
    // region UI state
    override var screenBottomSheetType: AccountsScreenBottomSheetType =
        AccountsScreenBottomSheetType.None
    override var clickedItemId: Int? = null
    // endregion

    // region state events
    override fun deleteAccount() {
        coroutineScope.launch {
            clickedItemId?.let { id ->
                deleteAccountUseCase(
                    id = id,
                )
            }
        }
    }

    override fun navigateToAddAccountScreen() {
        navigationKit.navigateToAddAccountScreen()
    }

    override fun navigateToEditAccountScreen(
        accountId: Int,
    ) {
        navigationKit.navigateToEditAccountScreen(
            accountId = accountId,
        )
    }

    override fun navigateUp() {
        navigationKit.navigateUp()
    }

    override fun resetScreenBottomSheetType() {
        updateScreenBottomSheetType(
            updatedAccountsScreenBottomSheetType = AccountsScreenBottomSheetType.None,
        )
    }

    override fun updateClickedItemId(
        updatedClickedItemId: Int?,
        shouldRefresh: Boolean,
    ) {
        clickedItemId = updatedClickedItemId
        if (shouldRefresh) {
            refresh()
        }
    }

    override fun setDefaultAccountIdInDataStore() {
        coroutineScope.launch {
            clickedItemId?.let { accountId ->
                val isDefaultAccountUpdated = myPreferencesRepository.setDefaultAccountId(
                    accountId = accountId,
                )
                if (!isDefaultAccountUpdated) {
                    // TODO(Abhi): Use the result to show snackbar
                }
            }
        }
    }

    override fun updateScreenBottomSheetType(
        updatedAccountsScreenBottomSheetType: AccountsScreenBottomSheetType,
        shouldRefresh: Boolean,
    ) {
        screenBottomSheetType = updatedAccountsScreenBottomSheetType
        if (shouldRefresh) {
            refresh()
        }
    }
    // endregion
}

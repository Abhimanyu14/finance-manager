package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.DeleteAccountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.bottomsheet.AccountsScreenBottomSheetType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

internal class AccountsScreenUIStateDelegateImpl(
    private val coroutineScope: CoroutineScope,
    private val deleteAccountUseCase: DeleteAccountUseCase,
    private val myPreferencesRepository: MyPreferencesRepository,
    private val navigator: Navigator,
) : AccountsScreenUIStateDelegate {
    // region UI state
    override val refreshSignal: MutableSharedFlow<Unit> = MutableSharedFlow(
        replay = 0,
        extraBufferCapacity = 1,
    )
    override var isLoading: Boolean = true
    override var screenBottomSheetType: AccountsScreenBottomSheetType =
        AccountsScreenBottomSheetType.None
    override var clickedItemId: Int? = null
    // endregion

    // region refresh
    override fun refresh() {
        refreshSignal.tryEmit(Unit)
    }
    // endregion

    // region state events
    override fun completeLoading(
        refresh: Boolean,
    ) {
        isLoading = false
        if (refresh) {
            refresh()
        }
    }

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
        navigator.navigateToAddAccountScreen()
    }

    override fun navigateToEditAccountScreen(
        accountId: Int,
    ) {
        navigator.navigateToEditAccountScreen(
            accountId = accountId,
        )
    }

    override fun navigateUp() {
        navigator.navigateUp()
    }

    override fun resetScreenBottomSheetType() {
        updateScreenBottomSheetType(
            updatedAccountsScreenBottomSheetType = AccountsScreenBottomSheetType.None,
        )
    }

    override fun startLoading(
        refresh: Boolean,
    ) {
        isLoading = true
        if (refresh) {
            refresh()
        }
    }

    override fun updateClickedItemId(
        updatedClickedItemId: Int?,
        refresh: Boolean,
    ) {
        clickedItemId = updatedClickedItemId
        if (refresh) {
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
        refresh: Boolean,
    ) {
        screenBottomSheetType = updatedAccountsScreenBottomSheetType
        if (refresh) {
            refresh()
        }
    }
    // endregion
}

package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.DeleteAccountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.bottomsheet.AccountsScreenBottomSheetType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class AccountsScreenUIStateDelegateImpl(
    private val deleteAccountUseCase: DeleteAccountUseCase,
    private val myPreferencesRepository: MyPreferencesRepository,
    private val navigator: Navigator,
) : AccountsScreenUIStateDelegate {
    // region UI state
    override val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(
        value = true,
    )
    override val screenBottomSheetType: MutableStateFlow<AccountsScreenBottomSheetType> =
        MutableStateFlow(
            value = AccountsScreenBottomSheetType.None,
        )
    override val clickedItemId: MutableStateFlow<Int?> = MutableStateFlow(
        value = null,
    )
    // endregion

    // region loading
    override fun startLoading() {
        isLoading.update {
            true
        }
    }

    override fun completeLoading() {
        isLoading.update {
            false
        }
    }
    // endregion

    // region state events
    override fun deleteAccount(
        coroutineScope: CoroutineScope,
        accountId: Int,
    ) {
        coroutineScope.launch {
            deleteAccountUseCase(
                id = accountId,
            )
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
        setScreenBottomSheetType(
            updatedAccountsScreenBottomSheetType = AccountsScreenBottomSheetType.None,
        )
    }

    override fun setClickedItemId(
        updatedClickedItemId: Int?,
    ) {
        clickedItemId.update {
            updatedClickedItemId
        }
    }

    override fun setDefaultAccountIdInDataStore(
        coroutineScope: CoroutineScope,
        accountId: Int,
    ) {
        coroutineScope.launch {
            val isDefaultAccountUpdated = myPreferencesRepository.setDefaultAccountId(
                accountId = accountId,
            )
            if (!isDefaultAccountUpdated) {
                // TODO(Abhi): Use the result to show snackbar
            }
        }
    }

    override fun setScreenBottomSheetType(
        updatedAccountsScreenBottomSheetType: AccountsScreenBottomSheetType,
    ) {
        screenBottomSheetType.update {
            updatedAccountsScreenBottomSheetType
        }
    }
    // endregion
}

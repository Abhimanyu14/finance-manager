package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.bottomsheet.AccountsScreenBottomSheetType
import kotlinx.coroutines.flow.MutableStateFlow

internal interface AccountsScreenUIStateDelegate {
    // region UI state
    val isLoading: MutableStateFlow<Boolean>
    val screenBottomSheetType: MutableStateFlow<AccountsScreenBottomSheetType>
    val clickedItemId: MutableStateFlow<Int?>
    // endregion

    // region loading
    fun startLoading()

    fun completeLoading()

    fun <T> withLoading(
        block: () -> T,
    ): T

    suspend fun <T> withLoadingSuspend(
        block: suspend () -> T,
    ): T
    // endregion

    // region state events
    fun deleteAccount()

    fun navigateToAddAccountScreen()

    fun navigateToEditAccountScreen(
        accountId: Int,
    )

    fun navigateUp()

    fun resetScreenBottomSheetType()

    fun setClickedItemId(
        updatedClickedItemId: Int?,
    )

    fun setDefaultAccountIdInDataStore()

    fun setScreenBottomSheetType(
        updatedAccountsScreenBottomSheetType: AccountsScreenBottomSheetType,
    )
    // endregion
}

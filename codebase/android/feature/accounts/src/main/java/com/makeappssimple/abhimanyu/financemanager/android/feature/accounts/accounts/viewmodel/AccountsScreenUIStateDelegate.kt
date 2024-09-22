package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.bottomsheet.AccountsScreenBottomSheetType
import kotlinx.coroutines.flow.MutableSharedFlow

internal interface AccountsScreenUIStateDelegate {
    // region UI state
    val refreshSignal: MutableSharedFlow<Unit>
    val isLoading: Boolean
    val screenBottomSheetType: AccountsScreenBottomSheetType
    val clickedItemId: Int?
    // endregion

    // region refresh
    fun refresh()
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

    fun updateClickedItemId(
        updatedClickedItemId: Int?,
    )

    fun setDefaultAccountIdInDataStore()

    fun updateScreenBottomSheetType(
        updatedAccountsScreenBottomSheetType: AccountsScreenBottomSheetType,
    )
    // endregion
}

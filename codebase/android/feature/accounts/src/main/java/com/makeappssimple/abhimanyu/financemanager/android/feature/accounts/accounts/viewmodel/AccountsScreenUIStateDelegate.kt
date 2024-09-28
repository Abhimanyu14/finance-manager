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

    // region state events
    fun completeLoading(
        refresh: Boolean = true,
    )

    fun deleteAccount()

    fun navigateToAddAccountScreen()

    fun navigateToEditAccountScreen(
        accountId: Int,
    )

    fun navigateUp()

    fun resetScreenBottomSheetType()

    fun startLoading(
        refresh: Boolean = true,
    )

    fun updateClickedItemId(
        updatedClickedItemId: Int?,
        refresh: Boolean = true,
    )

    fun setDefaultAccountIdInDataStore()

    fun updateScreenBottomSheetType(
        updatedAccountsScreenBottomSheetType: AccountsScreenBottomSheetType,
        refresh: Boolean = true,
    )
    // endregion
}

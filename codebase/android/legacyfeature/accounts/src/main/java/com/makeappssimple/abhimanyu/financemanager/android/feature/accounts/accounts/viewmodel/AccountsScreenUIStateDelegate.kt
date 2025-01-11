package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.cre.common.state.common.ScreenUICommonState
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.bottomsheet.AccountsScreenBottomSheetType

internal interface AccountsScreenUIStateDelegate : ScreenUICommonState {
    // region UI state
    val screenBottomSheetType: AccountsScreenBottomSheetType
    val clickedItemId: Int?
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
        shouldRefresh: Boolean = true,
    )

    fun setDefaultAccountIdInDataStore()

    fun updateScreenBottomSheetType(
        updatedAccountsScreenBottomSheetType: AccountsScreenBottomSheetType,
        shouldRefresh: Boolean = true,
    )
    // endregion
}

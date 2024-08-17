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
    // endregion

    // region state events
    fun deleteAccount(
        accountId: Int,
    )

    fun navigateToAddAccountScreen()

    fun navigateToEditAccountScreen(
        accountId: Int,
    )

    fun navigateUp()

    fun resetScreenBottomSheetType()

    fun setClickedItemId(
        updatedClickedItemId: Int?,
    )

    fun setDefaultAccountIdInDataStore(
        accountId: Int,
    )

    fun setScreenBottomSheetType(
        updatedAccountsScreenBottomSheetType: AccountsScreenBottomSheetType,
    )
    // endregion
}

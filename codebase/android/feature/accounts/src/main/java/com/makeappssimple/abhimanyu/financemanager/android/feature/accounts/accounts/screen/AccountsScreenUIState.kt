package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.screen

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.accounts.AccountsListItemData

@Stable
internal class AccountsScreenUIState(
    val screenBottomSheetType: AccountsScreenBottomSheetType,
    val isBottomSheetVisible: Boolean,
    val isLoading: Boolean,
    val accountIdToDelete: Int?,
    val clickedItemId: Int?,
    val accountsListItemDataList: List<AccountsListItemData>,
    val accountsTotalBalanceAmountValue: Long,
    val accountsTotalMinimumBalanceAmountValue: Long,
) : ScreenUIState

package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.screen

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.accounts.AccountsListItemData
import kotlinx.collections.immutable.ImmutableList

@Stable
internal data class AccountsScreenUIState(
    val screenBottomSheetType: AccountsScreenBottomSheetType,
    val isBottomSheetVisible: Boolean,
    val isLoading: Boolean,
    val clickedItemId: Int?,
    val accountsListItemDataList: ImmutableList<AccountsListItemData>,
    val accountsTotalBalanceAmountValue: Long,
    val accountsTotalMinimumBalanceAmountValue: Long,
) : ScreenUIState

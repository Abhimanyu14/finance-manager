package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.state

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.listitem.accounts.AccountsListItemData
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.bottomsheet.AccountsScreenBottomSheetType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Stable
internal data class AccountsScreenUIState(
    val screenBottomSheetType: AccountsScreenBottomSheetType = AccountsScreenBottomSheetType.None,
    val isBottomSheetVisible: Boolean = false,
    val isLoading: Boolean = true,
    val clickedItemId: Int? = null,
    val accountsListItemDataList: ImmutableList<AccountsListItemData> = persistentListOf(),
    val accountsTotalBalanceAmountValue: Long = 0L,
    val accountsTotalMinimumBalanceAmountValue: Long = 0L,
) : ScreenUIState

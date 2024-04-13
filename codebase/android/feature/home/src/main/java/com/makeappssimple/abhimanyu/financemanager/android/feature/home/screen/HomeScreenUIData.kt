package com.makeappssimple.abhimanyu.financemanager.android.feature.home.screen

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transaction.TransactionListItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewCardViewModelData

@Immutable
public data class HomeScreenUIData(
    val isBackupCardVisible: Boolean = false,
    val overviewTabSelectionIndex: Int = 0,
    val transactionListItemDataList: List<TransactionListItemData> = emptyList(),
    val accountsTotalBalanceAmountValue: Long = 0L,
    val accountsTotalMinimumBalanceAmountValue: Long = 0L,
    val overviewCardData: OverviewCardViewModelData? = null,
) : ScreenUIData

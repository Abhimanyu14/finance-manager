package com.makeappssimple.abhimanyu.financemanager.android.feature.home.screen

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.chart.composepie.data.PieChartData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transaction.TransactionListItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewCardViewModelData
import kotlinx.collections.immutable.ImmutableList

@Stable
internal data class HomeScreenUIState(
    val isBackupCardVisible: Boolean,
    val isBalanceVisible: Boolean,
    val isLoading: Boolean,
    val isRecentTransactionsTrailingTextVisible: Boolean,
    val screenBottomSheetType: HomeScreenBottomSheetType,
    val overviewTabSelectionIndex: Int,
    val transactionListItemDataList: ImmutableList<TransactionListItemData>,
    val accountsTotalBalanceAmountValue: Long,
    val accountsTotalMinimumBalanceAmountValue: Long,
    val overviewCardData: OverviewCardViewModelData,
    val pieChartData: PieChartData,
) : ScreenUIState

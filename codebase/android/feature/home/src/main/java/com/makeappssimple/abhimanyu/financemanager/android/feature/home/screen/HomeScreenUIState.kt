package com.makeappssimple.abhimanyu.financemanager.android.feature.home.screen

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.chart.composepie.data.PieChartData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transaction.TransactionListItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewCardViewModelData
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Stable
internal data class HomeScreenUIState(
    val isBackupCardVisible: Boolean = false,
    val isBalanceVisible: Boolean = false,
    val isBottomSheetVisible: Boolean = false,
    val isLoading: Boolean = true,
    val isRecentTransactionsTrailingTextVisible: Boolean = false,
    val screenBottomSheetType: HomeScreenBottomSheetType = HomeScreenBottomSheetType.None,
    val overviewTabSelectionIndex: Int = 0,
    val transactionListItemDataList: ImmutableList<TransactionListItemData> = persistentListOf(),
    val accountsTotalBalanceAmountValue: Long = 0L,
    val accountsTotalMinimumBalanceAmountValue: Long = 0L,
    val overviewCardData: OverviewCardViewModelData = OverviewCardViewModelData(),
    val pieChartData: PieChartData = PieChartData(),
) : ScreenUIState

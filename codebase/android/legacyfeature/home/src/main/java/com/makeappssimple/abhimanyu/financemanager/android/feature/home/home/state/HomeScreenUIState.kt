package com.makeappssimple.abhimanyu.financemanager.android.feature.home.home.state

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.cre.chart.composepie.data.PieChartData
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.listitem.transaction.TransactionListItemData
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.overview_card.OverviewCardViewModelData
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.home.bottomsheet.HomeScreenBottomSheetType
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

package com.makeappssimple.abhimanyu.financemanager.android.feature.home.home.state

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewCardAction
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.home.bottomsheet.HomeScreenBottomSheetType

@Stable
internal class HomeScreenUIStateEvents(
    val handleOverviewCardAction: (overviewCardAction: OverviewCardAction) -> Unit = {},
    val navigateToAccountsScreen: () -> Unit = {},
    val navigateToAddTransactionScreen: () -> Unit = {},
    val navigateToAnalysisScreen: () -> Unit = {},
    val navigateToSettingsScreen: () -> Unit = {},
    val navigateToTransactionsScreen: () -> Unit = {},
    val navigateToViewTransactionScreen: (transactionId: Int) -> Unit = {},
    val resetScreenBottomSheetType: () -> Unit = {},
    val updateIsBalanceVisible: (Boolean) -> Unit = {},
    val updateOverviewTabSelectionIndex: (updatedOverviewTabSelectionIndex: Int) -> Unit = {},
    val updateScreenBottomSheetType: (HomeScreenBottomSheetType) -> Unit = {},
) : ScreenUIStateEvents

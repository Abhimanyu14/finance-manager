package com.makeappssimple.abhimanyu.financemanager.android.feature.home.screen

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEvent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewCardAction

@Immutable
public sealed class HomeScreenUIEvent : ScreenUIEvent {
    public data object CreateDocument : HomeScreenUIEvent()
    public data object NavigateToAccountsScreen : HomeScreenUIEvent()
    public data object NavigateToAddTransactionScreen : HomeScreenUIEvent()
    public data object NavigateToAnalysisScreen : HomeScreenUIEvent()
    public data object NavigateToSettingsScreen : HomeScreenUIEvent()
    public data object NavigateToTransactionsScreen : HomeScreenUIEvent()

    public data class NavigateToViewTransactionScreen(
        val transactionId: Int,
    ) : HomeScreenUIEvent()

    public data class HandleOverviewCardAction(
        val overviewCardAction: OverviewCardAction,
    ) : HomeScreenUIEvent()

    public data class OnOverviewTabClick(
        val index: Int,
    ) : HomeScreenUIEvent()
}

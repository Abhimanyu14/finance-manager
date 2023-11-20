package com.makeappssimple.abhimanyu.financemanager.android.feature.home.screen

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEvent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewCardAction

@Immutable
sealed class HomeScreenUIEvent : ScreenUIEvent {
    data object CreateDocument : HomeScreenUIEvent()
    data object NavigateToAccountsScreen : HomeScreenUIEvent()
    data object NavigateToAddTransactionScreen : HomeScreenUIEvent()
    data object NavigateToAnalysisScreen : HomeScreenUIEvent()
    data object NavigateToSettingsScreen : HomeScreenUIEvent()
    data object NavigateToTransactionsScreen : HomeScreenUIEvent()

    data class HandleOverviewCardAction(
        val overviewCardAction: OverviewCardAction,
    ) : HomeScreenUIEvent()

    data class OnOverviewTabClick(
        val index: Int,
    ) : HomeScreenUIEvent()
}

package com.makeappssimple.abhimanyu.financemanager.android.feature.home.screen

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEvent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewCardAction

@Immutable
public sealed class HomeScreenUIEvent : ScreenUIEvent {
    public data object OnBackupCardClick : HomeScreenUIEvent()
    public data object OnFloatingActionButtonClick : HomeScreenUIEvent()
    public data object OnHomeRecentTransactionsClick : HomeScreenUIEvent()
    public data object OnNavigationBackButtonClick : HomeScreenUIEvent()
    public data object OnTopAppBarSettingsButtonClick : HomeScreenUIEvent()
    public data object OnTotalBalanceCardClick : HomeScreenUIEvent()
    public data object OnTotalBalanceCardViewBalanceClick : HomeScreenUIEvent()

    public data class OnTransactionListItemClick(
        val transactionId: Int,
    ) : HomeScreenUIEvent()

    public sealed class OnOverviewCard {
        public data object Click : HomeScreenUIEvent()

        public data class Action(
            val overviewCardAction: OverviewCardAction,
        ) : HomeScreenUIEvent()

        public data class TabClick(
            val index: Int,
        ) : HomeScreenUIEvent()
    }
}

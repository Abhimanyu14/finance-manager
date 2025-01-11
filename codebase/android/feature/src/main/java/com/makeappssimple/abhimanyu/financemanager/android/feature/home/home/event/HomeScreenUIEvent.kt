package com.makeappssimple.abhimanyu.financemanager.android.feature.home.home.event

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEvent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewCardAction

@Immutable
internal sealed class HomeScreenUIEvent : ScreenUIEvent {
    data object OnBackupCardClick : HomeScreenUIEvent()
    data object OnFloatingActionButtonClick : HomeScreenUIEvent()
    data object OnHomeRecentTransactionsClick : HomeScreenUIEvent()
    data object OnNavigationBackButtonClick : HomeScreenUIEvent()
    data object OnTopAppBarSettingsButtonClick : HomeScreenUIEvent()
    data object OnTotalBalanceCardClick : HomeScreenUIEvent()
    data object OnTotalBalanceCardViewBalanceClick : HomeScreenUIEvent()

    data class OnTransactionListItemClick(
        val transactionId: Int,
    ) : HomeScreenUIEvent()

    sealed class OnOverviewCard {
        data object Click : HomeScreenUIEvent()

        data class Action(
            val overviewCardAction: OverviewCardAction,
        ) : HomeScreenUIEvent()

        data class TabClick(
            val index: Int,
        ) : HomeScreenUIEvent()
    }
}

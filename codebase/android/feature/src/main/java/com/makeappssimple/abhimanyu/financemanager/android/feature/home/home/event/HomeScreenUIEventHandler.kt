package com.makeappssimple.abhimanyu.financemanager.android.feature.home.home.event

import android.net.Uri
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.home.state.HomeScreenUIStateEvents

internal class HomeScreenUIEventHandler internal constructor(
    private val uiStateEvents: HomeScreenUIStateEvents,
    private val createDocument: ((uri: Uri?) -> Unit) -> Unit,
) {
    fun handleUIEvent(
        uiEvent: HomeScreenUIEvent,
    ) {
        when (uiEvent) {
            is HomeScreenUIEvent.OnBackupCardClick -> {
                createDocument {}
            }

            is HomeScreenUIEvent.OnTotalBalanceCardClick -> {
                uiStateEvents.navigateToAccountsScreen()
            }

            is HomeScreenUIEvent.OnTotalBalanceCardViewBalanceClick -> {
                uiStateEvents.setBalanceVisible(true)
            }

            is HomeScreenUIEvent.OnFloatingActionButtonClick -> {
                uiStateEvents.navigateToAddTransactionScreen()
            }

            is HomeScreenUIEvent.OnOverviewCard.Click -> {
                uiStateEvents.navigateToAnalysisScreen()
            }

            is HomeScreenUIEvent.OnTopAppBarSettingsButtonClick -> {
                uiStateEvents.navigateToSettingsScreen()
            }

            is HomeScreenUIEvent.OnHomeRecentTransactionsClick -> {
                uiStateEvents.navigateToTransactionsScreen()
            }

            is HomeScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateEvents.resetScreenBottomSheetType()
            }

            is HomeScreenUIEvent.OnTransactionListItemClick -> {
                uiStateEvents.navigateToViewTransactionScreen(uiEvent.transactionId)
            }

            is HomeScreenUIEvent.OnOverviewCard.Action -> {
                uiStateEvents.handleOverviewCardAction(uiEvent.overviewCardAction)
            }

            is HomeScreenUIEvent.OnOverviewCard.TabClick -> {
                uiStateEvents.setOverviewTabSelectionIndex(uiEvent.index)
            }
        }
    }
}

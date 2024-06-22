package com.makeappssimple.abhimanyu.financemanager.android.feature.home.home.event

import android.net.Uri
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.home.state.HomeScreenUIStateAndStateEvents

internal class HomeScreenUIEventHandler internal constructor(
    private val uiStateAndStateEvents: HomeScreenUIStateAndStateEvents,
    private val createDocument: ((uri: Uri?) -> Unit) -> Unit,
) {
    fun handleUIEvent(
        uiEvent: HomeScreenUIEvent,
    ) {
        when (uiEvent) {
            is HomeScreenUIEvent.OnBackupCardClick -> {
                createDocument {

                }
            }

            is HomeScreenUIEvent.OnTotalBalanceCardClick -> {
                uiStateAndStateEvents.events.navigateToAccountsScreen()
            }

            is HomeScreenUIEvent.OnTotalBalanceCardViewBalanceClick -> {
                uiStateAndStateEvents.events.setBalanceVisible(true)
            }

            is HomeScreenUIEvent.OnFloatingActionButtonClick -> {
                uiStateAndStateEvents.events.navigateToAddTransactionScreen()
            }

            is HomeScreenUIEvent.OnOverviewCard.Click -> {
                uiStateAndStateEvents.events.navigateToAnalysisScreen()
            }

            is HomeScreenUIEvent.OnTopAppBarSettingsButtonClick -> {
                uiStateAndStateEvents.events.navigateToSettingsScreen()
            }

            is HomeScreenUIEvent.OnHomeRecentTransactionsClick -> {
                uiStateAndStateEvents.events.navigateToTransactionsScreen()
            }

            is HomeScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateAndStateEvents.events.resetScreenBottomSheetType()
            }

            is HomeScreenUIEvent.OnTransactionListItemClick -> {
                uiStateAndStateEvents.events.navigateToViewTransactionScreen(uiEvent.transactionId)
            }

            is HomeScreenUIEvent.OnOverviewCard.Action -> {
                uiStateAndStateEvents.events.handleOverviewCardAction(uiEvent.overviewCardAction)
            }

            is HomeScreenUIEvent.OnOverviewCard.TabClick -> {
                uiStateAndStateEvents.events.setOverviewTabSelectionIndex(uiEvent.index)
            }
        }
    }
}

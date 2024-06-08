package com.makeappssimple.abhimanyu.financemanager.android.feature.home.screen

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.MimeTypeConstants
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.viewmodel.HomeScreenViewModel

public class HomeScreenUIEventHandler internal constructor(
    private val viewModel: HomeScreenViewModel,
    private val uiStateAndEvents: HomeScreenUIStateAndEvents,
    private val createDocument: ManagedActivityResultLauncher<String, Uri?>,
) {
    public fun handleUIEvent(
        uiEvent: HomeScreenUIEvent,
    ) {
        when (uiEvent) {
            is HomeScreenUIEvent.OnBackupCardClick -> {
                createDocument.launch(MimeTypeConstants.JSON)
            }

            is HomeScreenUIEvent.OnTotalBalanceCardClick -> {
                viewModel.navigateToAccountsScreen()
            }

            is HomeScreenUIEvent.OnTotalBalanceCardViewBalanceClick -> {
                uiStateAndEvents.events.setBalanceVisible(true)
            }

            is HomeScreenUIEvent.OnFloatingActionButtonClick -> {
                viewModel.navigateToAddTransactionScreen()
            }

            is HomeScreenUIEvent.OnOverviewCard.Click -> {
                viewModel.navigateToAnalysisScreen()
            }

            is HomeScreenUIEvent.OnTopAppBarSettingsButtonClick -> {
                viewModel.navigateToSettingsScreen()
            }

            is HomeScreenUIEvent.OnHomeRecentTransactionsClick -> {
                viewModel.navigateToTransactionsScreen()
            }

            is HomeScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateAndEvents.events.resetScreenBottomSheetType()
            }

            is HomeScreenUIEvent.OnTransactionListItemClick -> {
                viewModel.navigateToViewTransactionScreen(
                    transactionId = uiEvent.transactionId,
                )
            }

            is HomeScreenUIEvent.OnOverviewCard.Action -> {
                viewModel.handleOverviewCardAction(
                    overviewCardAction = uiEvent.overviewCardAction,
                )
            }

            is HomeScreenUIEvent.OnOverviewCard.TabClick -> {
                viewModel.setOverviewTabSelectionIndex(
                    updatedOverviewTabSelectionIndex = uiEvent.index,
                )
            }
        }
    }
}

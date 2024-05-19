package com.makeappssimple.abhimanyu.financemanager.android.feature.home.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_CONTENT_HOME
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_HOME
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.button.MyFloatingActionButton
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.button.MyIconButton
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.navigationBarLandscapeSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.navigationBarsSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.icons.MyIcons
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.screen.CommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.screen.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.backup_card.BackupCard
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.backup_card.BackupCardData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.backup_card.BackupCardEvent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.home_recent_transactions.HomeRecentTransactions
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.home_recent_transactions.HomeRecentTransactionsData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.home_recent_transactions.HomeRecentTransactionsEvent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transaction.TransactionListItem
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transaction.TransactionListItemEvent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewCard
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewCardData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewCardEvent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.top_app_bar.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.total_balance_card.TotalBalanceCard
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.total_balance_card.TotalBalanceCardData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.total_balance_card.TotalBalanceCardEvent
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.R

private val bottomContentPadding = 100.dp

@Composable
internal fun HomeScreenUI(
    uiState: HomeScreenUIState,
    state: CommonScreenUIState = rememberCommonScreenUIState(),
    handleUIEvent: (uiEvent: HomeScreenUIEvent) -> Unit = {},
) {
    MyScaffold(
        modifier = Modifier
            .testTag(
                tag = SCREEN_HOME,
            )
            .fillMaxSize(),
        sheetContent = {
            when (uiState.screenBottomSheetType) {
                is HomeScreenBottomSheetType.None -> {
                    VerticalSpacer()
                }
            }
        },
        sheetState = state.modalBottomSheetState,
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = R.string.screen_home_appbar_title,
                appBarActions = {
                    MyIconButton(
                        tint = MaterialTheme.colorScheme.onBackground,
                        imageVector = MyIcons.Settings,
                        contentDescriptionStringResourceId = R.string.screen_home_appbar_settings,
                        onClick = {
                            handleUIEvent(HomeScreenUIEvent.OnTopAppBarSettingsButtonClick)
                        },
                    )
                },
            )
        },
        floatingActionButton = {
            MyFloatingActionButton(
                modifier = Modifier
                    .navigationBarsSpacer(),
                iconImageVector = MyIcons.Add,
                contentDescription = stringResource(
                    id = R.string.screen_home_floating_action_button_content_description,
                ),
                onClick = {
                    handleUIEvent(HomeScreenUIEvent.OnFloatingActionButtonClick)
                },
            )
        },
        onClick = state.focusManager::clearFocus,
        contentTestTag = SCREEN_CONTENT_HOME,
        coroutineScope = state.coroutineScope,
        onNavigationBackButtonClick = {
            handleUIEvent(HomeScreenUIEvent.OnNavigationBackButtonClick)
        },
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(
                    state = rememberScrollState(),
                )
                .navigationBarLandscapeSpacer()
                .padding(
                    bottom = bottomContentPadding,
                ),
        ) {
            TotalBalanceCard(
                data = TotalBalanceCardData(
                    isBalanceVisible = uiState.isBalanceVisible,
                    isClickable = true,
                    isLoading = uiState.isLoading,
                    totalBalanceAmount = uiState.accountsTotalBalanceAmountValue,
                    totalMinimumBalanceAmount = uiState.accountsTotalMinimumBalanceAmountValue,
                ),
                handleEvent = { event ->
                    when (event) {
                        is TotalBalanceCardEvent.OnClick -> {
                            handleUIEvent(HomeScreenUIEvent.OnTotalBalanceCardClick)
                        }

                        is TotalBalanceCardEvent.OnViewBalanceClick -> {
                            handleUIEvent(HomeScreenUIEvent.OnTotalBalanceCardViewBalanceClick)
                        }
                    }
                },
            )
            AnimatedVisibility(
                visible = uiState.isBackupCardVisible,
            ) {
                BackupCard(
                    data = BackupCardData(
                        isLoading = uiState.isLoading,
                    ),
                    handleEvent = { event ->
                        when (event) {
                            is BackupCardEvent.OnClick -> {
                                handleUIEvent(HomeScreenUIEvent.OnBackupCardClick)
                            }
                        }
                    },
                )
            }
            OverviewCard(
                data = OverviewCardData(
                    isLoading = uiState.isLoading,
                    overviewTabSelectionIndex = uiState.overviewTabSelectionIndex,
                    pieChartData = uiState.pieChartData,
                    title = uiState.overviewCardData.title,
                ),
                handleEvent = { event ->
                    when (event) {
                        is OverviewCardEvent.OnClick -> {
                            handleUIEvent(HomeScreenUIEvent.OnOverviewCard.Click)
                        }

                        is OverviewCardEvent.OnOverviewCardAction -> {
                            handleUIEvent(
                                HomeScreenUIEvent.OnOverviewCard.Action(
                                    overviewCardAction = event.overviewCardAction,
                                )
                            )
                        }

                        is OverviewCardEvent.OnOverviewTabClick -> {
                            handleUIEvent(
                                HomeScreenUIEvent.OnOverviewCard.TabClick(
                                    index = event.index,
                                )
                            )
                        }
                    }
                },
            )
            HomeRecentTransactions(
                data = HomeRecentTransactionsData(
                    isTrailingTextVisible = uiState.isRecentTransactionsTrailingTextVisible,
                ),
                handleEvent = { event ->
                    when (event) {
                        is HomeRecentTransactionsEvent.OnClick -> {
                            if (uiState.isRecentTransactionsTrailingTextVisible) {
                                handleUIEvent(HomeScreenUIEvent.OnHomeRecentTransactionsClick)
                            }
                        }
                    }
                },
            )
            uiState.transactionListItemDataList.map { listItem ->
                TransactionListItem(
                    data = listItem,
                    handleEvent = { event ->
                        when (event) {
                            is TransactionListItemEvent.OnClick -> {
                                handleUIEvent(
                                    HomeScreenUIEvent.OnTransactionListItemClick(
                                        transactionId = listItem.transactionId,
                                    )
                                )
                            }

                            is TransactionListItemEvent.OnDeleteButtonClick -> {}

                            is TransactionListItemEvent.OnEditButtonClick -> {}

                            is TransactionListItemEvent.OnLongClick -> {}

                            is TransactionListItemEvent.OnRefundButtonClick -> {}
                        }
                    },
                )
            }
        }
    }
}

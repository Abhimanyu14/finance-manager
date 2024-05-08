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
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.backup_card.BackupCard
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.backup_card.BackupCardData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.backup_card.BackupCardEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.home_recent_transactions.HomeRecentTransactions
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.home_recent_transactions.HomeRecentTransactionsData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.home_recent_transactions.HomeRecentTransactionsEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transaction.TransactionListItem
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transaction.TransactionListItemEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewCard
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewCardData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewCardEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.top_app_bar.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.total_balance_card.TotalBalanceCard
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.total_balance_card.TotalBalanceCardData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.total_balance_card.TotalBalanceCardEvents
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
        onBackPress = uiState.resetScreenBottomSheetType,
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
                    isLoading = uiState.isLoading,
                    totalBalanceAmount = uiState.accountsTotalBalanceAmountValue,
                    totalMinimumBalanceAmount = uiState.accountsTotalMinimumBalanceAmountValue,
                ),
                events = TotalBalanceCardEvents(
                    onClick = {
                        handleUIEvent(HomeScreenUIEvent.OnTotalBalanceCardClick)
                    },
                    onViewBalanceClick = {
                        uiState.setBalanceVisible(true)
                    },
                ),
            )
            AnimatedVisibility(
                visible = uiState.isBackupCardVisible,
            ) {
                BackupCard(
                    data = BackupCardData(
                        isLoading = uiState.isLoading,
                    ),
                    events = BackupCardEvents(
                        onClick = {
                            handleUIEvent(HomeScreenUIEvent.OnBackupCardClick)
                        },
                    ),
                )
            }
            OverviewCard(
                data = OverviewCardData(
                    isLoading = uiState.isLoading,
                    overviewTabSelectionIndex = uiState.overviewTabSelectionIndex,
                    pieChartData = uiState.pieChartData,
                    title = uiState.overviewCardData.title,
                ),
                events = OverviewCardEvents(
                    onClick = {
                        handleUIEvent(HomeScreenUIEvent.OnOverviewCard.Click)
                    },
                    onOverviewTabClick = {
                        handleUIEvent(HomeScreenUIEvent.OnOverviewCard.TabClick(it))
                    },
                    handleOverviewCardAction = {
                        handleUIEvent(HomeScreenUIEvent.OnOverviewCard.Action(it))
                    },
                ),
            )
            HomeRecentTransactions(
                data = HomeRecentTransactionsData(
                    isTrailingTextVisible = uiState.transactionListItemDataList.isNotEmpty(),
                ),
                events = HomeRecentTransactionsEvents(
                    onClick = if (uiState.transactionListItemDataList.isNotEmpty()) {
                        { handleUIEvent(HomeScreenUIEvent.OnHomeRecentTransactionsClick) }
                    } else {
                        null
                    },
                ),
            )
            uiState.transactionListItemDataList.map { listItem ->
                TransactionListItem(
                    data = listItem,
                    events = TransactionListItemEvents(
                        onClick = {
                            handleUIEvent(
                                HomeScreenUIEvent.OnTransactionListItemClick(
                                    transactionId = listItem.transactionId,
                                )
                            )
                        },
                    ),
                )
            }
        }
    }
}

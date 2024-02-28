package com.makeappssimple.abhimanyu.financemanager.android.feature.home.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.backup_card.BackupCard
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.backup_card.BackupCardData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.backup_card.BackupCardEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.TransactionListItem
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.TransactionListItemEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewCard
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewCardData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewCardEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.recenttransactions.HomeRecentTransactionsUI
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.total_balance_card.TotalBalanceCard
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.total_balance_card.TotalBalanceCardData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.total_balance_card.TotalBalanceCardEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.icons.MyIcons
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.R

private val bottomContentPadding = 80.dp

@Composable
internal fun HomeScreenUI(
    uiState: HomeScreenUIState,
    state: CommonScreenUIState = rememberCommonScreenUIState(),
    handleUIEvents: (uiEvent: HomeScreenUIEvent) -> Unit = {},
) {
    MyScaffold(
        modifier = Modifier
            .testTag(
                tag = SCREEN_HOME,
            )
            .fillMaxSize(),
        sheetContent = {
            when (uiState.screenBottomSheetType) {
                HomeScreenBottomSheetType.NONE -> {
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
                        contentDescription = stringResource(
                            id = R.string.screen_home_appbar_settings,
                        ),
                        onClick = {
                            handleUIEvents(HomeScreenUIEvent.NavigateToSettingsScreen)
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
                    handleUIEvents(HomeScreenUIEvent.NavigateToAddTransactionScreen)
                },
            )
        },
        onClick = state.focusManager::clearFocus,
        coroutineScope = state.coroutineScope,
        onBackPress = uiState.resetScreenBottomSheetType,
    ) {
        LazyColumn(
            modifier = Modifier
                .testTag(
                    tag = SCREEN_CONTENT_HOME,
                )
                .navigationBarLandscapeSpacer(),
            contentPadding = PaddingValues(
                bottom = bottomContentPadding,
            ),
        ) {
            item {
                TotalBalanceCard(
                    data = TotalBalanceCardData(
                        isLoading = uiState.isLoading,
                        totalBalanceAmount = uiState.accountsTotalBalanceAmountValue,
                        totalMinimumBalanceAmount = uiState.accountsTotalMinimumBalanceAmountValue,
                    ),
                    events = TotalBalanceCardEvents(
                        onClick = {
                            handleUIEvents(HomeScreenUIEvent.NavigateToAccountsScreen)
                        },
                    ),
                )
            }
            item {
                AnimatedVisibility(
                    visible = uiState.isBackupCardVisible,
                ) {
                    BackupCard(
                        data = BackupCardData(
                            isLoading = uiState.isLoading,
                        ),
                        events = BackupCardEvents(
                            onClick = {
                                handleUIEvents(HomeScreenUIEvent.CreateDocument)
                            },
                        ),
                    )
                }
            }
            item {
                OverviewCard(
                    data = OverviewCardData(
                        isLoading = uiState.isLoading,
                        overviewTabSelectionIndex = uiState.overviewTabSelectionIndex,
                        pieChartData = uiState.pieChartData,
                        title = uiState.overviewCardData.title,
                    ),
                    events = OverviewCardEvents(
                        onClick = {
                            handleUIEvents(HomeScreenUIEvent.NavigateToAnalysisScreen)
                        },
                        onOverviewTabClick = {
                            handleUIEvents(HomeScreenUIEvent.OnOverviewTabClick(it))
                        },
                        handleOverviewCardAction = {
                            handleUIEvents(HomeScreenUIEvent.HandleOverviewCardAction(it))
                        },
                    ),
                )
            }
            item {
                HomeRecentTransactionsUI(
                    isTrailingTextVisible = uiState.transactionListItemDataList.isNotEmpty(),
                    onClick = if (uiState.transactionListItemDataList.isNotEmpty()) {
                        { handleUIEvents(HomeScreenUIEvent.NavigateToTransactionsScreen) }
                    } else {
                        null
                    },
                )
            }
            items(
                items = uiState.transactionListItemDataList,
                key = { listItem ->
                    listItem.transactionId
                },
            ) { listItem ->
                TransactionListItem(
                    data = listItem,
                    events = TransactionListItemEvents(
                        onClick = {
                            handleUIEvents(
                                HomeScreenUIEvent.NavigateToViewTransactionScreen(
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

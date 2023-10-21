package com.makeappssimple.abhimanyu.financemanager.android.feature.home.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.buttons.MyFloatingActionButton
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.navigationBarLandscapeSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.navigationBarsSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.BottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.backup_card.BackupCard
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.backup_card.BackupCardData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.backup_card.BackupCardEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewCard
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewCardAction
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewCardData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewCardEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewCardViewModelData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.total_balance_card.TotalBalanceCard
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.total_balance_card.TotalBalanceCardData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.total_balance_card.TotalBalanceCardEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.transaction_list_item.TransactionListItem
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.transaction_list_item.TransactionListItemData
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.component.recenttransactions.HomeRecentTransactionsUI

private val bottomContentPadding = 80.dp

enum class HomeBottomSheetType : BottomSheetType {
    NONE,
}

@Immutable
data class HomeScreenUIData(
    val isBackupCardVisible: Boolean = false,
    val overviewTabSelectionIndex: Int = 0,
    val transactionListItemDataList: List<TransactionListItemData> = emptyList(),
    val accountsTotalBalanceAmountValue: Long = 0L,
    val accountsTotalMinimumBalanceAmountValue: Long = 0L,
    val overviewCardData: OverviewCardViewModelData? = null,
)

@Immutable
sealed class HomeScreenUIEvent {
    object CreateDocument : HomeScreenUIEvent()
    object NavigateToAddTransactionScreen : HomeScreenUIEvent()
    object NavigateToAnalysisScreen : HomeScreenUIEvent()
    object NavigateToSettingsScreen : HomeScreenUIEvent()
    object NavigateToAccountsScreen : HomeScreenUIEvent()

    object NavigateToTransactionsScreen : HomeScreenUIEvent()

    data class HandleOverviewCardAction(
        val overviewCardAction: OverviewCardAction,
    ) : HomeScreenUIEvent()

    data class OnOverviewTabClick(
        val index: Int,
    ) : HomeScreenUIEvent()
}

@Composable
internal fun HomeScreenUI(
    uiState: HomeScreenUIState,
    state: CommonScreenUIState,
    handleUIEvents: (uiEvent: HomeScreenUIEvent) -> Unit,
) {
    MyScaffold(
        modifier = Modifier
            .fillMaxSize(),
        sheetContent = {
            when (uiState.homeBottomSheetType) {
                HomeBottomSheetType.NONE -> {
                    VerticalSpacer()
                }
            }
        },
        sheetState = state.modalBottomSheetState,
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = R.string.screen_home_appbar_title,
                appBarActions = {
                    IconButton(
                        onClick = {
                            handleUIEvents(HomeScreenUIEvent.NavigateToSettingsScreen)
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Settings,
                            contentDescription = stringResource(
                                id = R.string.screen_home_appbar_settings,
                            ),
                            tint = MaterialTheme.colorScheme.onBackground,
                        )
                    }
                },
            )
        },
        floatingActionButton = {
            MyFloatingActionButton(
                modifier = Modifier
                    .navigationBarsSpacer(),
                iconImageVector = Icons.Rounded.Add,
                contentDescription = stringResource(
                    id = R.string.screen_home_floating_action_button_content_description,
                ),
                onClick = {
                    handleUIEvents(HomeScreenUIEvent.NavigateToAddTransactionScreen)
                },
            )
        },
        onClick = {
            state.focusManager.clearFocus()
        },
        coroutineScope = state.coroutineScope,
        onBackPress = uiState.resetBottomSheetType,
    ) {
        LazyColumn(
            modifier = Modifier
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
                    listItem.hashCode()
                },
            ) { listItem ->
                TransactionListItem(
                    data = listItem,
                )
            }
        }
    }
}

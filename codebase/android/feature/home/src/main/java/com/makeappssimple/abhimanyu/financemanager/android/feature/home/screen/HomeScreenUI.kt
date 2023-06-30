package com.makeappssimple.abhimanyu.financemanager.android.feature.home.screen

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.MimeTypeConstants
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.buttons.MyFloatingActionButton
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.BottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.BottomSheetHandler
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.backup_card.BackupCard
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.backup_card.BackupCardData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.backup_card.BackupCardEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewCard
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.total_balance_card.TotalBalanceCard
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.transaction_list_item.TransactionListItem
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.transaction_list_item.TransactionListItemData
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.component.bottomappbar.HomeBottomAppBar
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.component.bottomsheet.HomeMenuBottomSheetContent
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.component.recenttransactions.HomeRecentTransactionsUI

enum class HomeBottomSheetType : BottomSheetType {
    MENU,
    NONE,
}

@Immutable
data class HomeScreenUIData(
    val isBackupCardVisible: Boolean = false,
    val transactionListItemDataList: List<TransactionListItemData> = emptyList(),
)

@Immutable
internal data class HomeScreenUIEvents(
    val createDocument: ManagedActivityResultLauncher<String, Uri?>,
    val navigateToAddTransactionScreen: () -> Unit,
    val navigateToAnalysisScreen: () -> Unit,
    val navigateToCategoriesScreen: () -> Unit,
    val navigateToSettingsScreen: () -> Unit,
    val navigateToSourcesScreen: () -> Unit,
    val navigateToTransactionsScreen: () -> Unit,
)

@Composable
internal fun HomeScreenUI(
    events: HomeScreenUIEvents,
    uiState: HomeScreenUIState,
    state: CommonScreenUIState,
) {
    BottomSheetHandler(
        showModalBottomSheet = uiState.homeBottomSheetType != HomeBottomSheetType.NONE,
        bottomSheetType = uiState.homeBottomSheetType,
        coroutineScope = state.coroutineScope,
        keyboardController = state.keyboardController,
        modalBottomSheetState = state.modalBottomSheetState,
        resetBottomSheetType = uiState.resetBottomSheetType,
    )

    MyScaffold(
        sheetState = state.modalBottomSheetState,
        sheetContent = {
            when (uiState.homeBottomSheetType) {
                HomeBottomSheetType.MENU -> {
                    HomeMenuBottomSheetContent(
                        navigateToCategoriesScreen = events.navigateToCategoriesScreen,
                        navigateToSettingsScreen = events.navigateToSettingsScreen,
                        navigateToSourcesScreen = events.navigateToSourcesScreen,
                        resetBottomSheetType = uiState.resetBottomSheetType,
                    )
                }

                HomeBottomSheetType.NONE -> {
                    VerticalSpacer()
                }
            }
        },
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = R.string.screen_home_appbar_title,
            )
        },
        bottomBar = {
            HomeBottomAppBar {
                uiState.setHomeBottomSheetType(HomeBottomSheetType.MENU)
            }
        },
        floatingActionButton = {
            MyFloatingActionButton(
                iconImageVector = Icons.Rounded.Add,
                contentDescription = stringResource(
                    id = R.string.screen_home_floating_action_button_content_description,
                ),
                onClick = events.navigateToAddTransactionScreen,
            )
        },
        isFloatingActionButtonDocked = true,
        onClick = {
            state.focusManager.clearFocus()
        },
        backHandlerEnabled = uiState.homeBottomSheetType != HomeBottomSheetType.NONE,
        coroutineScope = state.coroutineScope,
        onBackPress = uiState.resetBottomSheetType,
        modifier = Modifier
            .fillMaxSize(),
    ) {
        LazyColumn(
            contentPadding = PaddingValues(
                bottom = 48.dp,
            ),
        ) {
            item {
                TotalBalanceCard(
                    onClick = events.navigateToSourcesScreen,
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
                                events.createDocument.launch(MimeTypeConstants.JSON)
                            },
                        ),
                    )
                }
            }
            item {
                OverviewCard(
                    onClick = events.navigateToAnalysisScreen,
                )
            }
            item {
                HomeRecentTransactionsUI(
                    onClick = events.navigateToTransactionsScreen,
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

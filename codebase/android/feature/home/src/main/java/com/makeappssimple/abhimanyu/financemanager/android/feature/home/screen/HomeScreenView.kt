package com.makeappssimple.abhimanyu.financemanager.android.feature.home.screen

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.MimeTypeConstants
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.buttons.MyFloatingActionButton
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.BottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.backup_card.BackupCard
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.overview_card.OverviewCard
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.total_balance_card.TotalBalanceCard
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.transaction_list_item.TransactionListItem
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.transaction_list_item.TransactionListItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.transaction_list_item.TransactionListItemEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.component.bottomappbar.HomeBottomAppBar
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.component.bottomsheet.HomeMenuBottomSheetContent
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.component.recenttransactions.HomeRecentTransactionsView

internal enum class HomeBottomSheetType : BottomSheetType {
    NONE,
    MENU,
}

@Immutable
internal data class HomeScreenViewData(
    val showBackupCard: Boolean,
    val transactionListItemDataList: List<TransactionListItemData>,
)

@Immutable
internal data class HomeScreenViewEvents(
    val createDocument: ManagedActivityResultLauncher<String, Uri?>,
    val navigateToAddTransactionScreen: () -> Unit,
    val navigateToAnalysisScreen: () -> Unit,
    val navigateToCategoriesScreen: () -> Unit,
    val navigateToSettingsScreen: () -> Unit,
    val navigateToSourcesScreen: () -> Unit,
    val navigateToTransactionsScreen: () -> Unit,
)

@Composable
internal fun HomeScreenView(
    data: HomeScreenViewData,
    events: HomeScreenViewEvents,
    state: CommonScreenViewState,
) {
    var homeBottomSheetType by remember {
        mutableStateOf(
            value = HomeBottomSheetType.NONE,
        )
    }

    if (state.modalBottomSheetState.currentValue != ModalBottomSheetValue.Hidden) {
        DisposableEffect(
            key1 = Unit,
        ) {
            onDispose {
                homeBottomSheetType = HomeBottomSheetType.NONE
                state.keyboardController?.hide()
            }
        }
    }

    MyScaffold(
        sheetState = state.modalBottomSheetState,
        sheetContent = {
            when (homeBottomSheetType) {
                HomeBottomSheetType.NONE -> {
                    VerticalSpacer()
                }

                HomeBottomSheetType.MENU -> {
                    HomeMenuBottomSheetContent(
                        coroutineScope = state.coroutineScope,
                        modalBottomSheetState = state.modalBottomSheetState,
                        navigateToCategoriesScreen = events.navigateToCategoriesScreen,
                        navigateToSettingsScreen = events.navigateToSettingsScreen,
                        navigateToSourcesScreen = events.navigateToSourcesScreen,
                        resetBottomSheetType = {
                            homeBottomSheetType = HomeBottomSheetType.NONE
                        },
                    )
                }
            }
        },
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = R.string.screen_home_appbar_title,
            )
        },
        bottomBar = {
            HomeBottomAppBar(
                coroutineScope = state.coroutineScope,
                modalBottomSheetState = state.modalBottomSheetState,
                updateHomeBottomSheetType = {
                    homeBottomSheetType = HomeBottomSheetType.MENU
                },
            )
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
        backHandlerEnabled = homeBottomSheetType != HomeBottomSheetType.NONE,
        coroutineScope = state.coroutineScope,
        onBackPress = {
            homeBottomSheetType = HomeBottomSheetType.NONE
        },
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
                    visible = data.showBackupCard,
                ) {
                    BackupCard(
                        onClick = {
                            events.createDocument.launch(MimeTypeConstants.JSON)
                        }
                    )
                }
            }
            item {
                OverviewCard(
                    onClick = events.navigateToAnalysisScreen,
                )
            }
            item {
                HomeRecentTransactionsView(
                    onClick = events.navigateToTransactionsScreen,
                )
            }
            items(
                items = data.transactionListItemDataList,
                key = { listItem ->
                    listItem.hashCode()
                },
            ) { listItem ->
                TransactionListItem(
                    data = listItem,
                    events = TransactionListItemEvents(),
                )
            }
        }
    }
}

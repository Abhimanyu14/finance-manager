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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.JSON_MIMETYPE
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.getReadableDateAndTime
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.buttons.MyFloatingActionButton
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.util.navigateToAddTransactionScreen
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.util.navigateToSourcesScreen
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.util.navigateToTransactionsScreen
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.BottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.adjustmentEmoji
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.backup_card.BackupCard
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.overview_card.OverviewCard
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.total_balance_card.TotalBalanceCard
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.transaction_list_item.TransactionListItem
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.transferEmoji
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.getAmountTextColor
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.components.bottomappbar.HomeBottomAppBar
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.components.recenttransactions.HomeRecentTransactionsView
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.components.bottomsheet.HomeMenuBottomSheetContent

internal enum class HomeBottomSheetType : BottomSheetType {
    NONE,
    MENU,
}

@Immutable
internal data class HomeScreenViewData(
    val showBackupCard: Boolean,
    val transactionData: List<TransactionData>,
    val createDocument: ManagedActivityResultLauncher<String, Uri?>,
    val navigationManager: NavigationManager,
)

@Composable
internal fun HomeScreenView(
    data: HomeScreenViewData,
    state: CommonScreenViewState,
) {
    var homeBottomSheetType by remember {
        mutableStateOf(
            value = HomeBottomSheetType.NONE,
        )
    }

    if (state.modalBottomSheetState.currentValue != ModalBottomSheetValue.Hidden) {
        DisposableEffect(Unit) {
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
                        navigationManager = data.navigationManager,
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
                onClick = {
                    navigateToAddTransactionScreen(
                        navigationManager = data.navigationManager,
                    )
                },
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
                    onClick = {
                        navigateToSourcesScreen(
                            navigationManager = data.navigationManager,
                        )
                    },
                )
            }
            item {
                AnimatedVisibility(
                    visible = data.showBackupCard,
                ) {
                    BackupCard(
                        onClick = {
                            data.createDocument.launch(JSON_MIMETYPE)
                        }
                    )
                }
            }
            item {
                OverviewCard()
            }
            item {
                HomeRecentTransactionsView(
                    onClick = {
                        navigateToTransactionsScreen(
                            navigationManager = data.navigationManager,
                        )
                    },
                )
            }
            items(
                items = data.transactionData,
                key = { listItem ->
                    listItem.hashCode()
                },
            ) { listItem ->
                val amountColor: Color = listItem.transaction.getAmountTextColor()
                val amountText: String =
                    if (listItem.transaction.transactionType == TransactionType.INCOME ||
                        listItem.transaction.transactionType == TransactionType.EXPENSE ||
                        listItem.transaction.transactionType == TransactionType.ADJUSTMENT ||
                        listItem.transaction.transactionType == TransactionType.REFUND
                    ) {
                        listItem.transaction.amount.toSignedString(
                            isPositive = listItem.sourceTo != null,
                            isNegative = listItem.sourceFrom != null,
                        )
                    } else {
                        listItem.transaction.amount.toString()
                    }
                val dateAndTimeText: String = getReadableDateAndTime(
                    timestamp = listItem.transaction.transactionTimestamp,
                )
                val emoji: String = when (listItem.transaction.transactionType) {
                    TransactionType.TRANSFER -> {
                        transferEmoji
                    }

                    TransactionType.ADJUSTMENT -> {
                        adjustmentEmoji
                    }

                    else -> {
                        listItem.category?.emoji.orEmpty()
                    }
                }
                val sourceFromName = listItem.sourceFrom?.name
                val sourceToName = listItem.sourceTo?.name
                val title: String = listItem.transaction.title
                val transactionForText: String =
                    listItem.transactionFor.titleToDisplay

                TransactionListItem(
                    amountColor = amountColor,
                    amountText = amountText,
                    dateAndTimeText = dateAndTimeText,
                    emoji = emoji,
                    sourceFromName = sourceFromName,
                    sourceToName = sourceToName,
                    title = title,
                    transactionForText = transactionForText,
                )
            }
        }
    }
}
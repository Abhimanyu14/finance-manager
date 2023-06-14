package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyLinearProgressIndicator
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.BottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.BottomSheetHandler
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.transaction_list_item.TransactionListItem
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.transaction_list_item.TransactionListItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.transaction_list_item.TransactionListItemEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.common.TransactionDeleteConfirmationBottomSheetContent

enum class ViewTransactionBottomSheetType : BottomSheetType {
    DELETE_CONFIRMATION,
    NONE,
}

@Immutable
data class ViewTransactionScreenUIData(
    val originalTransactionListItemData: TransactionListItemData? = null,
    val refundTransactionListItemData: List<TransactionListItemData>? = null,
    val transactionListItemData: TransactionListItemData? = null,
)

@Immutable
internal data class ViewTransactionScreenUIEvents(
    val deleteTransaction: (transactionId: Int) -> Unit,
    val navigateToAddTransactionScreen: (transactionId: Int) -> Unit,
    val navigateToEditTransactionScreen: (transactionId: Int) -> Unit,
    val navigateUp: () -> Unit,
)

@Composable
internal fun ViewTransactionScreenUI(
    events: ViewTransactionScreenUIEvents,
    uiState: ViewTransactionScreenUIState,
    state: CommonScreenUIState,
) {
    BottomSheetHandler(
        showModalBottomSheet = uiState.viewTransactionBottomSheetType != ViewTransactionBottomSheetType.NONE,
        bottomSheetType = uiState.viewTransactionBottomSheetType,
        coroutineScope = state.coroutineScope,
        keyboardController = state.keyboardController,
        modalBottomSheetState = state.modalBottomSheetState,
        resetBottomSheetType = uiState.resetBottomSheetType,
    )

    MyScaffold(
        sheetState = state.modalBottomSheetState,
        sheetContent = {
            when (uiState.viewTransactionBottomSheetType) {
                ViewTransactionBottomSheetType.DELETE_CONFIRMATION -> {
                    TransactionDeleteConfirmationBottomSheetContent(
                        transactionIdToDelete = uiState.transactionIdToDelete,
                        resetBottomSheetType = uiState.resetBottomSheetType,
                        resetTransactionIdToDelete = {
                            uiState.setTransactionIdToDelete(null)
                        },
                        deleteTransaction = {
                            uiState.transactionIdToDelete?.let { transactionIdToDeleteValue ->
                                events.deleteTransaction(transactionIdToDeleteValue)
                            }
                        },
                    )
                }

                ViewTransactionBottomSheetType.NONE -> {
                    VerticalSpacer()
                }
            }
        },
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = R.string.screen_view_transaction_appbar_title,
                navigationAction = events.navigateUp,
            )
        },
        onClick = {
            state.focusManager.clearFocus()
        },
        backHandlerEnabled = uiState.viewTransactionBottomSheetType != ViewTransactionBottomSheetType.NONE,
        coroutineScope = state.coroutineScope,
        onBackPress = uiState.resetBottomSheetType,
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            AnimatedVisibility(
                visible = uiState.transactionListItemData.isNull(),
            ) {
                MyLinearProgressIndicator()
            }
            AnimatedVisibility(
                visible = uiState.transactionListItemData.isNotNull(),
            ) {
                uiState.transactionListItemData?.let { transactionListItemData ->
                    TransactionListItem(
                        modifier = Modifier
                            .padding(
                                top = 8.dp,
                                bottom = 8.dp,
                            ),
                        data = uiState.transactionListItemData,
                        events = TransactionListItemEvents(
                            onDeleteButtonClick = {
                                uiState.setTransactionIdToDelete(transactionListItemData.transactionId)
                                uiState.setViewTransactionBottomSheetType(
                                    ViewTransactionBottomSheetType.DELETE_CONFIRMATION
                                )
                            },
                            onEditButtonClick = {
                                events.navigateToEditTransactionScreen(transactionListItemData.transactionId)
                            },
                            onRefundButtonClick = {
                                events.navigateToAddTransactionScreen(transactionListItemData.transactionId)
                            },
                        ),
                    )
                }
            }
            AnimatedVisibility(
                visible = uiState.originalTransactionListItemData.isNotNull(),
            ) {
                uiState.originalTransactionListItemData?.let { transactionListItemData ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                    ) {
                        MyText(
                            modifier = Modifier
                                .padding(
                                    top = 16.dp,
                                    start = 16.dp,
                                )
                                .fillMaxWidth(),
                            text = "Original Transaction",
                            style = MaterialTheme.typography.headlineMedium
                                .copy(
                                    color = MaterialTheme.colorScheme.onBackground,
                                ),
                        )
                        TransactionListItem(
                            modifier = Modifier
                                .padding(
                                    top = 8.dp,
                                    bottom = 8.dp,
                                ),
                            data = transactionListItemData,
                            events = TransactionListItemEvents(
                                onDeleteButtonClick = {
                                    uiState.setTransactionIdToDelete(transactionListItemData.transactionId)
                                    uiState.setViewTransactionBottomSheetType(
                                        ViewTransactionBottomSheetType.DELETE_CONFIRMATION
                                    )
                                },
                                onEditButtonClick = {
                                    events.navigateToEditTransactionScreen(transactionListItemData.transactionId)
                                },
                                onRefundButtonClick = {
                                    events.navigateToAddTransactionScreen(transactionListItemData.transactionId)
                                },
                            ),
                        )
                    }
                }
            }
            AnimatedVisibility(
                visible = uiState.refundTransactionListItemData.isNotNull() &&
                        uiState.refundTransactionListItemData.isNotEmpty(),
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    uiState.refundTransactionListItemData?.let {
                        item {
                            MyText(
                                modifier = Modifier
                                    .padding(
                                        top = 16.dp,
                                        start = 16.dp,
                                    )
                                    .fillMaxWidth(),
                                text = "Refund Transactions",
                                style = MaterialTheme.typography.headlineMedium
                                    .copy(
                                        color = MaterialTheme.colorScheme.onBackground,
                                    ),
                            )
                        }
                        items(uiState.refundTransactionListItemData) { transactionListItemData ->
                            TransactionListItem(
                                modifier = Modifier
                                    .padding(
                                        top = 8.dp,
                                        bottom = 8.dp,
                                    ),
                                data = transactionListItemData,
                                events = TransactionListItemEvents(
                                    onDeleteButtonClick = {
                                        uiState.setTransactionIdToDelete(transactionListItemData.transactionId)
                                        uiState.setViewTransactionBottomSheetType(
                                            ViewTransactionBottomSheetType.DELETE_CONFIRMATION
                                        )
                                    },
                                    onEditButtonClick = {
                                        events.navigateToEditTransactionScreen(
                                            transactionListItemData.transactionId
                                        )
                                    },
                                    onRefundButtonClick = {},
                                ),
                            )
                        }
                    }
                }
            }
        }
    }
}

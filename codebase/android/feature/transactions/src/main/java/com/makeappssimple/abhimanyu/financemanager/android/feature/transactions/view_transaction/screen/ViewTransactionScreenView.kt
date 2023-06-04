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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyLinearProgressIndicator
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.BottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.BottomSheetHandler
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.transaction_list_item.TransactionListItem
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.transaction_list_item.TransactionListItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.transaction_list_item.TransactionListItemEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.common.TransactionDeleteConfirmationBottomSheetContent

private enum class ViewTransactionBottomSheetType : BottomSheetType {
    DELETE_CONFIRMATION,
    NONE,
}

@Immutable
internal data class ViewTransactionScreenViewData(
    val originalTransactionListItemData: TransactionListItemData?,
    val refundTransactionListItemData: List<TransactionListItemData>?,
    val transactionListItemData: TransactionListItemData?,
)

@Immutable
internal data class ViewTransactionScreenViewEvents(
    val deleteTransaction: (transactionId: Int) -> Unit,
    val navigateToAddTransactionScreen: (transactionId: Int) -> Unit,
    val navigateToEditTransactionScreen: (transactionId: Int) -> Unit,
    val navigateUp: () -> Unit,
)

@Composable
internal fun ViewTransactionScreenView(
    data: ViewTransactionScreenViewData,
    events: ViewTransactionScreenViewEvents,
    state: CommonScreenViewState,
) {
    var viewTransactionBottomSheetType by remember {
        mutableStateOf(
            value = ViewTransactionBottomSheetType.NONE,
        )
    }
    var transactionIdToDelete: Int? by remember {
        mutableStateOf(
            value = null,
        )
    }
    val resetBottomSheetType = {
        viewTransactionBottomSheetType = ViewTransactionBottomSheetType.NONE
    }

    BottomSheetHandler(
        showModalBottomSheet = viewTransactionBottomSheetType != ViewTransactionBottomSheetType.NONE,
        bottomSheetType = viewTransactionBottomSheetType,
        coroutineScope = state.coroutineScope,
        keyboardController = state.keyboardController,
        modalBottomSheetState = state.modalBottomSheetState,
        resetBottomSheetType = resetBottomSheetType,
    )

    MyScaffold(
        sheetState = state.modalBottomSheetState,
        sheetContent = {
            when (viewTransactionBottomSheetType) {
                ViewTransactionBottomSheetType.DELETE_CONFIRMATION -> {
                    TransactionDeleteConfirmationBottomSheetContent(
                        transactionIdToDelete = transactionIdToDelete,
                        resetBottomSheetType = resetBottomSheetType,
                        resetTransactionIdToDelete = {
                            transactionIdToDelete = null
                        },
                        deleteTransaction = {
                            transactionIdToDelete?.let { transactionIdToDeleteValue ->
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
        backHandlerEnabled = viewTransactionBottomSheetType != ViewTransactionBottomSheetType.NONE,
        coroutineScope = state.coroutineScope,
        onBackPress = resetBottomSheetType,
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            AnimatedVisibility(
                visible = data.transactionListItemData.isNull(),
            ) {
                MyLinearProgressIndicator()
            }
            AnimatedVisibility(
                visible = data.transactionListItemData.isNotNull(),
            ) {
                data.transactionListItemData?.let { transactionListItemData ->
                    TransactionListItem(
                        modifier = Modifier
                            .padding(
                                top = 8.dp,
                                bottom = 8.dp,
                            ),
                        data = data.transactionListItemData,
                        events = TransactionListItemEvents(
                            onDeleteButtonClick = {
                                transactionIdToDelete = transactionListItemData.transactionId
                                viewTransactionBottomSheetType =
                                    ViewTransactionBottomSheetType.DELETE_CONFIRMATION
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
                visible = data.originalTransactionListItemData.isNotNull(),
            ) {
                data.originalTransactionListItemData?.let { transactionListItemData ->
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
                                    transactionIdToDelete = transactionListItemData.transactionId
                                    viewTransactionBottomSheetType =
                                        ViewTransactionBottomSheetType.DELETE_CONFIRMATION
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
                visible = data.refundTransactionListItemData.isNotNull() &&
                        data.refundTransactionListItemData.isNotEmpty(),
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    data.refundTransactionListItemData?.let {
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
                        items(data.refundTransactionListItemData) { transactionListItemData ->
                            TransactionListItem(
                                modifier = Modifier
                                    .padding(
                                        top = 8.dp,
                                        bottom = 8.dp,
                                    ),
                                data = transactionListItemData,
                                events = TransactionListItemEvents(
                                    onDeleteButtonClick = {
                                        transactionIdToDelete =
                                            transactionListItemData.transactionId
                                        viewTransactionBottomSheetType =
                                            ViewTransactionBottomSheetType.DELETE_CONFIRMATION
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

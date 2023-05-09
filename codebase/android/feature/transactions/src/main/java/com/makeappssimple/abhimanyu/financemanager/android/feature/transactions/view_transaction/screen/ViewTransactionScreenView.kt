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
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.toggleModalBottomSheetState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.transaction_list_item.TransactionListItem
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.transaction_list_item.TransactionListItemData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.common.TransactionDeleteConfirmationBottomSheetContent

internal enum class ViewTransactionBottomSheetType : BottomSheetType {
    NONE,
    DELETE_CONFIRMATION,
}

@Immutable
internal data class ViewTransactionScreenViewData(
    val originalTransactionListItemData: TransactionListItemData?,
    val refundTransactionListItemData: List<TransactionListItemData>?,
    val transactionListItemData: TransactionListItemData?,
    val deleteTransaction: (transactionId: Int) -> Unit,
    val navigateToAddTransactionScreen: (transactionId: Int) -> Unit,
    val navigateToEditTransactionScreen: (transactionId: Int) -> Unit,
    val navigateUp: () -> Unit,
)

@Composable
internal fun ViewTransactionScreenView(
    data: ViewTransactionScreenViewData,
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

    MyScaffold(
        sheetState = state.modalBottomSheetState,
        sheetContent = {
            when (viewTransactionBottomSheetType) {
                ViewTransactionBottomSheetType.NONE -> {
                    VerticalSpacer()
                }

                ViewTransactionBottomSheetType.DELETE_CONFIRMATION -> {
                    TransactionDeleteConfirmationBottomSheetContent(
                        coroutineScope = state.coroutineScope,
                        modalBottomSheetState = state.modalBottomSheetState,
                        transactionIdToDelete = transactionIdToDelete,
                        resetBottomSheetType = {
                            viewTransactionBottomSheetType = ViewTransactionBottomSheetType.NONE
                        },
                        resetTransactionIdToDelete = {
                            transactionIdToDelete = null
                        },
                    ) {
                        transactionIdToDelete?.let { transactionIdToDeleteValue ->
                            data.deleteTransaction(transactionIdToDeleteValue)
                        }
                    }
                }
            }
        },
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = R.string.screen_view_transaction_appbar_title,
                navigationAction = data.navigateUp,
            )
        },
        onClick = {
            state.focusManager.clearFocus()
        },
        backHandlerEnabled = viewTransactionBottomSheetType != ViewTransactionBottomSheetType.NONE,
        coroutineScope = state.coroutineScope,
        onBackPress = {
            viewTransactionBottomSheetType = ViewTransactionBottomSheetType.NONE
        },
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
                        data = data.transactionListItemData.copy(
                            modifier = transactionListItemData.modifier.then(
                                Modifier.padding(
                                    top = 8.dp,
                                    bottom = 8.dp,
                                )
                            ),
                            onDeleteButtonClick = {
                                transactionIdToDelete = transactionListItemData.transactionId
                                viewTransactionBottomSheetType =
                                    ViewTransactionBottomSheetType.DELETE_CONFIRMATION
                                toggleModalBottomSheetState(
                                    coroutineScope = state.coroutineScope,
                                    modalBottomSheetState = state.modalBottomSheetState,
                                )
                            },
                            onEditButtonClick = {
                                data.navigateToEditTransactionScreen(transactionListItemData.transactionId)
                            },
                            onRefundButtonClick = {
                                data.navigateToAddTransactionScreen(transactionListItemData.transactionId)
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
                            data = transactionListItemData.copy(
                                modifier = transactionListItemData.modifier.then(
                                    Modifier.padding(
                                        top = 8.dp,
                                        bottom = 8.dp,
                                    )
                                ),
                                onDeleteButtonClick = {
                                    transactionIdToDelete = transactionListItemData.transactionId
                                    viewTransactionBottomSheetType =
                                        ViewTransactionBottomSheetType.DELETE_CONFIRMATION
                                    toggleModalBottomSheetState(
                                        coroutineScope = state.coroutineScope,
                                        modalBottomSheetState = state.modalBottomSheetState,
                                    )
                                },
                                onEditButtonClick = {
                                    data.navigateToEditTransactionScreen(transactionListItemData.transactionId)
                                },
                                onRefundButtonClick = {
                                    data.navigateToAddTransactionScreen(transactionListItemData.transactionId)
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
                                data = transactionListItemData.copy(
                                    modifier = transactionListItemData.modifier.then(
                                        Modifier.padding(
                                            top = 8.dp,
                                            bottom = 8.dp,
                                        )
                                    ),
                                    onDeleteButtonClick = {
                                        transactionIdToDelete =
                                            transactionListItemData.transactionId
                                        viewTransactionBottomSheetType =
                                            ViewTransactionBottomSheetType.DELETE_CONFIRMATION
                                        toggleModalBottomSheetState(
                                            coroutineScope = state.coroutineScope,
                                            modalBottomSheetState = state.modalBottomSheetState,
                                        )
                                    },
                                    onEditButtonClick = {
                                        data.navigateToEditTransactionScreen(transactionListItemData.transactionId)
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

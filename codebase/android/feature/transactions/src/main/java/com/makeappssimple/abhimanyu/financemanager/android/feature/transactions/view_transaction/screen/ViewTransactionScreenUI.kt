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
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.NavigationBarsAndImeSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.navigationBarLandscapeSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.BottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.BottomSheetHandler
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.transaction_list_item.TransactionListItem
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.transaction_list_item.TransactionListItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.transaction_list_item.TransactionListItemEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.common.TransactionDeleteConfirmationBottomSheet

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
sealed class ViewTransactionScreenUIEvent {
    object NavigateUp : ViewTransactionScreenUIEvent()

    data class DeleteTransaction(
        val transactionId: Int,
    ) : ViewTransactionScreenUIEvent()

    data class NavigateToAddTransactionScreen(
        val transactionId: Int,
    ) : ViewTransactionScreenUIEvent()

    data class NavigateToEditTransactionScreen(
        val transactionId: Int,
    ) : ViewTransactionScreenUIEvent()

    data class NavigateToViewTransactionScreen(
        val transactionId: Int,
    ) : ViewTransactionScreenUIEvent()
}

@Composable
internal fun ViewTransactionScreenUI(
    uiState: ViewTransactionScreenUIState,
    state: CommonScreenUIState,
    handleUIEvents: (uiEvent: ViewTransactionScreenUIEvent) -> Unit,
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
        modifier = Modifier
            .fillMaxSize(),
        sheetContent = {
            when (uiState.viewTransactionBottomSheetType) {
                ViewTransactionBottomSheetType.DELETE_CONFIRMATION -> {
                    TransactionDeleteConfirmationBottomSheet(
                        transactionIdToDelete = uiState.transactionIdToDelete,
                        resetBottomSheetType = uiState.resetBottomSheetType,
                        resetTransactionIdToDelete = {
                            uiState.setTransactionIdToDelete(null)
                        },
                        deleteTransaction = {
                            uiState.transactionIdToDelete?.let { transactionIdToDeleteValue ->
                                handleUIEvents(
                                    ViewTransactionScreenUIEvent.DeleteTransaction(
                                        transactionId = transactionIdToDeleteValue,
                                    )
                                )
                            }
                        },
                    )
                }

                ViewTransactionBottomSheetType.NONE -> {
                    VerticalSpacer()
                }
            }
        },
        sheetState = state.modalBottomSheetState,
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = R.string.screen_view_transaction_appbar_title,
                navigationAction = {
                    handleUIEvents(ViewTransactionScreenUIEvent.NavigateUp)
                },
            )
        },
        onClick = {
            state.focusManager.clearFocus()
        },
        isModalBottomSheetVisible = uiState.viewTransactionBottomSheetType != ViewTransactionBottomSheetType.NONE,
        backHandlerEnabled = uiState.viewTransactionBottomSheetType != ViewTransactionBottomSheetType.NONE,
        coroutineScope = state.coroutineScope,
        onBackPress = uiState.resetBottomSheetType,
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarLandscapeSpacer(),
        ) {
            item {
                AnimatedVisibility(
                    visible = uiState.transactionListItemData.isNull(),
                ) {
                    MyLinearProgressIndicator()
                }
            }
            item {
                if (uiState.transactionListItemData.isNotNull()) {
                    TransactionListItem(
                        modifier = Modifier
                            .padding(
                                top = 8.dp,
                                bottom = 8.dp,
                            ),
                        data = uiState.transactionListItemData,
                        events = TransactionListItemEvents(
                            onDeleteButtonClick = {
                                uiState.setTransactionIdToDelete(uiState.transactionListItemData.transactionId)
                                uiState.setViewTransactionBottomSheetType(
                                    ViewTransactionBottomSheetType.DELETE_CONFIRMATION
                                )
                            },
                            onEditButtonClick = {
                                handleUIEvents(
                                    ViewTransactionScreenUIEvent.NavigateToEditTransactionScreen(
                                        transactionId = uiState.transactionListItemData.transactionId,
                                    )
                                )
                            },
                            onRefundButtonClick = {
                                handleUIEvents(
                                    ViewTransactionScreenUIEvent.NavigateToAddTransactionScreen(
                                        transactionId = uiState.transactionListItemData.transactionId,
                                    )
                                )
                            },
                        ),
                    )
                }
            }
            item {
                if (uiState.originalTransactionListItemData.isNotNull()) {
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
                            data = uiState.originalTransactionListItemData,
                            events = TransactionListItemEvents(
                                onClick = {
                                    handleUIEvents(
                                        ViewTransactionScreenUIEvent.NavigateToViewTransactionScreen(
                                            transactionId = uiState.originalTransactionListItemData.transactionId,
                                        )
                                    )
                                },
                                onDeleteButtonClick = {
                                    uiState.setTransactionIdToDelete(uiState.originalTransactionListItemData.transactionId)
                                    uiState.setViewTransactionBottomSheetType(
                                        ViewTransactionBottomSheetType.DELETE_CONFIRMATION
                                    )
                                },
                                onEditButtonClick = {
                                    handleUIEvents(
                                        ViewTransactionScreenUIEvent.NavigateToEditTransactionScreen(
                                            transactionId = uiState.originalTransactionListItemData.transactionId,
                                        )
                                    )
                                },
                                onRefundButtonClick = {
                                    handleUIEvents(
                                        ViewTransactionScreenUIEvent.NavigateToAddTransactionScreen(
                                            transactionId = uiState.originalTransactionListItemData.transactionId,
                                        )
                                    )
                                },
                            ),
                        )
                    }
                }
            }
            if (uiState.refundTransactionListItemData.isNotNull() &&
                uiState.refundTransactionListItemData.isNotEmpty()
            ) {
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
                items(
                    items = uiState.refundTransactionListItemData,
                    key = { listItem ->
                        listItem.hashCode()
                    },
                ) { transactionListItemData ->
                    TransactionListItem(
                        modifier = Modifier
                            .padding(
                                top = 8.dp,
                                bottom = 8.dp,
                            ),
                        data = transactionListItemData,
                        events = TransactionListItemEvents(
                            onClick = {
                                handleUIEvents(
                                    ViewTransactionScreenUIEvent.NavigateToViewTransactionScreen(
                                        transactionId = transactionListItemData.transactionId,
                                    )
                                )
                            },
                            onDeleteButtonClick = {
                                uiState.setTransactionIdToDelete(transactionListItemData.transactionId)
                                uiState.setViewTransactionBottomSheetType(
                                    ViewTransactionBottomSheetType.DELETE_CONFIRMATION
                                )
                            },
                            onEditButtonClick = {
                                handleUIEvents(
                                    ViewTransactionScreenUIEvent.NavigateToEditTransactionScreen(
                                        transactionId = transactionListItemData.transactionId,
                                    )
                                )
                            },
                            onRefundButtonClick = {},
                        ),
                    )
                }
            }
            item {
                NavigationBarsAndImeSpacer()
            }
        }
    }
}

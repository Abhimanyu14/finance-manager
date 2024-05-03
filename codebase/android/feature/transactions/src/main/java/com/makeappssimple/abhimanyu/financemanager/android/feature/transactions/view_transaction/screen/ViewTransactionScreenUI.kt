package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_CONTENT_VIEW_TRANSACTION
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_VIEW_TRANSACTION
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyLinearProgressIndicator
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.NavigationBarsAndImeSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.navigationBarLandscapeSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.BottomSheetHandler
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.transactions.TransactionDeleteConfirmationBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transaction.TransactionListItem
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transaction.TransactionListItemEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.top_app_bar.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.view_transaction_section_header.ViewTransactionSectionHeader
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.R

@Composable
internal fun ViewTransactionScreenUI(
    uiState: ViewTransactionScreenUIState,
    state: CommonScreenUIState = rememberCommonScreenUIState(),
    handleUIEvents: (uiEvent: ViewTransactionScreenUIEvent) -> Unit = {},
) {
    BottomSheetHandler(
        showModalBottomSheet = uiState.screenBottomSheetType != ViewTransactionScreenBottomSheetType.None,
        screenBottomSheetType = uiState.screenBottomSheetType,
        coroutineScope = state.coroutineScope,
        keyboardController = state.keyboardController,
        modalBottomSheetState = state.modalBottomSheetState,
        resetBottomSheetType = uiState.resetScreenBottomSheetType,
    )

    MyScaffold(
        modifier = Modifier
            .testTag(
                tag = SCREEN_VIEW_TRANSACTION,
            )
            .fillMaxSize(),
        sheetContent = {
            when (uiState.screenBottomSheetType) {
                is ViewTransactionScreenBottomSheetType.DeleteConfirmation -> {
                    TransactionDeleteConfirmationBottomSheet(
                        transactionIdToDelete = uiState.transactionIdToDelete,
                        resetBottomSheetType = uiState.resetScreenBottomSheetType,
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

                is ViewTransactionScreenBottomSheetType.None -> {
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
        onClick = state.focusManager::clearFocus,
        isModalBottomSheetVisible = uiState.screenBottomSheetType != ViewTransactionScreenBottomSheetType.None,
        backHandlerEnabled = uiState.screenBottomSheetType != ViewTransactionScreenBottomSheetType.None,
        coroutineScope = state.coroutineScope,
        onBackPress = uiState.resetScreenBottomSheetType,
    ) {
        LazyColumn(
            modifier = Modifier
                .testTag(
                    tag = SCREEN_CONTENT_VIEW_TRANSACTION,
                )
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
            if (uiState.transactionListItemData.isNotNull()) {
                item {
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
                                uiState.setScreenBottomSheetType(
                                    ViewTransactionScreenBottomSheetType.DeleteConfirmation
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
            if (uiState.originalTransactionListItemData.isNotNull()) {
                item {
                    ViewTransactionSectionHeader(
                        textStringResourceId = R.string.screen_view_transaction_original_transaction,
                    )
                }
                item {
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
                                uiState.setScreenBottomSheetType(
                                    ViewTransactionScreenBottomSheetType.DeleteConfirmation
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
            if (uiState.refundTransactionListItemData.isNotNull() &&
                uiState.refundTransactionListItemData.isNotEmpty()
            ) {
                item {
                    ViewTransactionSectionHeader(
                        textStringResourceId = R.string.screen_view_transaction_refund_transactions,
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
                                uiState.setScreenBottomSheetType(
                                    ViewTransactionScreenBottomSheetType.DeleteConfirmation
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

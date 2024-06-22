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
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.state.CommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.state.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.transactions.TransactionDeleteConfirmationBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.transactions.TransactionDeleteConfirmationBottomSheetEvent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transaction.TransactionListItem
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transaction.TransactionListItemEvent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.top_app_bar.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.view_transaction_section_header.ViewTransactionSectionHeader
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.view_transaction_section_header.ViewTransactionSectionHeaderData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.bottomsheet.ViewTransactionScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.event.ViewTransactionScreenUIEvent
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.state.ViewTransactionScreenUIState

@Composable
internal fun ViewTransactionScreenUI(
    uiState: ViewTransactionScreenUIState,
    state: CommonScreenUIState = rememberCommonScreenUIState(),
    handleUIEvent: (uiEvent: ViewTransactionScreenUIEvent) -> Unit = {},
) {
    BottomSheetHandler(
        isBottomSheetVisible = uiState.isBottomSheetVisible,
        screenBottomSheetType = uiState.screenBottomSheetType,
        coroutineScope = state.coroutineScope,
        modalBottomSheetState = state.modalBottomSheetState,
        keyboardController = state.keyboardController,
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
                        handleEvent = { event ->
                            when (event) {
                                TransactionDeleteConfirmationBottomSheetEvent.OnNegativeButtonClick -> {
                                    handleUIEvent(ViewTransactionScreenUIEvent.OnTransactionDeleteConfirmationBottomSheet.NegativeButtonClick)
                                }

                                TransactionDeleteConfirmationBottomSheetEvent.OnPositiveButtonClick -> {
                                    handleUIEvent(ViewTransactionScreenUIEvent.OnTransactionDeleteConfirmationBottomSheet.PositiveButtonClick)
                                }
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
                    handleUIEvent(ViewTransactionScreenUIEvent.OnTopAppBarNavigationButtonClick)
                },
            )
        },
        onClick = state.focusManager::clearFocus,
        isModalBottomSheetVisible = uiState.isBottomSheetVisible,
        isBackHandlerEnabled = uiState.screenBottomSheetType != ViewTransactionScreenBottomSheetType.None,
        coroutineScope = state.coroutineScope,
        onNavigationBackButtonClick = {
            handleUIEvent(ViewTransactionScreenUIEvent.OnNavigationBackButtonClick)
        },
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
                        handleEvent = { event ->
                            when (event) {
                                is TransactionListItemEvent.OnClick -> {}

                                is TransactionListItemEvent.OnDeleteButtonClick -> {
                                    handleUIEvent(
                                        ViewTransactionScreenUIEvent.OnTransactionListItem.DeleteButtonClick(
                                            transactionId = uiState.transactionListItemData.transactionId,
                                        )
                                    )
                                }

                                is TransactionListItemEvent.OnEditButtonClick -> {
                                    handleUIEvent(
                                        ViewTransactionScreenUIEvent.OnTransactionListItem.EditButtonClick(
                                            transactionId = uiState.transactionListItemData.transactionId,
                                        )
                                    )
                                }

                                is TransactionListItemEvent.OnLongClick -> {}

                                is TransactionListItemEvent.OnRefundButtonClick -> {
                                    handleUIEvent(
                                        ViewTransactionScreenUIEvent.OnTransactionListItem.RefundButtonClick(
                                            transactionId = uiState.transactionListItemData.transactionId,
                                        )
                                    )
                                }
                            }
                        },
                    )
                }
            }
            if (uiState.originalTransactionListItemData.isNotNull()) {
                item {
                    ViewTransactionSectionHeader(
                        data = ViewTransactionSectionHeaderData(
                            textStringResourceId = R.string.screen_view_transaction_original_transaction,
                        ),
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
                        handleEvent = { event ->
                            when (event) {
                                is TransactionListItemEvent.OnClick -> {
                                    handleUIEvent(
                                        ViewTransactionScreenUIEvent.OnTransactionListItem.Click(
                                            transactionId = uiState.originalTransactionListItemData.transactionId,
                                        )
                                    )
                                }

                                is TransactionListItemEvent.OnDeleteButtonClick -> {
                                    handleUIEvent(
                                        ViewTransactionScreenUIEvent.OnTransactionListItem.DeleteButtonClick(
                                            transactionId = uiState.originalTransactionListItemData.transactionId,
                                        )
                                    )
                                }

                                is TransactionListItemEvent.OnEditButtonClick -> {
                                    handleUIEvent(
                                        ViewTransactionScreenUIEvent.OnTransactionListItem.EditButtonClick(
                                            transactionId = uiState.originalTransactionListItemData.transactionId,
                                        )
                                    )
                                }

                                is TransactionListItemEvent.OnLongClick -> {}

                                is TransactionListItemEvent.OnRefundButtonClick -> {
                                    handleUIEvent(
                                        ViewTransactionScreenUIEvent.OnTransactionListItem.RefundButtonClick(
                                            transactionId = uiState.originalTransactionListItemData.transactionId,
                                        )
                                    )
                                }
                            }
                        },
                    )
                }
            }
            if (uiState.refundTransactionListItemData.isNotNull() &&
                uiState.refundTransactionListItemData.isNotEmpty()
            ) {
                item {
                    ViewTransactionSectionHeader(
                        data = ViewTransactionSectionHeaderData(
                            textStringResourceId = R.string.screen_view_transaction_refund_transactions,
                        )
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
                        handleEvent = { event ->
                            when (event) {
                                is TransactionListItemEvent.OnClick -> {
                                    handleUIEvent(
                                        ViewTransactionScreenUIEvent.OnTransactionListItem.Click(
                                            transactionId = transactionListItemData.transactionId,
                                        )
                                    )
                                }

                                is TransactionListItemEvent.OnDeleteButtonClick -> {
                                    handleUIEvent(
                                        ViewTransactionScreenUIEvent.OnTransactionListItem.DeleteButtonClick(
                                            transactionId = transactionListItemData.transactionId,
                                        )
                                    )
                                }

                                is TransactionListItemEvent.OnEditButtonClick -> {
                                    handleUIEvent(
                                        ViewTransactionScreenUIEvent.OnTransactionListItem.EditButtonClick(
                                            transactionId = transactionListItemData.transactionId,
                                        )
                                    )
                                }

                                is TransactionListItemEvent.OnLongClick -> {}

                                is TransactionListItemEvent.OnRefundButtonClick -> {}
                            }
                        },
                    )
                }
            }
            item {
                NavigationBarsAndImeSpacer()
            }
        }
    }
}

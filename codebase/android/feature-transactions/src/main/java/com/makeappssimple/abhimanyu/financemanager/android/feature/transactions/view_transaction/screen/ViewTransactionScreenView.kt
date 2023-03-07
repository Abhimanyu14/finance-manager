package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.getReadableDateAndTimeString
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyLinearProgressIndicator
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.util.navigateToAddTransactionScreen
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.util.navigateToEditTransactionScreen
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.util.navigateUp
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.BottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.BottomSheetBackHandler
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.toggleModalBottomSheetState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.adjustmentEmoji
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.transaction_list_item.TransactionListItem
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.transferEmoji
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.getAmountTextColor
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.common.TransactionDeleteConfirmationBottomSheetContent

internal enum class ViewTransactionBottomSheetType : BottomSheetType {
    NONE,
    DELETE_CONFIRMATION,
}

@Immutable
internal data class ViewTransactionScreenViewData(
    val navigationManager: NavigationManager,
    val transactionData: TransactionData?,
    val deleteTransaction: (transactionId: Int) -> Unit,
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

    val transaction = data.transactionData?.transaction

    val isDeleteButtonEnabled = transaction?.refundTransactionIds?.run {
        this.isEmpty()
    } ?: true
    val isEditButtonVisible = transaction?.transactionType != TransactionType.ADJUSTMENT
    val isRefundButtonVisible = transaction?.transactionType == TransactionType.EXPENSE

    val amountColor: Color =
        transaction?.getAmountTextColor() ?: MaterialTheme.colorScheme.onBackground
    val amountText: String = if (
        transaction?.transactionType == TransactionType.INCOME ||
        transaction?.transactionType == TransactionType.EXPENSE ||
        transaction?.transactionType == TransactionType.ADJUSTMENT ||
        transaction?.transactionType == TransactionType.REFUND
    ) {
        data.transactionData.transaction.amount.toSignedString(
            isPositive = data.transactionData.sourceTo != null,
            isNegative = data.transactionData.sourceFrom != null,
        )
    } else {
        transaction?.amount.toString()
    }
    val dateAndTimeText: String = getReadableDateAndTimeString(
        timestamp = transaction?.transactionTimestamp ?: 0L,
    )
    val emoji: String = when (transaction?.transactionType) {
        TransactionType.TRANSFER -> {
            transferEmoji
        }

        TransactionType.ADJUSTMENT -> {
            adjustmentEmoji
        }

        else -> {
            data.transactionData?.category?.emoji.orEmpty()
        }
    }
    val sourceFromName = data.transactionData?.sourceFrom?.name
    val sourceToName = data.transactionData?.sourceTo?.name
    val title: String = transaction?.title.orEmpty()
    val transactionForText: String =
        data.transactionData?.transactionFor?.titleToDisplay.orEmpty()

    BottomSheetBackHandler(
        enabled = viewTransactionBottomSheetType != ViewTransactionBottomSheetType.NONE,
        coroutineScope = state.coroutineScope,
        modalBottomSheetState = state.modalBottomSheetState,
    ) {
        viewTransactionBottomSheetType = ViewTransactionBottomSheetType.NONE
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
                navigationAction = {
                    navigateUp(
                        navigationManager = data.navigationManager,
                    )
                },
            )
        },
        onClick = {
            state.focusManager.clearFocus()
        },
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            AnimatedVisibility(
                visible = data.transactionData == null,
            ) {
                MyLinearProgressIndicator()
            }
            AnimatedVisibility(
                visible = data.transactionData != null,
            ) {
                TransactionListItem(
                    isDeleteButtonEnabled = isDeleteButtonEnabled,
                    isDeleteButtonVisible = true,
                    isEditButtonVisible = isEditButtonVisible,
                    isExpanded = true,
                    isRefundButtonVisible = isRefundButtonVisible,
                    amountColor = amountColor,
                    amountText = amountText,
                    dateAndTimeText = dateAndTimeText,
                    emoji = emoji,
                    sourceFromName = sourceFromName,
                    sourceToName = sourceToName,
                    title = title,
                    transactionForText = transactionForText,
                    onClick = null,
                    onDeleteButtonClick = {
                        transactionIdToDelete = transaction?.id
                        viewTransactionBottomSheetType =
                            ViewTransactionBottomSheetType.DELETE_CONFIRMATION
                        toggleModalBottomSheetState(
                            coroutineScope = state.coroutineScope,
                            modalBottomSheetState = state.modalBottomSheetState,
                        )
                    },
                    onEditButtonClick = {
                        transaction?.id?.let { transactionId ->
                            navigateToEditTransactionScreen(
                                navigationManager = data.navigationManager,
                                transactionId = transactionId,
                            )
                        }
                    },
                    onRefundButtonClick = {
                        transaction?.id?.let { transactionId ->
                            navigateToAddTransactionScreen(
                                navigationManager = data.navigationManager,
                                transactionId = transactionId,
                            )
                        }
                    },
                )
            }
        }
    }
}

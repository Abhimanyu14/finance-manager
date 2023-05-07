package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyLinearProgressIndicator
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
    val transactionListItemData: TransactionListItemData?,
    val transactionData: TransactionData?,
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

    val transaction = data.transactionData?.transaction

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
                visible = data.transactionData.isNull(),
            ) {
                MyLinearProgressIndicator()
            }
            AnimatedVisibility(
                visible = data.transactionData.isNotNull(),
            ) {
                data.transactionListItemData?.let {
                    TransactionListItem(
                        data = data.transactionListItemData.copy(
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
                                    data.navigateToEditTransactionScreen(transactionId)
                                }
                            },
                            onRefundButtonClick = {
                                transaction?.id?.let { transactionId ->
                                    data.navigateToAddTransactionScreen(transactionId)
                                }
                            },
                        ),
                    )
                }
            }
        }
    }
}

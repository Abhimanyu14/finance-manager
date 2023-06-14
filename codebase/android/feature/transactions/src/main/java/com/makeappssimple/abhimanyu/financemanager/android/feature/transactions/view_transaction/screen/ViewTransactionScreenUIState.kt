package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.transaction_list_item.TransactionListItemData

@Stable
class ViewTransactionScreenUIState(
    data: ViewTransactionScreenUIData,
    val transactionIdToDelete: Int?,
    val viewTransactionBottomSheetType: ViewTransactionBottomSheetType,
    val setTransactionIdToDelete: (Int?) -> Unit,
    val setViewTransactionBottomSheetType: (ViewTransactionBottomSheetType) -> Unit,
) {
    val originalTransactionListItemData: TransactionListItemData? =
        data.originalTransactionListItemData
    val refundTransactionListItemData: List<TransactionListItemData>? =
        data.refundTransactionListItemData
    val transactionListItemData: TransactionListItemData? = data.transactionListItemData
    val resetBottomSheetType: () -> Unit = {
        setViewTransactionBottomSheetType(ViewTransactionBottomSheetType.NONE)
    }
}

@Composable
fun rememberViewTransactionScreenUIState(
    data: ViewTransactionScreenUIData,
): ViewTransactionScreenUIState {
    val (viewTransactionBottomSheetType, setViewTransactionBottomSheetType) = remember {
        mutableStateOf(
            value = ViewTransactionBottomSheetType.NONE,
        )
    }
    var transactionIdToDelete: Int? by remember {
        mutableStateOf(
            value = null,
        )
    }
    val setTransactionIdToDelete = { updatedTransactionIdToDelete: Int? ->
        transactionIdToDelete = updatedTransactionIdToDelete
    }

    return remember(
        data,
        transactionIdToDelete,
        viewTransactionBottomSheetType,
        setTransactionIdToDelete,
        setViewTransactionBottomSheetType,
    ) {
        ViewTransactionScreenUIState(
            data = data,
            transactionIdToDelete = transactionIdToDelete,
            viewTransactionBottomSheetType = viewTransactionBottomSheetType,
            setTransactionIdToDelete = setTransactionIdToDelete,
            setViewTransactionBottomSheetType = setViewTransactionBottomSheetType,
        )
    }
}

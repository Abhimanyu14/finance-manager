package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transaction.TransactionListItemData

@Stable
internal class ViewTransactionScreenUIState(
    val isBottomSheetVisible: Boolean,
    val transactionIdToDelete: Int?,
    val refundTransactionListItemData: List<TransactionListItemData>?,
    val originalTransactionListItemData: TransactionListItemData?,
    val transactionListItemData: TransactionListItemData?,
    val screenBottomSheetType: ViewTransactionScreenBottomSheetType,
    val resetScreenBottomSheetType: () -> Unit,
    val setScreenBottomSheetType: (ViewTransactionScreenBottomSheetType) -> Unit,
    val setTransactionIdToDelete: (Int?) -> Unit,
) : ScreenUIState

@Composable
internal fun rememberViewTransactionScreenUIState(
    data: MyResult<ViewTransactionScreenUIData>?,
): ViewTransactionScreenUIState {
    var transactionIdToDelete: Int? by remember {
        mutableStateOf(
            value = null,
        )
    }
    val setTransactionIdToDelete = { updatedTransactionIdToDelete: Int? ->
        transactionIdToDelete = updatedTransactionIdToDelete
    }
    var screenBottomSheetType: ViewTransactionScreenBottomSheetType by remember {
        mutableStateOf(
            value = ViewTransactionScreenBottomSheetType.None,
        )
    }
    val setScreenBottomSheetType =
        { updatedViewTransactionScreenBottomSheetType: ViewTransactionScreenBottomSheetType ->
            screenBottomSheetType = updatedViewTransactionScreenBottomSheetType
        }

    return remember(
        data,
        transactionIdToDelete,
        screenBottomSheetType,
        setTransactionIdToDelete,
        setScreenBottomSheetType,
    ) {
        val unwrappedData: ViewTransactionScreenUIData? = when (data) {
            is MyResult.Success -> {
                data.data
            }

            else -> {
                null
            }
        }

        // TODO(Abhi): Can be reordered to match the class ordering
        ViewTransactionScreenUIState(
            isBottomSheetVisible = screenBottomSheetType != ViewTransactionScreenBottomSheetType.None,
            transactionIdToDelete = transactionIdToDelete,
            refundTransactionListItemData = unwrappedData?.refundTransactionListItemData,
            originalTransactionListItemData = unwrappedData?.originalTransactionListItemData,
            transactionListItemData = unwrappedData?.transactionListItemData,
            screenBottomSheetType = screenBottomSheetType,
            resetScreenBottomSheetType = {
                setScreenBottomSheetType(ViewTransactionScreenBottomSheetType.None)
            },
            setScreenBottomSheetType = setScreenBottomSheetType,
            setTransactionIdToDelete = setTransactionIdToDelete,
        )
    }
}

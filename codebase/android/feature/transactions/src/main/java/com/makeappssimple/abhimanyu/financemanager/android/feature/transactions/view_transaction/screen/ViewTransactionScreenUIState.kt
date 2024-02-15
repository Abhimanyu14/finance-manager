package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.TransactionListItemData

@Stable
class ViewTransactionScreenUIState(
    data: MyResult<ViewTransactionScreenUIData>?,
    private val unwrappedData: ViewTransactionScreenUIData? = when (data) {
        is MyResult.Success -> {
            data.data
        }

        else -> {
            null
        }
    },
    val transactionIdToDelete: Int?,
    val screenBottomSheetType: ViewTransactionScreenBottomSheetType,
    val setTransactionIdToDelete: (Int?) -> Unit,
    val setScreenBottomSheetType: (ViewTransactionScreenBottomSheetType) -> Unit,
    val isLoading: Boolean = unwrappedData.isNull(),
    val originalTransactionListItemData: TransactionListItemData? =
        unwrappedData?.originalTransactionListItemData,
    val refundTransactionListItemData: List<TransactionListItemData>? =
        unwrappedData?.refundTransactionListItemData,
    val transactionListItemData: TransactionListItemData? = unwrappedData?.transactionListItemData,
    val resetScreenBottomSheetType: () -> Unit = {
        setScreenBottomSheetType(ViewTransactionScreenBottomSheetType.NONE)
    },
) : ScreenUIState

@Composable
fun rememberViewTransactionScreenUIState(
    data: MyResult<ViewTransactionScreenUIData>?,
): ViewTransactionScreenUIState {
    val (screenBottomSheetType, setScreenBottomSheetType) = remember {
        mutableStateOf(
            value = ViewTransactionScreenBottomSheetType.NONE,
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
        screenBottomSheetType,
        setTransactionIdToDelete,
        setScreenBottomSheetType,
    ) {
        ViewTransactionScreenUIState(
            data = data,
            transactionIdToDelete = transactionIdToDelete,
            screenBottomSheetType = screenBottomSheetType,
            setTransactionIdToDelete = setTransactionIdToDelete,
            setScreenBottomSheetType = setScreenBottomSheetType,
        )
    }
}

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
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transaction.TransactionListItemData

@Stable
public class ViewTransactionScreenUIState(
    public val isLoading: Boolean,
    public val transactionIdToDelete: Int?,
    public val refundTransactionListItemData: List<TransactionListItemData>?,
    public val originalTransactionListItemData: TransactionListItemData?,
    public val transactionListItemData: TransactionListItemData?,
    public val screenBottomSheetType: ViewTransactionScreenBottomSheetType,
    public val resetScreenBottomSheetType: () -> Unit,
    public val setScreenBottomSheetType: (ViewTransactionScreenBottomSheetType) -> Unit,
    public val setTransactionIdToDelete: (Int?) -> Unit,
) : ScreenUIState

@Composable
public fun rememberViewTransactionScreenUIState(
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
            transactionIdToDelete = transactionIdToDelete,
            screenBottomSheetType = screenBottomSheetType,
            setTransactionIdToDelete = setTransactionIdToDelete,
            setScreenBottomSheetType = setScreenBottomSheetType,
            isLoading = unwrappedData.isNull(),
            originalTransactionListItemData = unwrappedData?.originalTransactionListItemData,
            refundTransactionListItemData = unwrappedData?.refundTransactionListItemData,
            transactionListItemData = unwrappedData?.transactionListItemData,
            resetScreenBottomSheetType = {
                setScreenBottomSheetType(ViewTransactionScreenBottomSheetType.None)
            },
        )
    }
}

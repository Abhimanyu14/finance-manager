package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateAndEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transaction.TransactionListItemData

@Stable
internal class ViewTransactionScreenUIStateAndEvents(
    val state: ViewTransactionScreenUIState,
    val events: ViewTransactionScreenUIStateEvents,
) : ScreenUIStateAndEvents

@Composable
internal fun rememberViewTransactionScreenUIStateAndEvents(
    originalTransactionListItemData: TransactionListItemData?,
    refundTransactionListItemData: List<TransactionListItemData>,
    transactionListItemData: TransactionListItemData?,
): ViewTransactionScreenUIStateAndEvents {
    // region transaction id to delete
    var transactionIdToDelete: Int? by remember {
        mutableStateOf(
            value = null,
        )
    }
    val setTransactionIdToDelete = { updatedTransactionIdToDelete: Int? ->
        transactionIdToDelete = updatedTransactionIdToDelete
    }
    // endregion

    // region screen bottom sheet type
    var screenBottomSheetType: ViewTransactionScreenBottomSheetType by remember {
        mutableStateOf(
            value = ViewTransactionScreenBottomSheetType.None,
        )
    }
    val setScreenBottomSheetType =
        { updatedViewTransactionScreenBottomSheetType: ViewTransactionScreenBottomSheetType ->
            screenBottomSheetType = updatedViewTransactionScreenBottomSheetType
        }
    // endregion

    return remember(
        transactionIdToDelete,
        setTransactionIdToDelete,
        screenBottomSheetType,
        setScreenBottomSheetType,
        originalTransactionListItemData,
        refundTransactionListItemData,
        transactionListItemData,
    ) {
        ViewTransactionScreenUIStateAndEvents(
            state = ViewTransactionScreenUIState(
                isBottomSheetVisible = screenBottomSheetType != ViewTransactionScreenBottomSheetType.None,
                transactionIdToDelete = transactionIdToDelete,
                refundTransactionListItemData = refundTransactionListItemData,
                originalTransactionListItemData = originalTransactionListItemData,
                transactionListItemData = transactionListItemData,
                screenBottomSheetType = screenBottomSheetType,
            ),
            events = ViewTransactionScreenUIStateEvents(
                resetScreenBottomSheetType = {
                    setScreenBottomSheetType(ViewTransactionScreenBottomSheetType.None)
                },
                setScreenBottomSheetType = setScreenBottomSheetType,
                setTransactionIdToDelete = setTransactionIdToDelete,
            ),
        )
    }
}

package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateAndStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transaction.TransactionListItemData
import kotlinx.collections.immutable.ImmutableList

@Stable
internal class ViewTransactionScreenUIStateAndStateEvents(
    val state: ViewTransactionScreenUIState,
    val events: ViewTransactionScreenUIStateEvents,
) : ScreenUIStateAndStateEvents

@Composable
internal fun rememberViewTransactionScreenUIStateAndEvents(
    currentTransactionListItemData: TransactionListItemData?,
    originalTransactionListItemData: TransactionListItemData?,
    refundTransactionListItemData: ImmutableList<TransactionListItemData>,
): ViewTransactionScreenUIStateAndStateEvents {
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
    val resetScreenBottomSheetType = {
        setScreenBottomSheetType(ViewTransactionScreenBottomSheetType.None)
    }
    // endregion

    return remember(
        transactionIdToDelete,
        setTransactionIdToDelete,
        screenBottomSheetType,
        setScreenBottomSheetType,
        originalTransactionListItemData,
        refundTransactionListItemData,
        currentTransactionListItemData,
    ) {
        ViewTransactionScreenUIStateAndStateEvents(
            state = ViewTransactionScreenUIState(
                isBottomSheetVisible = screenBottomSheetType != ViewTransactionScreenBottomSheetType.None,
                transactionIdToDelete = transactionIdToDelete,
                refundTransactionListItemData = refundTransactionListItemData,
                originalTransactionListItemData = originalTransactionListItemData,
                transactionListItemData = currentTransactionListItemData,
                screenBottomSheetType = screenBottomSheetType,
            ),
            events = ViewTransactionScreenUIStateEvents(
                resetScreenBottomSheetType = resetScreenBottomSheetType,
                setScreenBottomSheetType = setScreenBottomSheetType,
                setTransactionIdToDelete = setTransactionIdToDelete,
            ),
        )
    }
}

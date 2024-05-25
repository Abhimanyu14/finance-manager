package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateAndEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateEvents

@Stable
internal class TransactionForValuesScreenUIStateAndEvents(
    val state: TransactionForValuesScreenUIState,
    val events: TransactionForValuesScreenUIStateEvents,
) : ScreenUIStateAndEvents

@Immutable
internal class TransactionForValuesScreenUIStateEvents(
    val resetScreenBottomSheetType: () -> Unit,
    val setScreenBottomSheetType: (TransactionForValuesScreenBottomSheetType) -> Unit,
    val setTransactionForIdToDelete: (Int?) -> Unit,
) : ScreenUIStateEvents

@Composable
internal fun rememberTransactionForValuesScreenUIStateAndEvents(
    transactionForValues: List<TransactionFor>,
    transactionForValuesIsUsedInTransactions: List<Boolean>,
): TransactionForValuesScreenUIStateAndEvents {
    // region transaction for id to delete
    var transactionForIdToDelete: Int? by remember {
        mutableStateOf(
            value = null,
        )
    }
    val setTransactionForIdToDelete: (Int?) -> Unit = { updatedTransactionForIdToDelete: Int? ->
        transactionForIdToDelete = updatedTransactionForIdToDelete
    }
    // endregion

    // region screen bottom sheet type
    var screenBottomSheetType: TransactionForValuesScreenBottomSheetType by remember {
        mutableStateOf(
            value = TransactionForValuesScreenBottomSheetType.None,
        )
    }
    val setScreenBottomSheetType: (TransactionForValuesScreenBottomSheetType) -> Unit =
        { updatedTransactionForValuesBottomSheetType: TransactionForValuesScreenBottomSheetType ->
            screenBottomSheetType = updatedTransactionForValuesBottomSheetType
        }
    // endregion

    return remember(
        screenBottomSheetType,
        transactionForIdToDelete,
        setTransactionForIdToDelete,
        setScreenBottomSheetType,
    ) {
        TransactionForValuesScreenUIStateAndEvents(
            state = TransactionForValuesScreenUIState(
                isBottomSheetVisible = screenBottomSheetType != TransactionForValuesScreenBottomSheetType.None,
                transactionForIdToDelete = transactionForIdToDelete,
                transactionForValuesIsUsedInTransactions = transactionForValuesIsUsedInTransactions,
                transactionForValues = transactionForValues,
                screenBottomSheetType = screenBottomSheetType,
            ),
            events = TransactionForValuesScreenUIStateEvents(
                resetScreenBottomSheetType = {
                    setScreenBottomSheetType(TransactionForValuesScreenBottomSheetType.None)
                },
                setScreenBottomSheetType = setScreenBottomSheetType,
                setTransactionForIdToDelete = setTransactionForIdToDelete,
            ),
        )
    }
}

package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateAndEvents

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
)

@Composable
internal fun rememberTransactionForValuesScreenUIStateAndEvents(
    data: MyResult<TransactionForValuesScreenUIData>?,
): TransactionForValuesScreenUIStateAndEvents {
    var transactionForIdToDelete: Int? by remember {
        mutableStateOf(
            value = null,
        )
    }
    var screenBottomSheetType: TransactionForValuesScreenBottomSheetType by remember {
        mutableStateOf(
            value = TransactionForValuesScreenBottomSheetType.None,
        )
    }
    val setTransactionForIdToDelete: (Int?) -> Unit = { updatedTransactionForIdToDelete: Int? ->
        transactionForIdToDelete = updatedTransactionForIdToDelete
    }
    val setScreenBottomSheetType: (TransactionForValuesScreenBottomSheetType) -> Unit =
        { updatedTransactionForValuesBottomSheetType: TransactionForValuesScreenBottomSheetType ->
            screenBottomSheetType = updatedTransactionForValuesBottomSheetType
        }

    return remember(
        data,
        screenBottomSheetType,
        transactionForIdToDelete,
        setTransactionForIdToDelete,
        setScreenBottomSheetType,
    ) {
        val unwrappedData: TransactionForValuesScreenUIData? = when (data) {
            is MyResult.Success -> {
                data.data
            }

            else -> {
                null
            }
        }

        // TODO(Abhi): Can be reordered to match the class ordering
        TransactionForValuesScreenUIStateAndEvents(
            state = TransactionForValuesScreenUIState(
                isBottomSheetVisible = screenBottomSheetType != TransactionForValuesScreenBottomSheetType.None,
                transactionForIdToDelete = transactionForIdToDelete,
                transactionForValuesIsUsedInTransactions = unwrappedData?.transactionForValuesIsUsedInTransactions.orEmpty(),
                transactionForValues = unwrappedData?.transactionForValues.orEmpty(),
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

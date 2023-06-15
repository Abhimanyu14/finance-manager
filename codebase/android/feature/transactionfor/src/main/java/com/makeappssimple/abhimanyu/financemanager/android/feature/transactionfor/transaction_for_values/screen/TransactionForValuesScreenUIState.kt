package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor

@Stable
class TransactionForValuesScreenUIState(
    data: TransactionForValuesScreenUIData,
    val transactionForIdToDelete: Int?,
    val transactionForValuesBottomSheetType: TransactionForValuesBottomSheetType,
    val setTransactionForIdToDelete: (Int?) -> Unit,
    val setTransactionForValuesBottomSheetType: (TransactionForValuesBottomSheetType) -> Unit,
) {
    val transactionForValuesIsUsedInTransactions: List<Boolean> =
        data.transactionForValuesIsUsedInTransactions
    val transactionForValues: List<TransactionFor> = data.transactionForValues
    val resetBottomSheetType: () -> Unit = {
        setTransactionForValuesBottomSheetType(TransactionForValuesBottomSheetType.None)
    }
}

@Composable
fun rememberTransactionForValuesScreenUIState(
    data: TransactionForValuesScreenUIData,
): TransactionForValuesScreenUIState {
    var transactionForIdToDelete: Int? by remember {
        mutableStateOf(
            value = null,
        )
    }
    var transactionForValuesBottomSheetType: TransactionForValuesBottomSheetType by remember {
        mutableStateOf(
            value = TransactionForValuesBottomSheetType.None,
        )
    }
    val setTransactionForIdToDelete: (Int?) -> Unit = { updatedTransactionForIdToDelete: Int? ->
        transactionForIdToDelete = updatedTransactionForIdToDelete
    }
    val setTransactionForValuesBottomSheetType: (TransactionForValuesBottomSheetType) -> Unit =
        { updatedTransactionForValuesBottomSheetType: TransactionForValuesBottomSheetType ->
            transactionForValuesBottomSheetType = updatedTransactionForValuesBottomSheetType
        }


    return remember(
        data,
        transactionForValuesBottomSheetType,
        transactionForIdToDelete,
        setTransactionForIdToDelete,
        setTransactionForValuesBottomSheetType,
    ) {
        TransactionForValuesScreenUIState(
            data = data,
            transactionForIdToDelete = transactionForIdToDelete,
            transactionForValuesBottomSheetType = transactionForValuesBottomSheetType,
            setTransactionForIdToDelete = setTransactionForIdToDelete,
            setTransactionForValuesBottomSheetType = setTransactionForValuesBottomSheetType,
        )
    }
}

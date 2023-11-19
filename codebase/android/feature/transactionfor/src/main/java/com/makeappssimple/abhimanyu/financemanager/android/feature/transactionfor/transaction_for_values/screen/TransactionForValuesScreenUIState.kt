package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState

@Stable
class TransactionForValuesScreenUIState(
    data: MyResult<TransactionForValuesScreenUIData>?,
    private val unwrappedData: TransactionForValuesScreenUIData? = when (data) {
        is MyResult.Success -> {
            data.data
        }

        else -> {
            null
        }
    },
    val transactionForIdToDelete: Int?,
    val screenBottomSheetType: TransactionForValuesScreenBottomSheetType,
    val setTransactionForIdToDelete: (Int?) -> Unit,
    val setScreenBottomSheetType: (TransactionForValuesScreenBottomSheetType) -> Unit,
    val isLoading: Boolean = unwrappedData.isNull(),
    val transactionForValuesIsUsedInTransactions: List<Boolean> =
        unwrappedData?.transactionForValuesIsUsedInTransactions.orEmpty(),
    val transactionForValues: List<TransactionFor> = unwrappedData?.transactionForValues.orEmpty(),
    val resetScreenBottomSheetType: () -> Unit = {
        setScreenBottomSheetType(TransactionForValuesScreenBottomSheetType.None)
    },
) : ScreenUIState

@Composable
fun rememberTransactionForValuesScreenUIState(
    data: MyResult<TransactionForValuesScreenUIData>?,
): TransactionForValuesScreenUIState {
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
        TransactionForValuesScreenUIState(
            data = data,
            transactionForIdToDelete = transactionForIdToDelete,
            screenBottomSheetType = screenBottomSheetType,
            setTransactionForIdToDelete = setTransactionForIdToDelete,
            setScreenBottomSheetType = setScreenBottomSheetType,
        )
    }
}

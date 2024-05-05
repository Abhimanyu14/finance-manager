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
public class TransactionForValuesScreenUIState(
    public val isLoading: Boolean,
    public val transactionForIdToDelete: Int?,
    public val transactionForValuesIsUsedInTransactions: List<Boolean>,
    public val transactionForValues: List<TransactionFor>,
    public val screenBottomSheetType: TransactionForValuesScreenBottomSheetType,
    public val resetScreenBottomSheetType: () -> Unit,
    public val setScreenBottomSheetType: (TransactionForValuesScreenBottomSheetType) -> Unit,
    public val setTransactionForIdToDelete: (Int?) -> Unit,
) : ScreenUIState

@Composable
public fun rememberTransactionForValuesScreenUIState(
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
        val unwrappedData: TransactionForValuesScreenUIData? = when (data) {
            is MyResult.Success -> {
                data.data
            }

            else -> {
                null
            }
        }

        // TODO(Abhi): Can be reordered to match the class ordering
        TransactionForValuesScreenUIState(
            transactionForIdToDelete = transactionForIdToDelete,
            screenBottomSheetType = screenBottomSheetType,
            setTransactionForIdToDelete = setTransactionForIdToDelete,
            setScreenBottomSheetType = setScreenBottomSheetType,
            isLoading = unwrappedData.isNull(),
            transactionForValuesIsUsedInTransactions = unwrappedData?.transactionForValuesIsUsedInTransactions.orEmpty(),
            transactionForValues = unwrappedData?.transactionForValues.orEmpty(),
            resetScreenBottomSheetType = {
                setScreenBottomSheetType(TransactionForValuesScreenBottomSheetType.None)
            },
        )
    }
}

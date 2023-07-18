package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orMin
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.transaction_list_item.TransactionListItemData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel.Filter
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel.SortOption
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel.orDefault
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel.orEmpty
import java.time.LocalDate

@Stable
class TransactionsScreenUIState(
    data: MyResult<TransactionsScreenUIData>?,
    val isInSelectionMode: Boolean,
    val transactionsBottomSheetType: TransactionsBottomSheetType,
    val setIsInSelectionMode: (Boolean) -> Unit,
    val setTransactionsBottomSheetType: (TransactionsBottomSheetType) -> Unit,
) {
    private val unwrappedData = when (data) {
        is MyResult.Success -> {
            data.data
        }

        else -> {
            null
        }
    }

    val isLoading: Boolean = unwrappedData.isNull() || unwrappedData.isLoading
    val selectedFilter: Filter = unwrappedData?.selectedFilter.orEmpty()
    val selectedTransactions: List<Int> = unwrappedData?.selectedTransactions.orEmpty()
    val sortOptions: List<SortOption> = unwrappedData?.sortOptions.orEmpty()
    val transactionForValues: List<TransactionFor> = unwrappedData?.transactionForValues.orEmpty()
    val transactionTypes: List<TransactionType> = unwrappedData?.transactionTypes.orEmpty()
    val oldestTransactionLocalDate: LocalDate = unwrappedData?.oldestTransactionLocalDate.orMin()
    val currentLocalDate: LocalDate = unwrappedData?.currentLocalDate.orMin()
    val currentTimeMillis: Long = unwrappedData?.currentTimeMillis.orZero()
    val transactionDetailsListItemViewData: Map<String, List<TransactionListItemData>> =
        unwrappedData?.transactionDetailsListItemViewData.orEmpty()
    val searchText: String = unwrappedData?.searchText.orEmpty()
    val selectedSortOption: SortOption = unwrappedData?.selectedSortOption.orDefault()
    val resetBottomSheetType: () -> Unit = {
        setTransactionsBottomSheetType(TransactionsBottomSheetType.NONE)
    }
}

@Composable
fun rememberTransactionsScreenUIState(
    data: MyResult<TransactionsScreenUIData>?,
): TransactionsScreenUIState {
    val (isInSelectionMode: Boolean, setIsInSelectionMode: (Boolean) -> Unit) = remember {
        mutableStateOf(false)
    }
    val (transactionsBottomSheetType: TransactionsBottomSheetType, setTransactionsBottomSheetType: (TransactionsBottomSheetType) -> Unit) = remember {
        mutableStateOf(
            value = TransactionsBottomSheetType.NONE,
        )
    }

    return remember(
        data,
        isInSelectionMode,
        transactionsBottomSheetType,
        setIsInSelectionMode,
        setTransactionsBottomSheetType,
    ) {
        TransactionsScreenUIState(
            data = data,
            isInSelectionMode = isInSelectionMode,
            transactionsBottomSheetType = transactionsBottomSheetType,
            setIsInSelectionMode = setIsInSelectionMode,
            setTransactionsBottomSheetType = setTransactionsBottomSheetType,
        )
    }
}

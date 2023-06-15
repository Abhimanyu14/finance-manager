package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.transaction_list_item.TransactionListItemData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel.Filter
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel.SortOption
import java.time.LocalDate

@Stable
class TransactionsScreenUIState(
    data: TransactionsScreenUIData,
    val transactionsBottomSheetType: TransactionsBottomSheetType,
    val setTransactionsBottomSheetType: (TransactionsBottomSheetType) -> Unit,
) {
    val isLoading: Boolean = data.isLoading
    val selectedFilter: Filter = data.selectedFilter
    val sortOptions: List<SortOption> = data.sortOptions
    val transactionTypes: List<TransactionType> = data.transactionTypes
    val oldestTransactionLocalDate: LocalDate = data.oldestTransactionLocalDate
    val currentLocalDate: LocalDate = data.currentLocalDate
    val currentTimeMillis: Long = data.currentTimeMillis
    val transactionDetailsListItemViewData: Map<String, List<TransactionListItemData>> =
        data.transactionDetailsListItemViewData
    val searchText: String = data.searchText
    val selectedSortOption: SortOption = data.selectedSortOption
    val resetBottomSheetType: () -> Unit = {
        setTransactionsBottomSheetType(TransactionsBottomSheetType.NONE)
    }
}

@Composable
fun rememberTransactionsScreenUIState(
    data: TransactionsScreenUIData,
): TransactionsScreenUIState {
    val (transactionsBottomSheetType: TransactionsBottomSheetType, setTransactionsBottomSheetType: (TransactionsBottomSheetType) -> Unit) = remember {
        mutableStateOf(
            value = TransactionsBottomSheetType.NONE,
        )
    }

    return remember(
        data,
        transactionsBottomSheetType,
        setTransactionsBottomSheetType,
    ) {
        TransactionsScreenUIState(
            data = data,
            transactionsBottomSheetType = transactionsBottomSheetType,
            setTransactionsBottomSheetType = setTransactionsBottomSheetType,
        )
    }
}

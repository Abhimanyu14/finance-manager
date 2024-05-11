package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orMin
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.Filter
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.SortOption
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.orDefault
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.orEmpty
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transaction.TransactionListItemData
import java.time.LocalDate

@Stable
internal class TransactionsScreenUIState(
    val isBottomSheetVisible: Boolean,
    val isInSelectionMode: Boolean,
    val isLoading: Boolean,
    val selectedFilter: Filter,
    val selectedTransactions: List<Int>,
    val sortOptions: List<SortOption>,
    val transactionForValues: List<TransactionFor>,
    val accounts: List<Account>,
    val expenseCategories: List<Category>,
    val incomeCategories: List<Category>,
    val investmentCategories: List<Category>,
    val transactionTypes: List<TransactionType>,
    val currentLocalDate: LocalDate,
    val oldestTransactionLocalDate: LocalDate,
    val transactionDetailsListItemViewData: Map<String, List<TransactionListItemData>>,
    val selectedSortOption: SortOption,
    val searchText: String,
    val screenBottomSheetType: TransactionsScreenBottomSheetType,
    val resetScreenBottomSheetType: () -> Unit,
    val setIsInSelectionMode: (Boolean) -> Unit,
    val setScreenBottomSheetType: (TransactionsScreenBottomSheetType) -> Unit,
) : ScreenUIState

@Composable
internal fun rememberTransactionsScreenUIState(
    data: MyResult<TransactionsScreenUIData>?,
): TransactionsScreenUIState {
    val (isInSelectionMode: Boolean, setIsInSelectionMode: (Boolean) -> Unit) = remember {
        mutableStateOf(false)
    }
    var screenBottomSheetType: TransactionsScreenBottomSheetType by remember {
        mutableStateOf(
            value = TransactionsScreenBottomSheetType.None,
        )
    }
    val setScreenBottomSheetType =
        { updatedTransactionsScreenBottomSheetType: TransactionsScreenBottomSheetType ->
            screenBottomSheetType = updatedTransactionsScreenBottomSheetType
        }

    return remember(
        data,
        isInSelectionMode,
        screenBottomSheetType,
        setIsInSelectionMode,
        setScreenBottomSheetType,
    ) {
        val unwrappedData: TransactionsScreenUIData? = when (data) {
            is MyResult.Success -> {
                data.data
            }

            else -> {
                null
            }
        }

        // TODO(Abhi): Can be reordered to match the class ordering
        TransactionsScreenUIState(
            isBottomSheetVisible = screenBottomSheetType != TransactionsScreenBottomSheetType.None,
            isInSelectionMode = isInSelectionMode,
            isLoading = unwrappedData.isNull() || unwrappedData.isLoading,
            selectedFilter = unwrappedData?.selectedFilter.orEmpty(),
            selectedTransactions = unwrappedData?.selectedTransactions.orEmpty(),
            sortOptions = unwrappedData?.sortOptions.orEmpty(),
            transactionForValues = unwrappedData?.transactionForValues.orEmpty(),
            accounts = unwrappedData?.accounts.orEmpty(),
            expenseCategories = unwrappedData?.expenseCategories.orEmpty(),
            incomeCategories = unwrappedData?.incomeCategories.orEmpty(),
            investmentCategories = unwrappedData?.investmentCategories.orEmpty(),
            transactionTypes = unwrappedData?.transactionTypes.orEmpty(),
            currentLocalDate = unwrappedData?.currentLocalDate.orMin(),
            oldestTransactionLocalDate = unwrappedData?.oldestTransactionLocalDate.orMin(),
            transactionDetailsListItemViewData = unwrappedData?.transactionDetailsListItemViewData.orEmpty(),
            selectedSortOption = unwrappedData?.selectedSortOption.orDefault(),
            searchText = unwrappedData?.searchText.orEmpty(),
            screenBottomSheetType = screenBottomSheetType,
            resetScreenBottomSheetType = {
                setScreenBottomSheetType(TransactionsScreenBottomSheetType.None)
            },
            setIsInSelectionMode = setIsInSelectionMode,
            setScreenBottomSheetType = setScreenBottomSheetType,
        )
    }
}

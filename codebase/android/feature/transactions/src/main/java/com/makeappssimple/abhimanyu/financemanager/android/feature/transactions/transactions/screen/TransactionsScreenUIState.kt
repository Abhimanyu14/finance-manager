package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orMin
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orZero
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
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.TransactionListItemData
import java.time.LocalDate

@Stable
class TransactionsScreenUIState(
    data: MyResult<TransactionsScreenUIData>?,
    private val unwrappedData: TransactionsScreenUIData? = when (data) {
        is MyResult.Success -> {
            data.data
        }

        else -> {
            null
        }
    },
    val isInSelectionMode: Boolean,
    val screenBottomSheetType: TransactionsScreenBottomSheetType,
    val setIsInSelectionMode: (Boolean) -> Unit,
    val setScreenBottomSheetType: (TransactionsScreenBottomSheetType) -> Unit,
    val isLoading: Boolean = unwrappedData.isNull() || unwrappedData.isLoading,
    val selectedFilter: Filter = unwrappedData?.selectedFilter.orEmpty(),
    val selectedTransactions: List<Int> = unwrappedData?.selectedTransactions.orEmpty(),
    val sortOptions: List<SortOption> = unwrappedData?.sortOptions.orEmpty(),
    val transactionForValues: List<TransactionFor> = unwrappedData?.transactionForValues.orEmpty(),
    val accounts: List<Account> = unwrappedData?.accounts.orEmpty(),
    val expenseCategories: List<Category> = unwrappedData?.expenseCategories.orEmpty(),
    val incomeCategories: List<Category> = unwrappedData?.incomeCategories.orEmpty(),
    val investmentCategories: List<Category> = unwrappedData?.investmentCategories.orEmpty(),
    val transactionTypes: List<TransactionType> = unwrappedData?.transactionTypes.orEmpty(),
    val oldestTransactionLocalDate: LocalDate = unwrappedData?.oldestTransactionLocalDate.orMin(),
    val currentLocalDate: LocalDate = unwrappedData?.currentLocalDate.orMin(),
    val currentTimeMillis: Long = unwrappedData?.currentTimeMillis.orZero(),
    val transactionDetailsListItemViewData: Map<String, List<TransactionListItemData>> =
        unwrappedData?.transactionDetailsListItemViewData.orEmpty(),
    val searchText: String = unwrappedData?.searchText.orEmpty(),
    val selectedSortOption: SortOption = unwrappedData?.selectedSortOption.orDefault(),
    val resetScreenBottomSheetType: () -> Unit = {
        setScreenBottomSheetType(TransactionsScreenBottomSheetType.NONE)
    },
) : ScreenUIState

@Composable
fun rememberTransactionsScreenUIState(
    data: MyResult<TransactionsScreenUIData>?,
): TransactionsScreenUIState {
    val (isInSelectionMode: Boolean, setIsInSelectionMode: (Boolean) -> Unit) = remember {
        mutableStateOf(false)
    }
    val (screenBottomSheetType: TransactionsScreenBottomSheetType, setScreenBottomSheetType: (TransactionsScreenBottomSheetType) -> Unit) = remember {
        mutableStateOf(
            value = TransactionsScreenBottomSheetType.NONE,
        )
    }

    return remember(
        data,
        isInSelectionMode,
        screenBottomSheetType,
        setIsInSelectionMode,
        setScreenBottomSheetType,
    ) {
        TransactionsScreenUIState(
            data = data,
            isInSelectionMode = isInSelectionMode,
            screenBottomSheetType = screenBottomSheetType,
            setIsInSelectionMode = setIsInSelectionMode,
            setScreenBottomSheetType = setScreenBottomSheetType,
        )
    }
}

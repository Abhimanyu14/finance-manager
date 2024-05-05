package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transaction.TransactionListItemData
import java.time.LocalDate

@Stable
public class TransactionsScreenUIState(
    public val isInSelectionMode: Boolean,
    public val screenBottomSheetType: TransactionsScreenBottomSheetType,
    public val setIsInSelectionMode: (Boolean) -> Unit,
    public val setScreenBottomSheetType: (TransactionsScreenBottomSheetType) -> Unit,
    public val isLoading: Boolean,
    public val selectedFilter: Filter,
    public val selectedTransactions: List<Int>,
    public val sortOptions: List<SortOption>,
    public val transactionForValues: List<TransactionFor>,
    public val accounts: List<Account>,
    public val expenseCategories: List<Category>,
    public val incomeCategories: List<Category>,
    public val investmentCategories: List<Category>,
    public val transactionTypes: List<TransactionType>,
    public val oldestTransactionLocalDate: LocalDate,
    public val currentLocalDate: LocalDate,
    public val currentTimeMillis: Long,
    public val transactionDetailsListItemViewData: Map<String, List<TransactionListItemData>>,
    public val searchText: String,
    public val selectedSortOption: SortOption,
    public val resetScreenBottomSheetType: () -> Unit,
) : ScreenUIState

@Composable
public fun rememberTransactionsScreenUIState(
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

        TransactionsScreenUIState(
            isInSelectionMode = isInSelectionMode,
            screenBottomSheetType = screenBottomSheetType,
            setIsInSelectionMode = setIsInSelectionMode,
            setScreenBottomSheetType = setScreenBottomSheetType,
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
            oldestTransactionLocalDate = unwrappedData?.oldestTransactionLocalDate.orMin(),
            currentLocalDate = unwrappedData?.currentLocalDate.orMin(),
            currentTimeMillis = unwrappedData?.currentTimeMillis.orZero(),
            transactionDetailsListItemViewData = unwrappedData?.transactionDetailsListItemViewData.orEmpty(),
            searchText = unwrappedData?.searchText.orEmpty(),
            selectedSortOption = unwrappedData?.selectedSortOption.orDefault(),
            resetScreenBottomSheetType = {
                setScreenBottomSheetType(TransactionsScreenBottomSheetType.None)
            },
        )
    }
}

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
    data: MyResult<TransactionsScreenUIData>?,
    private val unwrappedData: TransactionsScreenUIData? = when (data) {
        is MyResult.Success -> {
            data.data
        }

        else -> {
            null
        }
    },
    public val isInSelectionMode: Boolean,
    public val screenBottomSheetType: TransactionsScreenBottomSheetType,
    public val setIsInSelectionMode: (Boolean) -> Unit,
    public val setScreenBottomSheetType: (TransactionsScreenBottomSheetType) -> Unit,
    public val isLoading: Boolean = unwrappedData.isNull() || unwrappedData.isLoading,
    public val selectedFilter: Filter = unwrappedData?.selectedFilter.orEmpty(),
    public val selectedTransactions: List<Int> = unwrappedData?.selectedTransactions.orEmpty(),
    public val sortOptions: List<SortOption> = unwrappedData?.sortOptions.orEmpty(),
    public val transactionForValues: List<TransactionFor> = unwrappedData?.transactionForValues.orEmpty(),
    public val accounts: List<Account> = unwrappedData?.accounts.orEmpty(),
    public val expenseCategories: List<Category> = unwrappedData?.expenseCategories.orEmpty(),
    public val incomeCategories: List<Category> = unwrappedData?.incomeCategories.orEmpty(),
    public val investmentCategories: List<Category> = unwrappedData?.investmentCategories.orEmpty(),
    public val transactionTypes: List<TransactionType> = unwrappedData?.transactionTypes.orEmpty(),
    public val oldestTransactionLocalDate: LocalDate = unwrappedData?.oldestTransactionLocalDate.orMin(),
    public val currentLocalDate: LocalDate = unwrappedData?.currentLocalDate.orMin(),
    public val currentTimeMillis: Long = unwrappedData?.currentTimeMillis.orZero(),
    public val transactionDetailsListItemViewData: Map<String, List<TransactionListItemData>> =
        unwrappedData?.transactionDetailsListItemViewData.orEmpty(),
    public val searchText: String = unwrappedData?.searchText.orEmpty(),
    public val selectedSortOption: SortOption = unwrappedData?.selectedSortOption.orDefault(),
    public val resetScreenBottomSheetType: () -> Unit = {
        setScreenBottomSheetType(TransactionsScreenBottomSheetType.None)
    },
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
        TransactionsScreenUIState(
            data = data,
            isInSelectionMode = isInSelectionMode,
            screenBottomSheetType = screenBottomSheetType,
            setIsInSelectionMode = setIsInSelectionMode,
            setScreenBottomSheetType = setScreenBottomSheetType,
        )
    }
}

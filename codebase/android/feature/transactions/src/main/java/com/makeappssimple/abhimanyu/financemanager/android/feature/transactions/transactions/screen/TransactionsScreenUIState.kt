package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.screen

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.Filter
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.SortOption
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transaction.TransactionListItemData
import java.time.LocalDate

@Stable
internal class TransactionsScreenUIState(
    val isBottomSheetVisible: Boolean,
    val isInSelectionMode: Boolean,
    val isLoading: Boolean,
    val isSearchSortAndFilterVisible: Boolean,
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
) : ScreenUIState

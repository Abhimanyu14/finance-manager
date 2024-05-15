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
import kotlinx.collections.immutable.ImmutableList
import java.time.LocalDate

@Stable
internal data class TransactionsScreenUIState(
    val isBottomSheetVisible: Boolean,
    val isInSelectionMode: Boolean,
    val isLoading: Boolean,
    val isSearchSortAndFilterVisible: Boolean,
    val selectedFilter: Filter,
    val selectedTransactions: ImmutableList<Int>,
    val sortOptions: ImmutableList<SortOption>,
    val transactionForValues: ImmutableList<TransactionFor>,
    val accounts: ImmutableList<Account>,
    val expenseCategories: ImmutableList<Category>,
    val incomeCategories: ImmutableList<Category>,
    val investmentCategories: ImmutableList<Category>,
    val transactionTypes: ImmutableList<TransactionType>,
    val currentLocalDate: LocalDate,
    val oldestTransactionLocalDate: LocalDate,
    val transactionDetailsListItemViewData: Map<String, ImmutableList<TransactionListItemData>>,
    val selectedSortOption: SortOption,
    val searchText: String,
    val screenBottomSheetType: TransactionsScreenBottomSheetType,
) : ScreenUIState

package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.screen

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.transaction_list_item.TransactionListItemData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel.Filter
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel.SortOption
import java.time.LocalDate

@Immutable
data class TransactionsScreenUIData(
    val isLoading: Boolean = false,
    val selectedFilter: Filter = Filter(),
    val accounts: List<Account> = emptyList(),
    val expenseCategories: List<Category> = emptyList(),
    val incomeCategories: List<Category> = emptyList(),
    val investmentCategories: List<Category> = emptyList(),
    val selectedTransactions: List<Int> = emptyList(),
    val sortOptions: List<SortOption> = emptyList(),
    val transactionTypes: List<TransactionType> = emptyList(),
    val transactionForValues: List<TransactionFor> = emptyList(),
    val oldestTransactionLocalDate: LocalDate = LocalDate.MIN,
    val currentLocalDate: LocalDate = LocalDate.MIN,
    val currentTimeMillis: Long = 0L,
    val transactionDetailsListItemViewData: Map<String, List<TransactionListItemData>> = emptyMap(),
    val searchText: String = "",
    val selectedSortOption: SortOption = SortOption.LATEST_FIRST,
) : ScreenUIData

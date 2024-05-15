package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.screen

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.Filter
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.SortOption
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transaction.TransactionListItemData
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate

@Immutable
public data class TransactionsScreenUIData(
    val isLoading: Boolean = false,
    val selectedFilter: Filter = Filter(),
    val accounts: ImmutableList<Account> = persistentListOf(),
    val expenseCategories: ImmutableList<Category> = persistentListOf(),
    val incomeCategories: ImmutableList<Category> = persistentListOf(),
    val investmentCategories: ImmutableList<Category> = persistentListOf(),
    val selectedTransactions: ImmutableList<Int> = persistentListOf(),
    val sortOptions: ImmutableList<SortOption> = persistentListOf(),
    val transactionTypes: ImmutableList<TransactionType> = persistentListOf(),
    val transactionForValues: ImmutableList<TransactionFor> = persistentListOf(),
    val oldestTransactionLocalDate: LocalDate = LocalDate.MIN,
    val currentLocalDate: LocalDate = LocalDate.MIN,
    val currentTimeMillis: Long = 0L,
    val transactionDetailsListItemViewData: Map<String, ImmutableList<TransactionListItemData>> = emptyMap(),
    val searchText: String = "",
    val selectedSortOption: SortOption = SortOption.LATEST_FIRST,
) : ScreenUIData

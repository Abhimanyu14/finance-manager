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
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate

@Stable
internal data class TransactionsScreenUIState(
    val isBottomSheetVisible: Boolean = false,
    val isInSelectionMode: Boolean = false,
    val isLoading: Boolean = true,
    val isSearchSortAndFilterVisible: Boolean = false,
    val selectedFilter: Filter = Filter(),
    val selectedTransactions: ImmutableList<Int> = persistentListOf(),
    val sortOptions: ImmutableList<SortOption> = persistentListOf(),
    val transactionForValues: ImmutableList<TransactionFor> = persistentListOf(),
    val accounts: ImmutableList<Account> = persistentListOf(),
    val expenseCategories: ImmutableList<Category> = persistentListOf(),
    val incomeCategories: ImmutableList<Category> = persistentListOf(),
    val investmentCategories: ImmutableList<Category> = persistentListOf(),
    val transactionTypes: ImmutableList<TransactionType> = persistentListOf(),
    val currentLocalDate: LocalDate = LocalDate.MIN,
    val oldestTransactionLocalDate: LocalDate = LocalDate.MIN,
    val transactionDetailsListItemViewData: Map<String, ImmutableList<TransactionListItemData>> = emptyMap(),
    val selectedSortOption: SortOption = SortOption.LATEST_FIRST,
    val searchText: String = "",
    val screenBottomSheetType: TransactionsScreenBottomSheetType = TransactionsScreenBottomSheetType.None,
) : ScreenUIState

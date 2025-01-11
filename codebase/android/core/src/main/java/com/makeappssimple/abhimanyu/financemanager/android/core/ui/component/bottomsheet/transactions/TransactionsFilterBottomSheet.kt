package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.transactions

import androidx.compose.runtime.Composable
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.Filter
import kotlinx.collections.immutable.ImmutableList
import java.time.LocalDate

@Composable
public fun TransactionsFilterBottomSheet(
    expenseCategories: ImmutableList<Category>,
    incomeCategories: ImmutableList<Category>,
    investmentCategories: ImmutableList<Category>,
    accounts: ImmutableList<Account>,
    transactionForValues: ImmutableList<TransactionFor>,
    transactionTypes: ImmutableList<TransactionType>,
    defaultMinDate: LocalDate,
    defaultMaxDate: LocalDate,
    selectedFilter: Filter,
    updateSelectedFilter: (updatedSelectedFilter: Filter) -> Unit,
    resetBottomSheetType: () -> Unit,
) {
    TransactionsFiltersBottomSheetUI(
        expenseCategories = expenseCategories,
        incomeCategories = incomeCategories,
        investmentCategories = investmentCategories,
        accounts = accounts,
        transactionForValues = transactionForValues,
        transactionTypes = transactionTypes,
        defaultMinDate = defaultMinDate,
        defaultMaxDate = defaultMaxDate,
        selectedFilter = selectedFilter,
        onPositiveButtonClick = {
            updateSelectedFilter(it)
            resetBottomSheetType()
        },
        onNegativeButtonClick = {},
    )
}

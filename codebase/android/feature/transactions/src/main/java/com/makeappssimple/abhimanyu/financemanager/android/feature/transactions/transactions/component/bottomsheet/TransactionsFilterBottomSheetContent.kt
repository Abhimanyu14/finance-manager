package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.component.bottomsheet

import android.content.Context
import androidx.compose.runtime.Composable
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel.Filter
import java.time.LocalDate

@Composable
internal fun TransactionsFilterBottomSheetContent(
    context: Context,
    expenseCategories: List<Category>,
    incomeCategories: List<Category>,
    investmentCategories: List<Category>,
    sources: List<Source>,
    transactionTypes: List<TransactionType>,
    defaultMinDate: LocalDate,
    defaultMaxDate: LocalDate,
    currentTimeMillis: Long,
    selectedFilter: Filter,
    updateSelectedFilter: (updatedSelectedFilter: Filter) -> Unit,
    resetBottomSheetType: () -> Unit,
) {
    TransactionsFiltersBottomSheet(
        context = context,
        expenseCategories = expenseCategories,
        incomeCategories = incomeCategories,
        investmentCategories = investmentCategories,
        sources = sources,
        transactionTypes = transactionTypes,
        defaultMinDate = defaultMinDate,
        defaultMaxDate = defaultMaxDate,
        currentTimeMillis = currentTimeMillis,
        selectedFilter = selectedFilter,
        onPositiveButtonClick = {
            updateSelectedFilter(it)
            resetBottomSheetType()
        },
        onNegativeButtonClick = {},
    )
}

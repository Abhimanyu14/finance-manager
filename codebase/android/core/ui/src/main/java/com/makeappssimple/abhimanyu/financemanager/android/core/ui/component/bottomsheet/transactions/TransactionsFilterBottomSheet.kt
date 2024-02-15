package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.transactions

import androidx.compose.runtime.Composable
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.Filter
import java.time.LocalDate

@Composable
fun TransactionsFilterBottomSheet(
    expenseCategories: List<Category>,
    incomeCategories: List<Category>,
    investmentCategories: List<Category>,
    accounts: List<Account>,
    transactionForValues: List<TransactionFor>,
    transactionTypes: List<TransactionType>,
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

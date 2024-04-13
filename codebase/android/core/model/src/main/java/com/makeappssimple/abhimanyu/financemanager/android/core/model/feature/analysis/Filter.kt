package com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.analysis

import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import java.time.LocalDate

public data class Filter(
    val selectedExpenseCategoryIndices: List<Int> = emptyList(),
    val selectedIncomeCategoryIndices: List<Int> = emptyList(),
    val selectedInvestmentCategoryIndices: List<Int> = emptyList(),
    val selectedAccountsIndices: List<Int> = emptyList(),
    val selectedTransactionTypeIndices: List<Int> = emptyList(),
    val fromLocalDate: LocalDate? = null,
    val toLocalDate: LocalDate? = null,
) {
    public fun areFiltersSelected(): Boolean {
        return selectedExpenseCategoryIndices.isNotEmpty() ||
                selectedIncomeCategoryIndices.isNotEmpty() ||
                selectedInvestmentCategoryIndices.isNotEmpty() ||
                selectedAccountsIndices.isNotEmpty() ||
                selectedTransactionTypeIndices.isNotEmpty() ||
                toLocalDate.isNotNull()
    }
}

public fun Filter?.orEmpty(): Filter {
    return if (this.isNull()) {
        Filter()
    } else {
        this
    }
}

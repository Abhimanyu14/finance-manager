package com.makeappssimple.abhimanyu.financemanager.android.core.model.feature

import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import java.time.LocalDate

public data class Filter(
    public val selectedExpenseCategoryIndices: List<Int> = emptyList(),
    public val selectedIncomeCategoryIndices: List<Int> = emptyList(),
    public val selectedInvestmentCategoryIndices: List<Int> = emptyList(),
    public val selectedAccountsIndices: List<Int> = emptyList(),
    public val selectedTransactionForValuesIndices: List<Int> = emptyList(),
    public val selectedTransactionTypeIndices: List<Int> = emptyList(),
    public val fromDate: LocalDate? = null,
    public val toDate: LocalDate? = null,
)

public fun Filter.areFiltersSelected(): Boolean {
    return selectedExpenseCategoryIndices.isNotEmpty() ||
            selectedIncomeCategoryIndices.isNotEmpty() ||
            selectedInvestmentCategoryIndices.isNotEmpty() ||
            selectedAccountsIndices.isNotEmpty() ||
            selectedTransactionForValuesIndices.isNotEmpty() ||
            selectedTransactionTypeIndices.isNotEmpty() ||
            toDate.isNotNull()
}

public fun Filter?.orEmpty(): Filter {
    return if (this.isNull()) {
        Filter()
    } else {
        this
    }
}

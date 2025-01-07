package com.makeappssimple.abhimanyu.financemanager.android.cre.model.feature.analysis

import com.makeappssimple.abhimanyu.financemanager.android.cre.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.extensions.isNull
import java.time.LocalDate

public class Filter(
    private val selectedExpenseCategoryIndices: List<Int> = emptyList(),
    private val selectedIncomeCategoryIndices: List<Int> = emptyList(),
    private val selectedInvestmentCategoryIndices: List<Int> = emptyList(),
    private val selectedAccountsIndices: List<Int> = emptyList(),
    private val selectedTransactionTypeIndices: List<Int> = emptyList(),
    public val fromLocalDate: LocalDate? = null,
    public val toLocalDate: LocalDate? = null,
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

package com.makeappssimple.abhimanyu.financemanager.android.cre.model.feature

import com.makeappssimple.abhimanyu.financemanager.android.cre.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.extensions.isNull
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate

public data class Filter(
    public val selectedExpenseCategoryIndices: ImmutableList<Int> = persistentListOf(),
    public val selectedIncomeCategoryIndices: ImmutableList<Int> = persistentListOf(),
    public val selectedInvestmentCategoryIndices: ImmutableList<Int> = persistentListOf(),
    public val selectedAccountsIndices: ImmutableList<Int> = persistentListOf(),
    public val selectedTransactionForValuesIndices: ImmutableList<Int> = persistentListOf(),
    public val selectedTransactionTypeIndices: ImmutableList<Int> = persistentListOf(),
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

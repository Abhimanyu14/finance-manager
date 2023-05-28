package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.viewmodel

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import java.time.LocalDate

@Immutable
data class Filter(
    val selectedExpenseCategoryIndices: List<Int> = emptyList(),
    val selectedIncomeCategoryIndices: List<Int> = emptyList(),
    val selectedInvestmentCategoryIndices: List<Int> = emptyList(),
    val selectedSourceIndices: List<Int> = emptyList(),
    val selectedTransactionTypeIndices: List<Int> = emptyList(),
    val fromDate: LocalDate? = null,
    val toDate: LocalDate? = null,
) {
    fun areFiltersSelected(): Boolean {
        return selectedExpenseCategoryIndices.isNotEmpty() ||
                selectedIncomeCategoryIndices.isNotEmpty() ||
                selectedInvestmentCategoryIndices.isNotEmpty() ||
                selectedSourceIndices.isNotEmpty() ||
                selectedTransactionTypeIndices.isNotEmpty() ||
                toDate.isNotNull()
    }
}

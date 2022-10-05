package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel

import androidx.compose.runtime.Immutable

@Immutable
data class Filter(
    val selectedExpenseCategoryIndices: List<Int> = emptyList(),
    val selectedIncomeCategoryIndices: List<Int> = emptyList(),
    val selectedInvestmentCategoryIndices: List<Int> = emptyList(),
    val selectedSourceIndices: List<Int> = emptyList(),
    val selectedTransactionTypeIndices: List<Int> = emptyList(),
) {
    fun areFiltersSelected(): Boolean {
        return selectedExpenseCategoryIndices.isNotEmpty() ||
                selectedIncomeCategoryIndices.isNotEmpty() ||
                selectedInvestmentCategoryIndices.isNotEmpty() ||
                selectedSourceIndices.isNotEmpty() ||
                selectedTransactionTypeIndices.isNotEmpty()
    }
}

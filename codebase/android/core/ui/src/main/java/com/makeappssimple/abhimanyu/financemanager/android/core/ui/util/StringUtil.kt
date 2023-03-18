package com.makeappssimple.abhimanyu.financemanager.android.core.ui.util

const val defaultExpenseCategory = "Default"
const val defaultIncomeCategory = "Salary"
const val defaultInvestmentCategory = "Provident Fund"
const val defaultSource = "Cash"

fun isDefaultExpenseCategory(
    category: String,
): Boolean {
    return category.contains(
        other = defaultExpenseCategory,
        ignoreCase = true,
    )
}

fun isDefaultIncomeCategory(
    category: String,
): Boolean {
    return category.contains(
        other = defaultIncomeCategory,
        ignoreCase = true,
    )
}

fun isDefaultInvestmentCategory(
    category: String,
): Boolean {
    return category.contains(
        other = defaultInvestmentCategory,
        ignoreCase = true,
    )
}

fun isDefaultSource(
    source: String,
): Boolean {
    return source.contains(
        other = defaultSource,
        ignoreCase = true,
    )
}

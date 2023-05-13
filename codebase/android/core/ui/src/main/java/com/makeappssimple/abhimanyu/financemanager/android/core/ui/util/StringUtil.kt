package com.makeappssimple.abhimanyu.financemanager.android.core.ui.util

private const val DEFAULT_EXPENSE_CATEGORY = "Default"
private const val DEFAULT_INCOME_CATEGORY = "Salary"
private const val DEFAULT_INVESTMENT_CATEGORY = "Investment"
private const val DEFAULT_SOURCE = "Cash"

fun isDefaultExpenseCategory(
    category: String,
): Boolean {
    return category.contains(
        other = DEFAULT_EXPENSE_CATEGORY,
        ignoreCase = true,
    )
}

fun isDefaultIncomeCategory(
    category: String,
): Boolean {
    return category.contains(
        other = DEFAULT_INCOME_CATEGORY,
        ignoreCase = true,
    )
}

fun isDefaultInvestmentCategory(
    category: String,
): Boolean {
    return category.contains(
        other = DEFAULT_INVESTMENT_CATEGORY,
        ignoreCase = true,
    )
}

fun isDefaultSource(
    source: String,
): Boolean {
    return source.contains(
        other = DEFAULT_SOURCE,
        ignoreCase = true,
    )
}

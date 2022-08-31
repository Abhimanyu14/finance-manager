package com.makeappssimple.abhimanyu.financemanager.android.core.ui.util

fun isDefaultExpenseCategory(
    category: String,
): Boolean {
    return category.contains(
        other = "Default",
        ignoreCase = true,
    )
}

fun isDefaultIncomeCategory(
    category: String,
): Boolean {
    return category.contains(
        other = "Salary",
        ignoreCase = true,
    )
}

fun isDefaultInvestmentCategory(
    category: String,
): Boolean {
    return category.contains(
        other = "Provident Fund",
        ignoreCase = true,
    )
}

fun isCashSource(
    source: String,
): Boolean {
    return source.contains(
        other = "Cash",
        ignoreCase = true,
    )
}

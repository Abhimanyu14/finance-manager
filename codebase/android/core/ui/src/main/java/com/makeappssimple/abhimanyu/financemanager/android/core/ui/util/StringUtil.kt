package com.makeappssimple.abhimanyu.financemanager.android.core.ui.util

import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.equalsIgnoringCase

private const val DEFAULT_EXPENSE_CATEGORY = "Default"
private const val DEFAULT_INCOME_CATEGORY = "Salary"
private const val DEFAULT_INVESTMENT_CATEGORY = "Investment"
private const val DEFAULT_ACCOUNT = "Cash"
private const val DEFAULT_TRANSACTION_FOR = "Self"

fun isDefaultExpenseCategory(
    category: String,
): Boolean {
    return category.equalsIgnoringCase(
        other = DEFAULT_EXPENSE_CATEGORY,
    )
}

fun isDefaultIncomeCategory(
    category: String,
): Boolean {
    return category.equalsIgnoringCase(
        other = DEFAULT_INCOME_CATEGORY,
    )
}

fun isDefaultInvestmentCategory(
    category: String,
): Boolean {
    return category.equalsIgnoringCase(
        other = DEFAULT_INVESTMENT_CATEGORY,
    )
}

fun isDefaultAccount(
    account: String,
): Boolean {
    return account.equalsIgnoringCase(
        other = DEFAULT_ACCOUNT,
    )
}

fun isDefaultTransactionFor(
    transactionFor: String,
): Boolean {
    return transactionFor.equalsIgnoringCase(
        other = DEFAULT_TRANSACTION_FOR,
    )
}

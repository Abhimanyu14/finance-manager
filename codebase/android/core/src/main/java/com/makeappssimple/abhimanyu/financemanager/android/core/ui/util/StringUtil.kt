package com.makeappssimple.abhimanyu.financemanager.android.core.ui.util

import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.equalsIgnoringCase

private object DefaultConstants {
    const val EXPENSE_CATEGORY = "Default"
    const val INCOME_CATEGORY = "Salary"
    const val INVESTMENT_CATEGORY = "Investment"
    const val ACCOUNT = "Cash"
    const val TRANSACTION_FOR = "Self"
}

public fun isDefaultExpenseCategory(
    category: String,
): Boolean {
    return category.equalsIgnoringCase(
        other = DefaultConstants.EXPENSE_CATEGORY,
    )
}

public fun isDefaultIncomeCategory(
    category: String,
): Boolean {
    return category.equalsIgnoringCase(
        other = DefaultConstants.INCOME_CATEGORY,
    )
}

public fun isDefaultInvestmentCategory(
    category: String,
): Boolean {
    return category.equalsIgnoringCase(
        other = DefaultConstants.INVESTMENT_CATEGORY,
    )
}

public fun isDefaultAccount(
    account: String,
): Boolean {
    return account.equalsIgnoringCase(
        other = DefaultConstants.ACCOUNT,
    )
}

public fun isDefaultTransactionFor(
    transactionFor: String,
): Boolean {
    return transactionFor.equalsIgnoringCase(
        other = DefaultConstants.TRANSACTION_FOR,
    )
}

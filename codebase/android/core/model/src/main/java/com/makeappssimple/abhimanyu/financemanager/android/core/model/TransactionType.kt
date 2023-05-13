package com.makeappssimple.abhimanyu.financemanager.android.core.model

enum class TransactionType(
    val title: String,
) {
    INCOME(
        title = "Income",
    ),
    EXPENSE(
        title = "Expense",
    ),
    TRANSFER(
        title = "Transfer",
    ),
    ADJUSTMENT(
        title = "Adjustment",
    ),
    INVESTMENT(
        title = "Investment",
    ),
    REFUND(
        title = "Refund",
    ),
    /*
    // TODO-Abhi: Add Loans later
    LOAN(
        title = "Loan",
    ),
    REPAYMENT(
        title = "Repayment",
    ),
    */
}

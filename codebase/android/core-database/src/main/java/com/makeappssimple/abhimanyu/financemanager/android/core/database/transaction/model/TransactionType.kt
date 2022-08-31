package com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
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

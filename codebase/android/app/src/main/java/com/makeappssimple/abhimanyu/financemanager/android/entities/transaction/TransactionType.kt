package com.makeappssimple.abhimanyu.financemanager.android.entities.transaction

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

package com.makeappssimple.abhimanyu.financemanager.entities

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
}

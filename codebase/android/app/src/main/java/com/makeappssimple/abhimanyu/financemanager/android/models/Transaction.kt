package com.makeappssimple.abhimanyu.financemanager.android.models

enum class TransactionFor {
    SELF,
    COMMON,
    OTHERS,
}

enum class TransactionType {
    INCOME,
    EXPENSE,
    TRANSFER,
    LOAN,
    REPAYMENT,
}

data class Transaction(
    val amount: Amount,
    val category: Category,
    val id: Int,
    val description: String = "",
    val title: String,
    val creationTimestamp: Timestamp,
    val transactionTimestamp: Timestamp,
    val TransactionFor: TransactionFor? = null,
    val transactionType: TransactionType = TransactionType.EXPENSE,
)

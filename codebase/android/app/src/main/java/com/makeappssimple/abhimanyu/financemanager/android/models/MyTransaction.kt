package com.makeappssimple.abhimanyu.financemanager.android.models

data class MyTransaction(
    val id: Int,
    val title: String = "",
    val description: String = "",
    val amount: Amount,
    val category: Category,
    val creationTimestamp: Timestamp,
    val transactionTimestamp: Timestamp,
)

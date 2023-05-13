package com.makeappssimple.abhimanyu.financemanager.android.core.model

data class TransactionData(
    val transaction: Transaction,
    val category: Category?,
    val sourceFrom: Source?,
    val sourceTo: Source?,
    val transactionFor: TransactionFor,
)

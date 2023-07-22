package com.makeappssimple.abhimanyu.financemanager.android.core.model

data class TransactionData(
    val transaction: Transaction,
    val category: Category?,
    val accountFrom: Account?,
    val accountTo: Account?,
    val transactionFor: TransactionFor,
)

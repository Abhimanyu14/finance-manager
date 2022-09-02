package com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model

import com.makeappssimple.abhimanyu.financemanager.android.core.database.amount.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.model.TransactionFor

data class TransactionDetail(
    val amount: Amount,
    val category: Category,
    val id: Int = 0,
    val creationTimestamp: Long,
    val transactionTimestamp: Long,
    val sourceFrom: Source?,
    val sourceTo: Source?,
    val description: String,
    val title: String,
    val transactionFor: TransactionFor,
    val transactionType: TransactionType,
) {

    constructor(
        category: Category,
        sourceFrom: Source?,
        sourceTo: Source?,
        transaction: Transaction,
        transactionFor: TransactionFor,
    ) : this(
        amount = transaction.amount,
        category = category,
        id = transaction.id,
        creationTimestamp = transaction.creationTimestamp,
        transactionTimestamp = transaction.transactionTimestamp,
        sourceFrom = sourceFrom,
        sourceTo = sourceTo,
        description = transaction.description,
        title = transaction.title,
        transactionFor = transactionFor,
        transactionType = transaction.transactionType,
    )
}

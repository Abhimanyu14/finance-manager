package com.makeappssimple.abhimanyu.financemanager.android.core.data.model

import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionDataEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionData

fun TransactionData.asEntity(): TransactionDataEntity {
    return TransactionDataEntity(
        transaction = transaction.asEntity(),
        category = category?.asEntity(),
        sourceFrom = sourceFrom?.asEntity(),
        sourceTo = sourceTo?.asEntity(),
        transactionFor = transactionFor.asEntity(),
    )
}

package com.makeappssimple.abhimanyu.financemanager.android.core.data.model

import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Transaction

public fun Transaction.asEntity(): TransactionEntity {
    return TransactionEntity(
        amount = amount.asEntity(),
        categoryId = categoryId,
        id = id,
        originalTransactionId = originalTransactionId,
        accountFromId = accountFromId,
        accountToId = accountToId,
        transactionForId = transactionForId,
        refundTransactionIds = refundTransactionIds,
        creationTimestamp = creationTimestamp,
        transactionTimestamp = transactionTimestamp,
        description = description,
        title = title,
        transactionType = transactionType,
    )
}

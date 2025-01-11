package com.makeappssimple.abhimanyu.financemanager.android.core.data.model

import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionDataEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionData

public fun TransactionData.asEntity(): TransactionDataEntity {
    return TransactionDataEntity(
        transaction = transaction.asEntity(),
        category = category?.asEntity(),
        accountFrom = accountFrom?.asEntity(),
        accountTo = accountTo?.asEntity(),
        transactionFor = transactionFor.asEntity(),
    )
}

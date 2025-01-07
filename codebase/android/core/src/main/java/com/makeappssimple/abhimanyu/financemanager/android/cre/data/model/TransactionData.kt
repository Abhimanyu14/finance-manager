package com.makeappssimple.abhimanyu.financemanager.android.cre.data.model

import com.makeappssimple.abhimanyu.financemanager.android.cre.database.model.TransactionDataEntity
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.TransactionData

public fun TransactionData.asEntity(): TransactionDataEntity {
    return TransactionDataEntity(
        transaction = transaction.asEntity(),
        category = category?.asEntity(),
        accountFrom = accountFrom?.asEntity(),
        accountTo = accountTo?.asEntity(),
        transactionFor = transactionFor.asEntity(),
    )
}

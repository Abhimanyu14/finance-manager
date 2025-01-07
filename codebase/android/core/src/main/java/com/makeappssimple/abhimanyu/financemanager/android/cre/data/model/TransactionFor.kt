package com.makeappssimple.abhimanyu.financemanager.android.cre.data.model

import com.makeappssimple.abhimanyu.financemanager.android.cre.database.model.TransactionForEntity
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.TransactionFor

public fun TransactionFor.asEntity(): TransactionForEntity {
    return TransactionForEntity(
        id = id,
        title = title,
    )
}

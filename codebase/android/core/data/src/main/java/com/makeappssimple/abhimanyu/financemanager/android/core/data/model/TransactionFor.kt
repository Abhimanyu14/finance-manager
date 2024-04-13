package com.makeappssimple.abhimanyu.financemanager.android.core.data.model

import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionForEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor

public fun TransactionFor.asEntity(): TransactionForEntity {
    return TransactionForEntity(
        id = id,
        title = title,
    )
}

package com.makeappssimple.abhimanyu.financemanager.android.core.data.model

import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.AmountEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Amount

public fun Amount.asEntity(): AmountEntity {
    return AmountEntity(
        currency = currency,
        value = value,
    )
}

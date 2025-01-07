package com.makeappssimple.abhimanyu.financemanager.android.cre.data.model

import com.makeappssimple.abhimanyu.financemanager.android.cre.database.model.AmountEntity
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.Amount

public fun Amount.asEntity(): AmountEntity {
    return AmountEntity(
        currency = currency,
        value = value,
    )
}

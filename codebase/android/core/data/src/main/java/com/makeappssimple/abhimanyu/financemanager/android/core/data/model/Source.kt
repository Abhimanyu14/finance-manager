package com.makeappssimple.abhimanyu.financemanager.android.core.data.model

import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.SourceEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Source

// TODO(Abhi) - Source to Account rename migration
fun Source.asEntity(): SourceEntity {
    return SourceEntity(
        balanceAmount = balanceAmount.asEntity(),
        id = id,
        type = type,
        name = name,
    )
}

package com.makeappssimple.abhimanyu.financemanager.android.core.data.model

import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.AccountEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account

fun Account.asEntity(): AccountEntity {
    return AccountEntity(
        balanceAmount = balanceAmount.asEntity(),
        id = id,
        type = type,
        minimumAccountBalanceAmount = minimumAccountBalanceAmount?.asEntity(),
        name = name,
    )
}

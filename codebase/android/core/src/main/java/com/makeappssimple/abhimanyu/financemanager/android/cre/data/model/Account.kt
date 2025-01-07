package com.makeappssimple.abhimanyu.financemanager.android.cre.data.model

import com.makeappssimple.abhimanyu.financemanager.android.cre.database.model.AccountEntity
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.Account

public fun Account.asEntity(): AccountEntity {
    return AccountEntity(
        balanceAmount = balanceAmount.asEntity(),
        id = id,
        type = type,
        minimumAccountBalanceAmount = minimumAccountBalanceAmount?.asEntity(),
        name = name,
    )
}

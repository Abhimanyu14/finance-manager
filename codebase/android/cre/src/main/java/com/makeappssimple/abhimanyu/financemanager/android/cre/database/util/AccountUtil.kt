package com.makeappssimple.abhimanyu.financemanager.android.cre.database.util

import com.makeappssimple.abhimanyu.financemanager.android.cre.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.extensions.map
import com.makeappssimple.abhimanyu.financemanager.android.cre.database.model.AccountEntity
import com.makeappssimple.abhimanyu.financemanager.android.cre.database.model.AmountEntity
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.AccountType
import kotlinx.collections.immutable.ImmutableList

public fun sanitizeAccounts(
    accounts: ImmutableList<AccountEntity>,
): ImmutableList<AccountEntity> {
    return accounts.map {
        if (it.type == AccountType.BANK && it.minimumAccountBalanceAmount.isNull()) {
            it.copy(
                minimumAccountBalanceAmount = AmountEntity(
                    value = 0,
                ),
            )
        } else {
            it
        }
    }
}

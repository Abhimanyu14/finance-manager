package com.makeappssimple.abhimanyu.financemanager.android.core.database.util

import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.map
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.AccountEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.AmountEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.model.AccountType
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

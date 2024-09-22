package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orZero
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import kotlinx.collections.immutable.ImmutableList
import javax.inject.Inject

public class GetAccountsTotalMinimumBalanceAmountValueUseCase @Inject constructor() {
    public operator fun invoke(
        allAccounts: ImmutableList<Account>,
    ): Long {
        return allAccounts.sumOf { account ->
            account.minimumAccountBalanceAmount?.value.orZero()
        }
    }
}

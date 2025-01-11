package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.usecase

import com.makeappssimple.abhimanyu.financemanager.android.cre.data.usecase.transaction.CheckIfAccountIsUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.Account
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import javax.inject.Inject

public class GetIsAccountsUsedInTransactionFlowUseCase @Inject constructor(
    private val checkIfAccountIsUsedInTransactionsUseCase: CheckIfAccountIsUsedInTransactionsUseCase,
) {
    public suspend operator fun invoke(
        allAccounts: ImmutableList<Account>,
    ): ImmutableMap<Int, Boolean> {
        return allAccounts.associate { account ->
            account.id to checkIfAccountIsUsedInTransactionsUseCase(
                accountId = account.id,
            )
        }.toImmutableMap()
    }
}

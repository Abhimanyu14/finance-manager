package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account

import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.CheckIfAccountIsUsedInTransactionsUseCase
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

public class GetIsAccountsUsedInTransactionFlowUseCase @Inject constructor(
    private val getAllAccountsFlowUseCase: GetAllAccountsFlowUseCase,
    private val checkIfAccountIsUsedInTransactionsUseCase: CheckIfAccountIsUsedInTransactionsUseCase,
) {
    public operator fun invoke(): Flow<ImmutableMap<Int, Boolean>> {
        return getAllAccountsFlowUseCase().map { accounts ->
            accounts.associate { account ->
                account.id to checkIfAccountIsUsedInTransactionsUseCase(
                    accountId = account.id,
                )
            }.toImmutableMap()
        }
    }
}

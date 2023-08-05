package com.makeappssimple.abhimanyu.financemanager.android.core.data.account.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orZero
import com.makeappssimple.abhimanyu.financemanager.android.core.data.account.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface GetAccountsTotalMinimumBalanceAmountValueUseCase {
    operator fun invoke(): Flow<Long>
}

class GetAccountsTotalMinimumBalanceAmountValueUseCaseImpl(
    private val accountRepository: AccountRepository,
) : GetAccountsTotalMinimumBalanceAmountValueUseCase {
    override operator fun invoke(): Flow<Long> {
        return accountRepository.getAllAccountsFlow().map {
            it.sumOf { account ->
                account.minimumAccountBalanceAmount?.value.orZero()
            }
        }
    }
}

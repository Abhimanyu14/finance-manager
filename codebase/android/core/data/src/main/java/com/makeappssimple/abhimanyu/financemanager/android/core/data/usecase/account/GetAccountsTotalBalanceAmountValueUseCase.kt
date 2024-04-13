package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.account.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

public interface GetAccountsTotalBalanceAmountValueUseCase {
    public operator fun invoke(): Flow<Long>
}

public class GetAccountsTotalBalanceAmountValueUseCaseImpl(
    private val accountRepository: AccountRepository,
) : GetAccountsTotalBalanceAmountValueUseCase {
    override operator fun invoke(): Flow<Long> {
        return accountRepository.getAllAccountsFlow().map {
            it.sumOf { account ->
                account.balanceAmount.value
            }
        }
    }
}

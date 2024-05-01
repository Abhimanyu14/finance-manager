package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account

import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orZero
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.account.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

public class GetAccountsTotalMinimumBalanceAmountValueUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
) {
    public operator fun invoke(): Flow<Long> {
        return accountRepository.getAllAccountsFlow().map {
            it.sumOf { account ->
                account.minimumAccountBalanceAmount?.value.orZero()
            }
        }
    }
}

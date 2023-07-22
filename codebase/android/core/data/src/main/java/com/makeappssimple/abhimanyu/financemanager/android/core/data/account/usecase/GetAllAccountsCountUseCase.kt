package com.makeappssimple.abhimanyu.financemanager.android.core.data.account.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.account.repository.AccountRepository

interface GetAllAccountsCountUseCase {
    suspend operator fun invoke(): Int
}

class GetAllAccountsCountUseCaseImpl(
    private val accountRepository: AccountRepository,
) : GetAllAccountsCountUseCase {
    override suspend operator fun invoke(): Int {
        return accountRepository.getAllAccountsCount()
    }
}

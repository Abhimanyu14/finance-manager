package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.account.AccountRepository

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

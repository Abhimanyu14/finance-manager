package com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.repository.AccountRepository

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

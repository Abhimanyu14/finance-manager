package com.makeappssimple.abhimanyu.financemanager.android.core.data.account.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.account.repository.AccountRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account

interface GetAllAccountsUseCase {
    suspend operator fun invoke(): List<Account>
}

class GetAllAccountsUseCaseImpl(
    private val accountRepository: AccountRepository,
) : GetAllAccountsUseCase {
    override suspend operator fun invoke(): List<Account> {
        return accountRepository.getAllAccounts()
    }
}

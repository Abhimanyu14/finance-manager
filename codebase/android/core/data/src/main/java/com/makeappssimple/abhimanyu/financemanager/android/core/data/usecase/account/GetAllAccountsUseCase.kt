package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.account.AccountRepository
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

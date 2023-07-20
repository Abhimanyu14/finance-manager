package com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.repository.AccountRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Source

interface GetAllAccountsUseCase {
    suspend operator fun invoke(): List<Source>
}

class GetAllAccountsUseCaseImpl(
    private val accountRepository: AccountRepository,
) : GetAllAccountsUseCase {
    override suspend operator fun invoke(): List<Source> {
        return accountRepository.getAllAccounts()
    }
}

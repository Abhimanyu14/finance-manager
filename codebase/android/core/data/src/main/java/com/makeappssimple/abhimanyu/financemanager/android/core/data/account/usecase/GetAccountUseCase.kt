package com.makeappssimple.abhimanyu.financemanager.android.core.data.account.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.account.repository.AccountRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account

interface GetAccountUseCase {
    suspend operator fun invoke(
        id: Int,
    ): Account?
}

class GetAccountUseCaseImpl(
    private val accountRepository: AccountRepository,
) : GetAccountUseCase {
    override suspend operator fun invoke(
        id: Int,
    ): Account? {
        return accountRepository.getAccount(
            id = id,
        )
    }
}

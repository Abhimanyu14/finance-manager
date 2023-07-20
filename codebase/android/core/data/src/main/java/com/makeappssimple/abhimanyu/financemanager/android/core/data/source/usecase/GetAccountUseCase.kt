package com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.repository.AccountRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Source

interface GetAccountUseCase {
    suspend operator fun invoke(
        id: Int,
    ): Source?
}

class GetAccountUseCaseImpl(
    private val accountRepository: AccountRepository,
) : GetAccountUseCase {
    override suspend operator fun invoke(
        id: Int,
    ): Source? {
        return accountRepository.getAccount(
            id = id,
        )
    }
}

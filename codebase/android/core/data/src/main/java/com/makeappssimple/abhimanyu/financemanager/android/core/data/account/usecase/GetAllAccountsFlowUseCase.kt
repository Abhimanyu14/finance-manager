package com.makeappssimple.abhimanyu.financemanager.android.core.data.account.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.account.repository.AccountRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import kotlinx.coroutines.flow.Flow

interface GetAllAccountsFlowUseCase {
    operator fun invoke(): Flow<List<Account>>
}

class GetAllAccountsFlowUseCaseImpl(
    private val accountRepository: AccountRepository,
) : GetAllAccountsFlowUseCase {
    override operator fun invoke(): Flow<List<Account>> {
        return accountRepository.getAllAccountsFlow()
    }
}

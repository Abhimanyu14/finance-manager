package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.account.AccountRepository
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

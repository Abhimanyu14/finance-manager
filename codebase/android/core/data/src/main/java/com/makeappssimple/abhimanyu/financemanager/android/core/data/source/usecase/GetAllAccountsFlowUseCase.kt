package com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.repository.AccountRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Source
import kotlinx.coroutines.flow.Flow

interface GetAllAccountsFlowUseCase {
    operator fun invoke(): Flow<List<Source>>
}

class GetAllAccountsFlowUseCaseImpl(
    private val accountRepository: AccountRepository,
) : GetAllAccountsFlowUseCase {
    override operator fun invoke(): Flow<List<Source>> {
        return accountRepository.getAllAccountsFlow()
    }
}

package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.account.AccountRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import kotlinx.coroutines.flow.Flow

public interface GetAllAccountsFlowUseCase {
    public operator fun invoke(): Flow<List<Account>>
}

public class GetAllAccountsFlowUseCaseImpl(
    private val accountRepository: AccountRepository,
) : GetAllAccountsFlowUseCase {
    override operator fun invoke(): Flow<List<Account>> {
        return accountRepository.getAllAccountsFlow()
    }
}

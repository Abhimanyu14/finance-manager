package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.account.AccountRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

public class GetAllAccountsFlowUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
) {
    public operator fun invoke(): Flow<List<Account>> {
        return accountRepository.getAllAccountsFlow()
    }
}

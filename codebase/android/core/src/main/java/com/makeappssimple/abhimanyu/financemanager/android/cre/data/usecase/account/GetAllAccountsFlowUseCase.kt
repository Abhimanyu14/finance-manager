package com.makeappssimple.abhimanyu.financemanager.android.cre.data.usecase.account

import com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.account.AccountRepository
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.Account
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

public class GetAllAccountsFlowUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
) {
    public operator fun invoke(): Flow<ImmutableList<Account>> {
        return accountRepository.getAllAccountsFlow()
    }
}

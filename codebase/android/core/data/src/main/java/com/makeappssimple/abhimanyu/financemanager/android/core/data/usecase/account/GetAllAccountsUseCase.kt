package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.account.AccountRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account

public interface GetAllAccountsUseCase {
    public suspend operator fun invoke(): List<Account>
}

public class GetAllAccountsUseCaseImpl(
    private val accountRepository: AccountRepository,
) : GetAllAccountsUseCase {
    override suspend operator fun invoke(): List<Account> {
        return accountRepository.getAllAccounts()
    }
}

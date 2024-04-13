package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.account.AccountRepository

public interface GetAllAccountsCountUseCase {
    public suspend operator fun invoke(): Int
}

public class GetAllAccountsCountUseCaseImpl(
    private val accountRepository: AccountRepository,
) : GetAllAccountsCountUseCase {
    override suspend operator fun invoke(): Int {
        return accountRepository.getAllAccountsCount()
    }
}

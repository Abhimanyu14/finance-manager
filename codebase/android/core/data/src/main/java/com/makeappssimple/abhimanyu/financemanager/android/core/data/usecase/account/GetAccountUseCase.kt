package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.account.AccountRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account

public interface GetAccountUseCase {
    public suspend operator fun invoke(
        id: Int,
    ): Account?
}

public class GetAccountUseCaseImpl(
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

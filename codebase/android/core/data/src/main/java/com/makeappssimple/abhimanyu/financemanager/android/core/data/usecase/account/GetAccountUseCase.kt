package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.account.AccountRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import javax.inject.Inject

public class GetAccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
) {
    public suspend operator fun invoke(
        id: Int,
    ): Account? {
        return accountRepository.getAccount(
            id = id,
        )
    }
}

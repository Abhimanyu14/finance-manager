package com.makeappssimple.abhimanyu.financemanager.android.cre.data.usecase.account

import com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.account.AccountRepository
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.Account
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

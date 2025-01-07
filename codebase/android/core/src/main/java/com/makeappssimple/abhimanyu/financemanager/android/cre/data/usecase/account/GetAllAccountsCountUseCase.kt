package com.makeappssimple.abhimanyu.financemanager.android.cre.data.usecase.account

import com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.account.AccountRepository
import javax.inject.Inject

public class GetAllAccountsCountUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
) {
    public suspend operator fun invoke(): Int {
        return accountRepository.getAllAccountsCount()
    }
}

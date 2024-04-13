package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.account.AccountRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account

public interface InsertAccountsUseCase {
    public suspend operator fun invoke(
        vararg accounts: Account,
    ): List<Long>
}

public class InsertAccountsUseCaseImpl(
    private val myPreferencesRepository: MyPreferencesRepository,
    private val accountRepository: AccountRepository,
) : InsertAccountsUseCase {
    override suspend operator fun invoke(
        vararg accounts: Account,
    ): List<Long> {
        myPreferencesRepository.setLastDataChangeTimestamp()
        return accountRepository.insertAccounts(
            accounts = accounts,
        )
    }
}

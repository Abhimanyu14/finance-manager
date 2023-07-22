package com.makeappssimple.abhimanyu.financemanager.android.core.data.account.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.account.repository.AccountRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.preferences.repository.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account

interface InsertAccountsUseCase {
    suspend operator fun invoke(
        vararg accounts: Account,
    )
}

class InsertAccountsUseCaseImpl(
    private val myPreferencesRepository: MyPreferencesRepository,
    private val accountRepository: AccountRepository,
) : InsertAccountsUseCase {
    override suspend operator fun invoke(
        vararg accounts: Account,
    ) {
        myPreferencesRepository.setLastDataChangeTimestamp()
        return accountRepository.insertAccounts(
            accounts = accounts,
        )
    }
}

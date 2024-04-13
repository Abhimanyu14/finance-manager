package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.account.AccountRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account

interface DeleteAccountsUseCase {
    suspend operator fun invoke(
        vararg accounts: Account,
    ): Boolean
}

class DeleteAccountsUseCaseImpl(
    private val myPreferencesRepository: MyPreferencesRepository,
    private val accountRepository: AccountRepository,
) : DeleteAccountsUseCase {
    override suspend operator fun invoke(
        vararg accounts: Account,
    ): Boolean {
        myPreferencesRepository.setLastDataChangeTimestamp()
        return accountRepository.deleteAccounts(
            accounts = accounts,
        )
    }
}

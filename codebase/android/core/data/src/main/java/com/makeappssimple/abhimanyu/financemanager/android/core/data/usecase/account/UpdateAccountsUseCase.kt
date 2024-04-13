package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.account.AccountRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account

public interface UpdateAccountsUseCase {
    public suspend operator fun invoke(
        vararg accounts: Account,
    ): Boolean
}

public class UpdateAccountsUseCaseImpl(
    private val myPreferencesRepository: MyPreferencesRepository,
    private val accountRepository: AccountRepository,
) : UpdateAccountsUseCase {
    override suspend operator fun invoke(
        vararg accounts: Account,
    ): Boolean {
        myPreferencesRepository.setLastDataChangeTimestamp()
        return accountRepository.updateAccounts(
            accounts = accounts,
        )
    }
}

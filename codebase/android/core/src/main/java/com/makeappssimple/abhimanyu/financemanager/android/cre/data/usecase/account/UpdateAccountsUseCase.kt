package com.makeappssimple.abhimanyu.financemanager.android.cre.data.usecase.account

import com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.account.AccountRepository
import com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.Account
import javax.inject.Inject

public class UpdateAccountsUseCase @Inject constructor(
    private val myPreferencesRepository: MyPreferencesRepository,
    private val accountRepository: AccountRepository,
) {
    public suspend operator fun invoke(
        vararg accounts: Account,
    ): Boolean {
        myPreferencesRepository.setLastDataChangeTimestamp()
        return accountRepository.updateAccounts(
            accounts = accounts,
        )
    }
}

package com.makeappssimple.abhimanyu.financemanager.android.cre.data.usecase.account

import com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.account.AccountRepository
import com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.Account
import kotlinx.collections.immutable.ImmutableList
import javax.inject.Inject

public class InsertAccountsUseCase @Inject constructor(
    private val myPreferencesRepository: MyPreferencesRepository,
    private val accountRepository: AccountRepository,
) {
    public suspend operator fun invoke(
        vararg accounts: Account,
    ): ImmutableList<Long> {
        myPreferencesRepository.setLastDataChangeTimestamp()
        return accountRepository.insertAccounts(
            accounts = accounts,
        )
    }
}

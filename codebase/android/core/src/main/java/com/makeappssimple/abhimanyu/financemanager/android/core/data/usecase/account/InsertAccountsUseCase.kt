package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.account.AccountRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import kotlinx.collections.immutable.ImmutableList
import javax.inject.Inject

public class InsertAccountsUseCase @Inject constructor(
    private val myPreferencesRepository: MyPreferencesRepository,
    private val accountRepository: AccountRepository,
) {
    public suspend operator fun invoke(
        vararg accounts: Account,
    ): ImmutableList<Long> {
        myPreferencesRepository.updateLastDataChangeTimestamp()
        return accountRepository.insertAccounts(
            accounts = accounts,
        )
    }
}

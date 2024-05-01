package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.account.AccountRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import javax.inject.Inject

public class UpdateAccountsBalanceAmountUseCase @Inject constructor(
    private val myPreferencesRepository: MyPreferencesRepository,
    private val accountRepository: AccountRepository,
) {
    public suspend operator fun invoke(
        accountsBalanceAmountChange: List<Pair<Int, Long>>,
    ): Boolean {
        myPreferencesRepository.setLastDataChangeTimestamp()
        return accountRepository.updateAccountBalanceAmount(
            accountsBalanceAmountChange = accountsBalanceAmountChange,
        )
    }
}

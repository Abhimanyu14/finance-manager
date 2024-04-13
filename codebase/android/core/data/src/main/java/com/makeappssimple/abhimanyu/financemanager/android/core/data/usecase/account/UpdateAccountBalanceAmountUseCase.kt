package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.account.AccountRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository

public interface UpdateAccountsBalanceAmountUseCase {
    public suspend operator fun invoke(
        accountsBalanceAmountChange: List<Pair<Int, Long>>,
    ): Boolean
}

public class UpdateAccountsBalanceAmountUseCaseImpl(
    private val myPreferencesRepository: MyPreferencesRepository,
    private val accountRepository: AccountRepository,
) : UpdateAccountsBalanceAmountUseCase {
    override suspend operator fun invoke(
        accountsBalanceAmountChange: List<Pair<Int, Long>>,
    ): Boolean {
        myPreferencesRepository.setLastDataChangeTimestamp()
        return accountRepository.updateAccountBalanceAmount(
            accountsBalanceAmountChange = accountsBalanceAmountChange,
        )
    }
}

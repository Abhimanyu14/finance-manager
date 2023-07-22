package com.makeappssimple.abhimanyu.financemanager.android.core.data.account.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.account.repository.AccountRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.preferences.repository.MyPreferencesRepository

interface UpdateAccountsBalanceAmountUseCase {
    suspend operator fun invoke(
        accountsBalanceAmountChange: List<Pair<Int, Long>>,
    )
}

class UpdateAccountsBalanceAmountUseCaseImpl(
    private val myPreferencesRepository: MyPreferencesRepository,
    private val accountRepository: AccountRepository,
) : UpdateAccountsBalanceAmountUseCase {
    override suspend operator fun invoke(
        accountsBalanceAmountChange: List<Pair<Int, Long>>,
    ) {
        myPreferencesRepository.setLastDataChangeTimestamp()
        return accountRepository.updateAccountBalanceAmount(
            accountsBalanceAmountChange = accountsBalanceAmountChange,
        )
    }
}

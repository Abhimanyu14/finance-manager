package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.account.AccountRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository

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

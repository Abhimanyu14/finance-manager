package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.account.AccountRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Amount
import javax.inject.Inject

public class InsertAccountUseCase @Inject constructor(
    private val myPreferencesRepository: MyPreferencesRepository,
    private val accountRepository: AccountRepository,
) {
    public suspend operator fun invoke(
        accountType: AccountType?,
        minimumAccountBalanceAmountValue: Long,
        name: String,
    ): Long {
        if (accountType == null) {
            return -1
        }
        val minimumAccountBalanceAmount = if (accountType == AccountType.BANK) {
            Amount(
                value = minimumAccountBalanceAmountValue,
            )
        } else {
            null
        }
        myPreferencesRepository.updateLastDataChangeTimestamp()
        return accountRepository.insertAccounts(
            Account(
                type = accountType,
                minimumAccountBalanceAmount = minimumAccountBalanceAmount,
                name = name,
            ),
        ).first()
    }
}

package com.makeappssimple.abhimanyu.financemanager.android.cre.data.usecase.account

import com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.account.AccountRepository
import com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.AccountType
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.Amount
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
        myPreferencesRepository.setLastDataChangeTimestamp()
        return accountRepository.insertAccounts(
            Account(
                type = accountType,
                minimumAccountBalanceAmount = minimumAccountBalanceAmount,
                name = name,
            ),
        ).first()
    }
}

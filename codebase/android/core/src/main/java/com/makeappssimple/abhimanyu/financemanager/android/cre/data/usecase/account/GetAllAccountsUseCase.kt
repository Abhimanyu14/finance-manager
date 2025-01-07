package com.makeappssimple.abhimanyu.financemanager.android.cre.data.usecase.account

import com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.account.AccountRepository
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.sortOrder
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import javax.inject.Inject

public class GetAllAccountsUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
) {
    public suspend operator fun invoke(): ImmutableList<Account> {
        return accountRepository.getAllAccounts().sortedWith(
            comparator = compareBy<Account> {
                it.type.sortOrder
            }.thenByDescending {
                it.balanceAmount.value
            }
        ).toImmutableList()
    }
}

package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.account.AccountRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.sortOrder
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

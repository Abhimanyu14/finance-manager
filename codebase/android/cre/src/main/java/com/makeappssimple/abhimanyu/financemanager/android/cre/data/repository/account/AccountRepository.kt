package com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.account

import com.makeappssimple.abhimanyu.financemanager.android.cre.model.Account
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

public interface AccountRepository {
    public fun getAllAccountsFlow(): Flow<ImmutableList<Account>>

    public suspend fun getAllAccounts(): ImmutableList<Account>

    public suspend fun getAllAccountsCount(): Int

    public suspend fun getAccount(
        id: Int,
    ): Account?

    public suspend fun getAccounts(
        ids: ImmutableList<Int>,
    ): ImmutableList<Account>?

    public suspend fun insertAccounts(
        vararg accounts: Account,
    ): ImmutableList<Long>

    public suspend fun updateAccountBalanceAmount(
        accountsBalanceAmountChange: ImmutableList<Pair<Int, Long>>,
    ): Boolean

    public suspend fun updateAccounts(
        vararg accounts: Account,
    ): Boolean

    public suspend fun deleteAccount(
        id: Int,
    ): Boolean

    public suspend fun deleteAccounts(
        vararg accounts: Account,
    ): Boolean
}

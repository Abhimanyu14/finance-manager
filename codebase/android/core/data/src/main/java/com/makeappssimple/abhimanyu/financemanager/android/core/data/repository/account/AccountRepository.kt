package com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.account

import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import kotlinx.coroutines.flow.Flow

public interface AccountRepository {
    public fun getAllAccountsFlow(): Flow<List<Account>>

    public suspend fun getAllAccounts(): List<Account>

    public suspend fun getAllAccountsCount(): Int

    public suspend fun getAccount(
        id: Int,
    ): Account?

    public suspend fun getAccounts(
        ids: List<Int>,
    ): List<Account>?

    public suspend fun insertAccounts(
        vararg accounts: Account,
    ): List<Long>

    public suspend fun updateAccountBalanceAmount(
        accountsBalanceAmountChange: List<Pair<Int, Long>>,
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

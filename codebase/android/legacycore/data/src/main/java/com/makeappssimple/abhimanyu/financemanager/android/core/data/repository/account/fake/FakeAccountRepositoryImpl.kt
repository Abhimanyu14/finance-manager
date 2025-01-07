package com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.account.fake

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.account.AccountRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

public class FakeAccountRepositoryImpl : AccountRepository {
    override fun getAllAccountsFlow(): Flow<ImmutableList<Account>> {
        return flow {
            persistentListOf<Account>()
        }
    }

    override suspend fun getAllAccounts(): ImmutableList<Account> {
        return persistentListOf()
    }

    override suspend fun getAllAccountsCount(): Int {
        return 0
    }

    override suspend fun getAccount(
        id: Int,
    ): Account? {
        return null
    }

    override suspend fun getAccounts(
        ids: ImmutableList<Int>,
    ): ImmutableList<Account> {
        return persistentListOf()
    }

    override suspend fun insertAccounts(
        vararg accounts: Account,
    ): ImmutableList<Long> {
        return persistentListOf()
    }

    @androidx.room.Transaction
    override suspend fun updateAccountBalanceAmount(
        accountsBalanceAmountChange: ImmutableList<Pair<Int, Long>>,
    ): Boolean {
        return false
    }

    override suspend fun updateAccounts(
        vararg accounts: Account,
    ): Boolean {
        return false
    }

    override suspend fun deleteAccount(
        id: Int,
    ): Boolean {
        return false
    }

    override suspend fun deleteAccounts(
        vararg accounts: Account,
    ): Boolean {
        return false
    }
}

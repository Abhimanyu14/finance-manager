package com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.account.fake

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.account.AccountRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeAccountRepositoryImpl : AccountRepository {
    override fun getAllAccountsFlow(): Flow<List<Account>> {
        return flow {
            emptyList<Account>()
        }
    }

    override suspend fun getAllAccounts(): List<Account> {
        return emptyList()
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
        ids: List<Int>,
    ): List<Account> {
        return emptyList()
    }

    override suspend fun insertAccounts(
        vararg accounts: Account,
    ): List<Long> {
        return emptyList()
    }

    @androidx.room.Transaction
    override suspend fun updateAccountBalanceAmount(
        accountsBalanceAmountChange: List<Pair<Int, Long>>,
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

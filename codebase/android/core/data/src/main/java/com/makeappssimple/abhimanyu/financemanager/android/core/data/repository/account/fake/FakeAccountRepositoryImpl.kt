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
    ) {
    }

    @androidx.room.Transaction
    override suspend fun updateAccountBalanceAmount(
        accountsBalanceAmountChange: List<Pair<Int, Long>>,
    ) {
    }

    override suspend fun updateAccounts(
        vararg accounts: Account,
    ) {
    }

    override suspend fun deleteAccount(
        id: Int,
    ) {
    }

    override suspend fun deleteAccounts(
        vararg accounts: Account,
    ) {
    }
}

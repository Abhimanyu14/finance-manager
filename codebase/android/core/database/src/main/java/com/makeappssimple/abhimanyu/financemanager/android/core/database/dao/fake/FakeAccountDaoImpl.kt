package com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.fake

import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.AccountDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.AccountEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

public class FakeAccountDaoImpl : AccountDao {
    override fun getAllAccountsFlow(): Flow<List<AccountEntity>> {
        return emptyFlow()
    }

    override suspend fun getAllAccounts(): List<AccountEntity> {
        return emptyList()
    }

    override suspend fun getAllAccountsCount(): Int {
        return 0
    }

    override suspend fun getAccount(
        id: Int,
    ): AccountEntity? {
        return null
    }

    override suspend fun getAccounts(
        ids: List<Int>,
    ): List<AccountEntity> {
        return emptyList()
    }

    override suspend fun insertAccounts(
        vararg accounts: AccountEntity,
    ): List<Long> {
        return emptyList()
    }

    override suspend fun updateAccounts(
        vararg accounts: AccountEntity,
    ): Int {
        return 0
    }

    override suspend fun deleteAccount(
        id: Int,
    ): Int {
        return 0
    }

    override suspend fun deleteAccounts(
        vararg accounts: AccountEntity,
    ): Int {
        return 0
    }

    override suspend fun deleteAllAccounts(): Int {
        return 0
    }
}

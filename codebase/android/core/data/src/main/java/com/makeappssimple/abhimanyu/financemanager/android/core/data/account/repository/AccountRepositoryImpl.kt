package com.makeappssimple.abhimanyu.financemanager.android.core.data.account.repository

import com.makeappssimple.abhimanyu.financemanager.android.core.data.model.asEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.AccountDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.asExternalModel
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.updateBalanceAmount
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AccountRepositoryImpl(
    private val accountDao: AccountDao,
) : AccountRepository {
    override fun getAllAccountsFlow(): Flow<List<Account>> {
        return accountDao.getAllAccountsFlow().map {
            it.map { accountEntity ->
                accountEntity.asExternalModel()
            }
        }
    }

    override suspend fun getAllAccounts(): List<Account> {
        return accountDao.getAllAccounts().map {
            it.asExternalModel()
        }
    }

    override suspend fun getAllAccountsCount(): Int {
        return accountDao.getAllAccountsCount()
    }

    override suspend fun getAccount(
        id: Int,
    ): Account? {
        return accountDao.getAccount(
            id = id,
        )?.asExternalModel()
    }

    override suspend fun getAccounts(
        ids: List<Int>,
    ): List<Account> {
        return accountDao.getAccounts(
            ids = ids,
        ).map {
            it.asExternalModel()
        }
    }

    override suspend fun insertAccounts(
        vararg accounts: Account,
    ) {
        accountDao.insertAccounts(
            accounts = accounts.map {
                it.asEntity()
            }.toTypedArray(),
        )
    }

    @androidx.room.Transaction
    override suspend fun updateAccountBalanceAmount(
        accountsBalanceAmountChange: List<Pair<Int, Long>>,
    ) {
        val accountIds = accountsBalanceAmountChange.map {
            it.first
        }
        val accounts = getAccounts(
            ids = accountIds,
        )
        val updatedAccounts = accounts.mapIndexed { index, account ->
            account.updateBalanceAmount(
                updatedBalanceAmount = account.balanceAmount.value + accountsBalanceAmountChange[index].second,
            )
        }
        updateAccounts(
            accounts = updatedAccounts.toTypedArray(),
        )
    }

    override suspend fun updateAccounts(
        vararg accounts: Account,
    ) {
        accountDao.updateAccounts(
            accounts = accounts.map {
                it.asEntity()
            }.toTypedArray(),
        )
    }

    override suspend fun deleteAccount(
        id: Int,
    ) {
        accountDao.deleteAccount(
            id = id,
        )
    }

    override suspend fun deleteAccounts(
        vararg accounts: Account,
    ) {
        accountDao.deleteAccounts(
            accounts = accounts.map {
                it.asEntity()
            }.toTypedArray(),
        )
    }
}

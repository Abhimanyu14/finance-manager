package com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.account

import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.data.model.asEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.AccountDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.AccountEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.asExternalModel
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.updateBalanceAmount
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

public class AccountRepositoryImpl(
    private val accountDao: AccountDao,
    private val dispatcherProvider: DispatcherProvider,
) : AccountRepository {
    override fun getAllAccountsFlow(): Flow<List<Account>> {
        return accountDao.getAllAccountsFlow().map {
            it.map(
                transform = AccountEntity::asExternalModel,
            )
        }
    }

    override suspend fun getAllAccounts(): List<Account> {
        return executeOnIoDispatcher {
            accountDao.getAllAccounts().map(
                transform = AccountEntity::asExternalModel,
            )
        }
    }

    override suspend fun getAllAccountsCount(): Int {
        return executeOnIoDispatcher {
            accountDao.getAllAccountsCount()
        }
    }

    override suspend fun getAccount(
        id: Int,
    ): Account? {
        return executeOnIoDispatcher {
            accountDao.getAccount(
                id = id,
            )?.asExternalModel()
        }
    }

    override suspend fun getAccounts(
        ids: List<Int>,
    ): List<Account> {
        return executeOnIoDispatcher {
            accountDao.getAccounts(
                ids = ids,
            ).map(
                transform = AccountEntity::asExternalModel,
            )
        }
    }

    override suspend fun insertAccounts(
        vararg accounts: Account,
    ): List<Long> {
        return executeOnIoDispatcher {
            accountDao.insertAccounts(
                accounts = accounts.map(
                    transform = Account::asEntity,
                ).toTypedArray(),
            )
        }
    }

    @androidx.room.Transaction
    override suspend fun updateAccountBalanceAmount(
        accountsBalanceAmountChange: List<Pair<Int, Long>>,
    ): Boolean {
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
        return updateAccounts(
            accounts = updatedAccounts.toTypedArray(),
        )
    }

    override suspend fun updateAccounts(
        vararg accounts: Account,
    ): Boolean {
        return executeOnIoDispatcher {
            accountDao.updateAccounts(
                accounts = accounts.map(
                    transform = Account::asEntity,
                ).toTypedArray(),
            ) == accounts.size
        }
    }

    override suspend fun deleteAccount(
        id: Int,
    ): Boolean {
        return executeOnIoDispatcher {
            accountDao.deleteAccount(
                id = id,
            ) == 1
        }
    }

    override suspend fun deleteAccounts(
        vararg accounts: Account,
    ): Boolean {
        return executeOnIoDispatcher {
            accountDao.deleteAccounts(
                accounts = accounts.map(
                    transform = Account::asEntity,
                ).toTypedArray(),
            ) == accounts.size
        }
    }

    private suspend fun <T> executeOnIoDispatcher(
        block: suspend CoroutineScope.() -> T,
    ): T {
        return withContext(
            context = dispatcherProvider.io,
            block = block,
        )
    }
}

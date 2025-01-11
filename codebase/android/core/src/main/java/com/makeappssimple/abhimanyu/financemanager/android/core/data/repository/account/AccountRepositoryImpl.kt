package com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.account

import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.map
import com.makeappssimple.abhimanyu.financemanager.android.core.data.model.asEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.AccountDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.AccountEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.asExternalModel
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.updateBalanceAmount
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

public class AccountRepositoryImpl(
    private val accountDao: AccountDao,
    private val dispatcherProvider: DispatcherProvider,
) : AccountRepository {
    override fun getAllAccountsFlow(): Flow<ImmutableList<Account>> {
        return accountDao.getAllAccountsFlow().map {
            it.map(
                transform = AccountEntity::asExternalModel,
            )
        }
    }

    override suspend fun getAllAccounts(): ImmutableList<Account> {
        return dispatcherProvider.executeOnIoDispatcher {
            accountDao.getAllAccounts().map(
                transform = AccountEntity::asExternalModel,
            )
        }
    }

    override suspend fun getAllAccountsCount(): Int {
        return dispatcherProvider.executeOnIoDispatcher {
            accountDao.getAllAccountsCount()
        }
    }

    override suspend fun getAccount(
        id: Int,
    ): Account? {
        return dispatcherProvider.executeOnIoDispatcher {
            accountDao.getAccount(
                id = id,
            )?.asExternalModel()
        }
    }

    override suspend fun getAccounts(
        ids: ImmutableList<Int>,
    ): ImmutableList<Account> {
        return dispatcherProvider.executeOnIoDispatcher {
            accountDao.getAccounts(
                ids = ids,
            ).map(
                transform = AccountEntity::asExternalModel,
            )
        }
    }

    override suspend fun insertAccounts(
        vararg accounts: Account,
    ): ImmutableList<Long> {
        return dispatcherProvider.executeOnIoDispatcher {
            accountDao.insertAccounts(
                accounts = accounts.map(
                    transform = Account::asEntity,
                ).toTypedArray(),
            ).toImmutableList()
        }
    }

    override suspend fun updateAccountBalanceAmount(
        accountsBalanceAmountChange: ImmutableList<Pair<Int, Long>>,
    ): Boolean {
        return dispatcherProvider.executeOnIoDispatcher {
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
    }

    override suspend fun updateAccounts(
        vararg accounts: Account,
    ): Boolean {
        return dispatcherProvider.executeOnIoDispatcher {
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
        return dispatcherProvider.executeOnIoDispatcher {
            accountDao.deleteAccount(
                id = id,
            ) == 1
        }
    }

    override suspend fun deleteAccounts(
        vararg accounts: Account,
    ): Boolean {
        return dispatcherProvider.executeOnIoDispatcher {
            accountDao.deleteAccounts(
                accounts = accounts.map(
                    transform = Account::asEntity,
                ).toTypedArray(),
            ) == accounts.size
        }
    }
}

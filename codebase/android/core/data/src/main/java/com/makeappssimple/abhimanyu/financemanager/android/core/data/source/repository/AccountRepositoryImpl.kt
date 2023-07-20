package com.makeappssimple.abhimanyu.financemanager.android.core.data.source.repository

import com.makeappssimple.abhimanyu.financemanager.android.core.data.model.asEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.AccountDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.asExternalModel
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.model.updateBalanceAmount
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AccountRepositoryImpl(
    private val accountDao: AccountDao,
) : AccountRepository {
    override fun getAllAccountsFlow(): Flow<List<Source>> {
        return accountDao.getAllAccountsFlow().map {
            it.map { sourceEntity ->
                sourceEntity.asExternalModel()
            }
        }
    }

    override suspend fun getAllAccounts(): List<Source> {
        return accountDao.getAllAccounts().map {
            it.asExternalModel()
        }
    }

    override suspend fun getAllAccountsCount(): Int {
        return accountDao.getAllAccountsCount()
    }

    override suspend fun getAccount(
        id: Int,
    ): Source? {
        return accountDao.getAccount(
            id = id,
        )?.asExternalModel()
    }

    override suspend fun getAccounts(
        ids: List<Int>,
    ): List<Source> {
        return accountDao.getAccounts(
            ids = ids,
        ).map {
            it.asExternalModel()
        }
    }

    override suspend fun insertAccounts(
        vararg sources: Source,
    ) {
        accountDao.insertAccounts(
            sources = sources.map {
                it.asEntity()
            }.toTypedArray(),
        )
    }

    @androidx.room.Transaction
    override suspend fun updateAccountBalanceAmount(
        sourcesBalanceAmountChange: List<Pair<Int, Long>>,
    ) {
        val sourceIds = sourcesBalanceAmountChange.map {
            it.first
        }
        val sources = getAccounts(
            ids = sourceIds,
        )
        val updatedSources = sources.mapIndexed { index, source ->
            source.updateBalanceAmount(
                updatedBalanceAmount = source.balanceAmount.value + sourcesBalanceAmountChange[index].second,
            )
        }
        updateAccounts(
            sources = updatedSources.toTypedArray(),
        )
    }

    override suspend fun updateAccounts(
        vararg sources: Source,
    ) {
        accountDao.updateAccounts(
            sources = sources.map {
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
        vararg sources: Source,
    ) {
        accountDao.deleteAccounts(
            sources = sources.map {
                it.asEntity()
            }.toTypedArray(),
        )
    }
}

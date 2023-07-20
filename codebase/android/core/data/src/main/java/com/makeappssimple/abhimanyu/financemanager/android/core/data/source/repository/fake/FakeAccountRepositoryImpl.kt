package com.makeappssimple.abhimanyu.financemanager.android.core.data.source.repository.fake

import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.repository.AccountRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Source
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeAccountRepositoryImpl : AccountRepository {
    override fun getAllAccountsFlow(): Flow<List<Source>> {
        return flow {
            emptyList<Source>()
        }
//        return sourceDao.getAllSourcesFlow().map {
//            it.map { sourceEntity ->
//                sourceEntity.asExternalModel()
//            }
//        }
    }

    override suspend fun getAllAccounts(): List<Source> {
        return emptyList()
//        return sourceDao.getAllSources().map {
//            it.asExternalModel()
//        }
    }

    override suspend fun getAllAccountsCount(): Int {
        return 0
//        return sourceDao.getAllSourcesCount()
    }

    override suspend fun getAccount(
        id: Int,
    ): Source? {
        return null
//        return sourceDao.getSource(
//            id = id,
//        )?.asExternalModel()
    }

    override suspend fun getAccounts(
        ids: List<Int>,
    ): List<Source> {
        return emptyList()
//        return sourceDao.getSources(
//            ids = ids,
//        ).map {
//            it.asExternalModel()
//        }
    }

    override suspend fun insertAccounts(
        vararg sources: Source,
    ) {
//        sourceDao.insertSources(
//            sources = sources.map {
//                it.asEntity()
//            }.toTypedArray(),
//        )
    }

    @androidx.room.Transaction
    override suspend fun updateAccountBalanceAmount(
        sourcesBalanceAmountChange: List<Pair<Int, Long>>,
    ) {
//        val sourceIds = sourcesBalanceAmountChange.map {
//            it.first
//        }
//        val sources = getSources(
//            ids = sourceIds,
//        )
//        val updatedSources = sources.mapIndexed { index, source ->
//            source.updateBalanceAmount(
//                updatedBalanceAmount = source.balanceAmount.value + sourcesBalanceAmountChange[index].second,
//            )
//        }
//        updateSources(
//            sources = updatedSources.toTypedArray(),
//        )
    }

    override suspend fun updateAccounts(
        vararg sources: Source,
    ) {
//        sourceDao.updateSources(
//            sources = sources.map {
//                it.asEntity()
//            }.toTypedArray(),
//        )
    }

    override suspend fun deleteAccount(
        id: Int,
    ) {
//        sourceDao.deleteSource(
//            id = id,
//        )
    }

    override suspend fun deleteAccounts(
        vararg sources: Source,
    ) {
//        sourceDao.deleteSources(
//            sources = sources.map {
//                it.asEntity()
//            }.toTypedArray(),
//        )
    }
}

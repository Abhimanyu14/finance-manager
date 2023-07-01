package com.makeappssimple.abhimanyu.financemanager.android.core.data.source.repository.fake

import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.repository.SourceRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Source
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeSourceRepositoryImpl : SourceRepository {
    override fun getAllSourcesFlow(): Flow<List<Source>> {
        return flow {
            emptyList<Source>()
        }
//        return sourceDao.getAllSourcesFlow().map {
//            it.map { sourceEntity ->
//                sourceEntity.asExternalModel()
//            }
//        }
    }

    override suspend fun getAllSources(): List<Source> {
        return emptyList()
//        return sourceDao.getAllSources().map {
//            it.asExternalModel()
//        }
    }

    override suspend fun getAllSourcesCount(): Int {
        return 0
//        return sourceDao.getAllSourcesCount()
    }

    override suspend fun getSource(
        id: Int,
    ): Source? {
        return null
//        return sourceDao.getSource(
//            id = id,
//        )?.asExternalModel()
    }

    override suspend fun getSources(
        ids: List<Int>,
    ): List<Source> {
        return emptyList()
//        return sourceDao.getSources(
//            ids = ids,
//        ).map {
//            it.asExternalModel()
//        }
    }

    override suspend fun insertSources(
        vararg sources: Source,
    ) {
//        sourceDao.insertSources(
//            sources = sources.map {
//                it.asEntity()
//            }.toTypedArray(),
//        )
    }

    @androidx.room.Transaction
    override suspend fun updateSourceBalanceAmount(
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

    override suspend fun updateSources(
        vararg sources: Source,
    ) {
//        sourceDao.updateSources(
//            sources = sources.map {
//                it.asEntity()
//            }.toTypedArray(),
//        )
    }

    override suspend fun deleteSource(
        id: Int,
    ) {
//        sourceDao.deleteSource(
//            id = id,
//        )
    }

    override suspend fun deleteSources(
        vararg sources: Source,
    ) {
//        sourceDao.deleteSources(
//            sources = sources.map {
//                it.asEntity()
//            }.toTypedArray(),
//        )
    }
}

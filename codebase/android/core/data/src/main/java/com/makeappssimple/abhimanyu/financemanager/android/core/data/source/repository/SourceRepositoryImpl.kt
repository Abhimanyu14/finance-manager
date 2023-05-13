package com.makeappssimple.abhimanyu.financemanager.android.core.data.source.repository

import com.makeappssimple.abhimanyu.financemanager.android.core.data.model.asEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.SourceDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.asExternalModel
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.model.updateBalanceAmount
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SourceRepositoryImpl(
    private val sourceDao: SourceDao,
) : SourceRepository {
    override fun getAllSourcesFlow(): Flow<List<Source>> {
        return sourceDao.getAllSourcesFlow().map {
            it.map { sourceEntity ->
                sourceEntity.asExternalModel()
            }
        }
    }

    override suspend fun getAllSources(): List<Source> {
        return sourceDao.getAllSources().map {
            it.asExternalModel()
        }
    }

    override suspend fun getAllSourcesCount(): Int {
        return sourceDao.getAllSourcesCount()
    }

    override suspend fun getSource(
        id: Int,
    ): Source? {
        return sourceDao.getSource(
            id = id,
        )?.asExternalModel()
    }

    override suspend fun getSources(
        ids: List<Int>,
    ): List<Source> {
        return sourceDao.getSources(
            ids = ids,
        ).map {
            it.asExternalModel()
        }
    }

    override suspend fun insertSources(
        vararg sources: Source,
    ) {
        sourceDao.insertSources(
            sources = sources.map {
                it.asEntity()
            }.toTypedArray(),
        )
    }

    @androidx.room.Transaction
    override suspend fun updateSourceBalanceAmount(
        sourcesBalanceAmountChange: List<Pair<Int, Long>>,
    ) {
        val sourceIds = sourcesBalanceAmountChange.map {
            it.first
        }
        val sources = getSources(
            ids = sourceIds,
        )
        val updatedSources = sources.mapIndexed { index, source ->
            source.updateBalanceAmount(
                updatedBalanceAmount = source.balanceAmount.value + sourcesBalanceAmountChange[index].second,
            )
        }
        updateSources(
            sources = updatedSources.toTypedArray(),
        )
    }

    override suspend fun updateSources(
        vararg sources: Source,
    ) {
        sourceDao.updateSources(
            sources = sources.map {
                it.asEntity()
            }.toTypedArray(),
        )
    }

    override suspend fun deleteSource(
        id: Int,
    ) {
        sourceDao.deleteSource(
            id = id,
        )
    }

    override suspend fun deleteSources(
        vararg sources: Source,
    ) {
        sourceDao.deleteSources(
            sources = sources.map {
                it.asEntity()
            }.toTypedArray(),
        )
    }
}

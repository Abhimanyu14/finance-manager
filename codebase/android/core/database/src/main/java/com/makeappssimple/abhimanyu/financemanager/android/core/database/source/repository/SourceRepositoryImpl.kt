package com.makeappssimple.abhimanyu.financemanager.android.core.database.source.repository

import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.datasource.local.SourceDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.updateBalanceAmount
import kotlinx.coroutines.flow.Flow

class SourceRepositoryImpl(
    private val sourceDao: SourceDao,
) : SourceRepository {
    override fun getAllSourcesFlow(): Flow<List<Source>> {
        return sourceDao.getAllSourcesFlow()
    }

    override suspend fun getAllSourcesCount(): Int {
        return sourceDao.getAllSourcesCount()
    }

    override suspend fun getSource(
        id: Int,
    ): Source? {
        return sourceDao.getSource(
            id = id,
        )
    }

    override suspend fun getSources(
        ids: List<Int>,
    ): List<Source> {
        return sourceDao.getSources(
            ids = ids,
        )
    }

    override suspend fun insertSources(
        vararg sources: Source,
    ) {
        sourceDao.insertSources(
            sources = sources,
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
            sources = sources,
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
            sources = sources,
        )
    }
}

package com.makeappssimple.abhimanyu.financemanager.android.data.source.repository

import com.makeappssimple.abhimanyu.financemanager.android.data.source.datasource.local.SourceDao
import com.makeappssimple.abhimanyu.financemanager.android.entities.source.Source
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class SourceRepositoryImpl(
    private val sourceDao: SourceDao,
) : SourceRepository {
    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    override val sources = sourceDao.getSources()
    override val sourcesTotalBalanceAmountValue: Flow<Long> = sources.map {
        it.sumOf { source ->
            source.balanceAmount.value
        }
    }

    override suspend fun getSourcesCount(): Int {
        return sourceDao.getSourcesCount()
    }

    override suspend fun getSource(
        id: Int,
    ): Source? {
        return sourceDao.getSource(
            id = id,
        )
    }

    override suspend fun insertSource(
        source: Source,
    ) {
        sourceDao.insertSource(
            source = source,
        )
    }

    override suspend fun insertSources(
        vararg sources: Source,
    ) {
        sourceDao.insertSources(
            sources = sources,
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

package com.makeappssimple.abhimanyu.financemanager.android.core.database.source.repository

import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.datasource.local.SourceDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import kotlinx.coroutines.flow.Flow

class SourceRepositoryImpl(
    private val sourceDao: SourceDao,
) : SourceRepository {
    override val sources: Flow<List<Source>> = sourceDao.getSources()

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

    override suspend fun deleteAllSources() {
        sourceDao.deleteAllSources()
    }
}

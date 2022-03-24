package com.makeappssimple.abhimanyu.financemanager.android.data.local.source

import com.makeappssimple.abhimanyu.financemanager.android.models.Source

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class SourceRepository(
    private val sourceDao: SourceDao,
) {
    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val sources = sourceDao.getSources()

    suspend fun getSource(
        id: Int,
    ): Source? {
        return sourceDao.getSource(
            id = id,
        )
    }

    suspend fun insertSource(
        source: Source,
    ) {
        sourceDao.insertSource(
            source = source,
        )
    }

    suspend fun updateSources(
        vararg sources: Source,
    ) {
        sourceDao.updateSources(
            sources = sources,
        )
    }

    suspend fun deleteSource(
        id: Int,
    ) {
        sourceDao.deleteSource(
            id = id,
        )
    }

    suspend fun deleteSources(
        vararg sources: Source,
    ) {
        sourceDao.deleteSources(
            sources = sources,
        )
    }
}
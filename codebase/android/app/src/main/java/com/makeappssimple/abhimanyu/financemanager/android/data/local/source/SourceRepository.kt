package com.makeappssimple.abhimanyu.financemanager.android.data.local.source

import com.makeappssimple.abhimanyu.financemanager.android.models.Source
import kotlinx.coroutines.flow.Flow

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class SourceRepository(
    private val sourceDao: SourceDao,
) {
    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val sources = sourceDao.getSources()

    fun getSource(
        id: Int,
    ): Flow<Source> {
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

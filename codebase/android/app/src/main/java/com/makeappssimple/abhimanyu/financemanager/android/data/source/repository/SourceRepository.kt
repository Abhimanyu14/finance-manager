package com.makeappssimple.abhimanyu.financemanager.android.data.source.repository

import com.makeappssimple.abhimanyu.financemanager.android.entities.source.Source
import kotlinx.coroutines.flow.Flow

interface SourceRepository {
    val sources: Flow<List<Source>>

    suspend fun getSourcesCount(): Int

    suspend fun getSource(
        id: Int,
    ): Source?

    suspend fun insertSource(
        source: Source,
    )

    suspend fun insertSources(
        vararg sources: Source,
    )

    suspend fun updateSources(
        vararg sources: Source,
    )

    suspend fun deleteSource(
        id: Int,
    )

    suspend fun deleteSources(
        vararg sources: Source,
    )
}

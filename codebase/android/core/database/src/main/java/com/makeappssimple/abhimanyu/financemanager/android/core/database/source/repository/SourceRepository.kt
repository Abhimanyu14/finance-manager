package com.makeappssimple.abhimanyu.financemanager.android.core.database.source.repository

import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import kotlinx.coroutines.flow.Flow

interface SourceRepository {
    fun getAllSourcesFlow(): Flow<List<Source>>

    suspend fun getAllSources(): List<Source>

    suspend fun getAllSourcesCount(): Int

    suspend fun getSource(
        id: Int,
    ): Source?

    suspend fun getSources(
        ids: List<Int>,
    ): List<Source>?

    suspend fun insertSources(
        vararg sources: Source,
    )

    suspend fun updateSourceBalanceAmount(
        sourcesBalanceAmountChange: List<Pair<Int, Long>>,
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

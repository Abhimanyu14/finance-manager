package com.makeappssimple.abhimanyu.financemanager.android.core.database.source.datasource.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import kotlinx.coroutines.flow.Flow

@Dao
interface SourceDao {

    @Query(value = "SELECT * from source_table ORDER BY id ASC")
    fun getSources(): Flow<List<Source>>

    @Query(value = "SELECT COUNT(*) FROM source_table")
    suspend fun getSourcesCount(): Int

    @Query(value = "SELECT * from source_table WHERE id = :id")
    suspend fun getSource(
        id: Int,
    ): Source?

    @Query(value = "SELECT * from source_table WHERE id IN (:ids)")
    suspend fun getSources(
        ids: List<Int>,
    ): List<Source>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSources(
        vararg sources: Source,
    )

    @Update
    suspend fun updateSources(
        vararg sources: Source,
    )

    @Query(value = "DELETE FROM source_table WHERE id = :id")
    suspend fun deleteSource(
        id: Int,
    )

    @Delete
    suspend fun deleteSources(
        vararg sources: Source,
    )

    @Query(value = "DELETE FROM source_table")
    suspend fun deleteAllSources()
}

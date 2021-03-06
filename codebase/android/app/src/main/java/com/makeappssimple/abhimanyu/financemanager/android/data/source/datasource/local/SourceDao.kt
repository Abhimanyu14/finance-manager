package com.makeappssimple.abhimanyu.financemanager.android.data.source.datasource.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.makeappssimple.abhimanyu.financemanager.android.entities.source.Source
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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSource(
        source: Source,
    )

    @Insert(onConflict = OnConflictStrategy.REPLACE)
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

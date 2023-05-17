package com.makeappssimple.abhimanyu.financemanager.android.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.SourceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SourceDao {
    @Query(value = "SELECT * from source_table ORDER BY id ASC")
    fun getAllSourcesFlow(): Flow<List<SourceEntity>>

    @Query(value = "SELECT * from source_table ORDER BY id ASC")
    suspend fun getAllSources(): List<SourceEntity>

    @Query(value = "SELECT COUNT(*) FROM source_table")
    suspend fun getAllSourcesCount(): Int

    @Query(value = "SELECT * from source_table WHERE id = :id")
    suspend fun getSource(
        id: Int,
    ): SourceEntity?

    @Query(value = "SELECT * from source_table WHERE id IN (:ids)")
    suspend fun getSources(
        ids: List<Int>,
    ): List<SourceEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSources(
        vararg sources: SourceEntity,
    )

    @Update
    suspend fun updateSources(
        vararg sources: SourceEntity,
    )

    @Query(value = "DELETE FROM source_table WHERE id = :id")
    suspend fun deleteSource(
        id: Int,
    )

    @Delete
    suspend fun deleteSources(
        vararg sources: SourceEntity,
    )

    @Query(value = "DELETE FROM source_table")
    suspend fun deleteAllSources()
}

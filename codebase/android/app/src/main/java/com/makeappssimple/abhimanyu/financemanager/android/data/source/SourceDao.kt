package com.makeappssimple.abhimanyu.financemanager.android.data.source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.makeappssimple.abhimanyu.financemanager.android.models.Source
import kotlinx.coroutines.flow.Flow

@Dao
interface SourceDao {

    @Query(
        value = "SELECT * from source_table ORDER BY id ASC",
    )
    fun getSources(): Flow<List<Source>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSource(
        source: Source,
    )

    @Query(
        value = "DELETE FROM source_table WHERE id = :id",
    )
    suspend fun deleteSource(
        id: Int,
    )

    @Delete
    suspend fun deleteSources(
        vararg sources: Source,
    )

    @Query(
        value = "DELETE FROM source_table",
    )
    suspend fun deleteAll()
}

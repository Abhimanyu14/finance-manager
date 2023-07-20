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
interface AccountDao {
    @Query(value = "SELECT * from source_table ORDER BY id ASC")
    fun getAllAccountsFlow(): Flow<List<SourceEntity>>

    @Query(value = "SELECT * from source_table ORDER BY id ASC")
    suspend fun getAllAccounts(): List<SourceEntity>

    @Query(value = "SELECT COUNT(*) FROM source_table")
    suspend fun getAllAccountsCount(): Int

    @Query(value = "SELECT * from source_table WHERE id = :id")
    suspend fun getAccount(
        id: Int,
    ): SourceEntity?

    @Query(value = "SELECT * from source_table WHERE id IN (:ids)")
    suspend fun getAccounts(
        ids: List<Int>,
    ): List<SourceEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAccounts(
        vararg sources: SourceEntity,
    )

    @Update
    suspend fun updateAccounts(
        vararg sources: SourceEntity,
    )

    @Query(value = "DELETE FROM source_table WHERE id = :id")
    suspend fun deleteAccount(
        id: Int,
    )

    @Delete
    suspend fun deleteAccounts(
        vararg sources: SourceEntity,
    )

    @Query(value = "DELETE FROM source_table")
    suspend fun deleteAllAccounts()
}

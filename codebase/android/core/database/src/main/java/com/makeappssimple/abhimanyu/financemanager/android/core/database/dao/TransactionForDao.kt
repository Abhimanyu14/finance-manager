package com.makeappssimple.abhimanyu.financemanager.android.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionForEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionForDao {
    @Query(value = "SELECT * from transaction_for_table ORDER BY id ASC")
    fun getAllTransactionForValuesFlow(): Flow<List<TransactionForEntity>>

    @Query(value = "SELECT * from transaction_for_table ORDER BY id ASC")
    suspend fun getAllTransactionForValues(): List<TransactionForEntity>

    @Query(value = "SELECT COUNT(*) FROM transaction_for_table")
    suspend fun getTransactionForValuesCount(): Int

    @Query(value = "SELECT * from transaction_for_table WHERE id = :id")
    suspend fun getTransactionFor(
        id: Int,
    ): TransactionForEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTransactionForValues(
        vararg transactionForValues: TransactionForEntity,
    )

    @Update
    suspend fun updateTransactionForValues(
        vararg transactionForValues: TransactionForEntity,
    )

    @Query(value = "DELETE FROM transaction_for_table WHERE id = :id")
    suspend fun deleteTransactionFor(
        id: Int,
    )
}

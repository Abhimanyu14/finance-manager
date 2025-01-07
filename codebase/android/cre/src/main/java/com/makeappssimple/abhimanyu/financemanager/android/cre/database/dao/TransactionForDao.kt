package com.makeappssimple.abhimanyu.financemanager.android.cre.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.makeappssimple.abhimanyu.financemanager.android.cre.database.model.TransactionForEntity
import kotlinx.coroutines.flow.Flow

@Dao
public interface TransactionForDao {
    @Query(value = "SELECT * from transaction_for_table ORDER BY id ASC")
    public fun getAllTransactionForValuesFlow(): Flow<List<TransactionForEntity>>

    @Query(value = "SELECT * from transaction_for_table ORDER BY id ASC")
    public suspend fun getAllTransactionForValues(): List<TransactionForEntity>

    @Query(value = "SELECT COUNT(*) FROM transaction_for_table")
    public suspend fun getTransactionForValuesCount(): Int

    @Query(value = "SELECT * from transaction_for_table WHERE id = :id")
    public suspend fun getTransactionFor(
        id: Int,
    ): TransactionForEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public suspend fun insertTransactionForValues(
        vararg transactionForValues: TransactionForEntity,
    ): List<Long>

    @Update
    public suspend fun updateTransactionForValues(
        vararg transactionForValues: TransactionForEntity,
    ): Int

    @Query(value = "DELETE FROM transaction_for_table WHERE id = :id")
    public suspend fun deleteTransactionFor(
        id: Int,
    ): Int

    @Query(value = "DELETE FROM transaction_for_table")
    public suspend fun deleteAllTransactionForValues(): Int
}

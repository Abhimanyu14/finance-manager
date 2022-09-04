package com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.datasource.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionData
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Query(value = "SELECT * from transaction_table ORDER BY transaction_timestamp DESC")
    fun getAllTransactions(): Flow<List<Transaction>>

    @androidx.room.Transaction
    @Query("SELECT * FROM transaction_table ORDER BY transaction_timestamp DESC")
    fun getAllTransactionData(): Flow<List<TransactionData>>

    @Query(value = "SELECT * from transaction_table WHERE transaction_timestamp BETWEEN :startingTimestamp AND :endingTimestamp ORDER BY transaction_timestamp DESC")
    fun getTransactionsBetweenTimestamps(
        startingTimestamp: Long,
        endingTimestamp: Long,
    ): Flow<List<Transaction>>

    @androidx.room.Transaction
    @Query("SELECT * FROM transaction_table ORDER BY transaction_timestamp DESC LIMIT :numberOfTransactions")
    fun getRecentTransactionData(
        numberOfTransactions: Int,
    ): Flow<List<TransactionData>>

    @Query(value = "SELECT COUNT(*) FROM transaction_table")
    suspend fun getTransactionsCount(): Int

    @Query(value = "SELECT title from transaction_table WHERE category_id = :categoryId GROUP BY title ORDER BY COUNT(title) DESC LIMIT :numberOfSuggestions")
    suspend fun getTitleSuggestions(
        categoryId: Int,
        numberOfSuggestions: Int,
    ): List<String>

    @Query(value = "SELECT EXISTS(SELECT * from transaction_table WHERE category_id = :categoryId)")
    suspend fun checkIfCategoryIsUsedInTransactions(
        categoryId: Int,
    ): Boolean

    @Query(value = "SELECT EXISTS(SELECT * from transaction_table WHERE source_from_id = :sourceId OR source_to_id = :sourceId)")
    suspend fun checkIfSourceIsUsedInTransactions(
        sourceId: Int,
    ): Boolean

    @Query(value = "SELECT * from transaction_table WHERE id = :id")
    suspend fun getTransaction(
        id: Int,
    ): Transaction?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTransaction(
        transaction: Transaction,
    )

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTransactions(
        vararg transactions: Transaction,
    )

    @Update
    suspend fun updateTransaction(
        transaction: Transaction,
    )

    @Update
    suspend fun updateTransactions(
        vararg transactions: Transaction,
    )

    @Query(value = "DELETE FROM transaction_table WHERE id = :id")
    suspend fun deleteTransaction(
        id: Int,
    )

    @Delete
    suspend fun deleteTransactions(
        vararg transactions: Transaction,
    )

    @Query(value = "DELETE FROM transaction_table")
    suspend fun deleteAllTransactions()
}

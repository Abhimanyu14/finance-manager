package com.makeappssimple.abhimanyu.financemanager.android.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionDataEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Query(
        value = "SELECT * from transaction_table " +
                "ORDER BY transaction_timestamp DESC"
    )
    fun getAllTransactionsFlow(): Flow<List<TransactionEntity>>

    @Query(
        value = "SELECT * from transaction_table " +
                "ORDER BY transaction_timestamp DESC"
    )
    suspend fun getAllTransactions(): List<TransactionEntity>

    @androidx.room.Transaction
    @Query(
        value = "SELECT * FROM transaction_table " +
                "ORDER BY transaction_timestamp DESC"
    )
    fun getAllTransactionDataFlow(): Flow<List<TransactionDataEntity>>

    @androidx.room.Transaction
    @Query(
        value = "SELECT * FROM transaction_table " +
                "ORDER BY transaction_timestamp DESC"
    )
    suspend fun getAllTransactionData(): List<TransactionDataEntity>

    /**
     * TODO(Abhi): To search amount properly, JSON1 extension is required which is not available in Android.
     * For more info - https://stackoverflow.com/a/65104396/9636037
     *
     * The current code is a hacky solution, which does a simple text search of the JSON string.
     */
    @androidx.room.Transaction
    @Query(
        value = "SELECT * FROM transaction_table " +
                "WHERE instr(lower(title), lower(:searchText)) > 0 OR instr(lower(amount), lower(:searchText)) > 0 " +
                "ORDER BY transaction_timestamp DESC"
    )
    suspend fun getSearchedTransactionData(
        searchText: String,
    ): List<TransactionDataEntity>

    @Query(
        value = "SELECT * from transaction_table " +
                "WHERE transaction_timestamp BETWEEN :startingTimestamp AND :endingTimestamp " +
                "ORDER BY transaction_timestamp DESC"
    )
    fun getTransactionsBetweenTimestampsFlow(
        startingTimestamp: Long,
        endingTimestamp: Long,
    ): Flow<List<TransactionEntity>>

    @Query(
        value = "SELECT * from transaction_table " +
                "WHERE transaction_timestamp BETWEEN :startingTimestamp AND :endingTimestamp " +
                "ORDER BY transaction_timestamp DESC"
    )
    suspend fun getTransactionsBetweenTimestamps(
        startingTimestamp: Long,
        endingTimestamp: Long,
    ): List<TransactionEntity>

    @androidx.room.Transaction
    @Query(
        value = "SELECT * FROM transaction_table " +
                "ORDER BY transaction_timestamp DESC " +
                "LIMIT :numberOfTransactions"
    )
    fun getRecentTransactionDataFlow(
        numberOfTransactions: Int,
    ): Flow<List<TransactionDataEntity>>

    @Query(value = "SELECT COUNT(*) FROM transaction_table")
    suspend fun getTransactionsCount(): Int

    @Query(
        value = "SELECT title from transaction_table " +
                "WHERE category_id = :categoryId " +
                "AND title LIKE '%' || :enteredTitle || '%' " +
                "GROUP BY title " +
                "ORDER BY COUNT(title) DESC " +
                "LIMIT :numberOfSuggestions"
    )
    suspend fun getTitleSuggestions(
        categoryId: Int,
        numberOfSuggestions: Int,
        enteredTitle: String,
    ): List<String>

    @Query(
        value = "SELECT EXISTS(SELECT * FROM transaction_table " +
                "WHERE category_id = :categoryId)"
    )
    suspend fun checkIfCategoryIsUsedInTransactions(
        categoryId: Int,
    ): Boolean

    @Query(
        value = "SELECT EXISTS(SELECT * FROM transaction_table " +
                "WHERE account_from_id = :accountId OR account_to_id = :accountId)"
    )
    suspend fun checkIfAccountIsUsedInTransactions(
        accountId: Int,
    ): Boolean

    @Query(
        value = "SELECT EXISTS(SELECT * FROM transaction_table " +
                "WHERE transaction_for_id = :transactionForId)"
    )
    suspend fun checkIfTransactionForIsUsedInTransactions(
        transactionForId: Int,
    ): Boolean

    @Query(
        value = "SELECT * FROM transaction_table " +
                "WHERE id = :id"
    )
    suspend fun getTransaction(
        id: Int,
    ): TransactionEntity?

    @androidx.room.Transaction
    @Query(
        value = "SELECT * FROM transaction_table " +
                "WHERE id = :id"
    )
    suspend fun getTransactionData(
        id: Int,
    ): TransactionDataEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTransactions(
        vararg transactions: TransactionEntity,
    ): List<Long>

    @Query(value = "DELETE FROM transaction_table")
    suspend fun deleteAllTransactions(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTransaction(
        transaction: TransactionEntity,
    ): Long

    @Update
    suspend fun updateTransaction(
        transaction: TransactionEntity,
    ): Int

    @Update
    suspend fun updateTransactions(
        vararg transactions: TransactionEntity,
    ): Int

    @Query(
        value = "DELETE FROM transaction_table " +
                "WHERE id = :id"
    )
    suspend fun deleteTransaction(
        id: Int,
    ): Int
}

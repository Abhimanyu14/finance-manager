package com.makeappssimple.abhimanyu.financemanager.android.cre.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.makeappssimple.abhimanyu.financemanager.android.cre.database.model.TransactionDataEntity
import com.makeappssimple.abhimanyu.financemanager.android.cre.database.model.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
public interface TransactionDao {
    @Query(
        value = "SELECT * from transaction_table " +
                "ORDER BY transaction_timestamp DESC"
    )
    public fun getAllTransactionsFlow(): Flow<List<TransactionEntity>>

    @Query(
        value = "SELECT * from transaction_table " +
                "ORDER BY transaction_timestamp DESC"
    )
    public suspend fun getAllTransactions(): List<TransactionEntity>

    @androidx.room.Transaction
    @Query(
        value = "SELECT * FROM transaction_table " +
                "ORDER BY transaction_timestamp DESC"
    )
    public fun getAllTransactionDataFlow(): Flow<List<TransactionDataEntity>>

    @androidx.room.Transaction
    @Query(
        value = "SELECT * FROM transaction_table " +
                "ORDER BY transaction_timestamp DESC"
    )
    public suspend fun getAllTransactionData(): List<TransactionDataEntity>

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
    public suspend fun getSearchedTransactionData(
        searchText: String,
    ): List<TransactionDataEntity>

    @Query(
        value = "SELECT * from transaction_table " +
                "WHERE transaction_timestamp BETWEEN :startingTimestamp AND :endingTimestamp " +
                "ORDER BY transaction_timestamp DESC"
    )
    public fun getTransactionsBetweenTimestampsFlow(
        startingTimestamp: Long,
        endingTimestamp: Long,
    ): Flow<List<TransactionEntity>>

    @Query(
        value = "SELECT * from transaction_table " +
                "WHERE transaction_timestamp BETWEEN :startingTimestamp AND :endingTimestamp " +
                "ORDER BY transaction_timestamp DESC"
    )
    public suspend fun getTransactionsBetweenTimestamps(
        startingTimestamp: Long,
        endingTimestamp: Long,
    ): List<TransactionEntity>

    @androidx.room.Transaction
    @Query(
        value = "SELECT * FROM transaction_table " +
                "ORDER BY transaction_timestamp DESC " +
                "LIMIT :numberOfTransactions"
    )
    public fun getRecentTransactionDataFlow(
        numberOfTransactions: Int,
    ): Flow<List<TransactionDataEntity>>

    @Query(value = "SELECT COUNT(*) FROM transaction_table")
    public suspend fun getTransactionsCount(): Int

    @Query(
        value = "SELECT title from transaction_table " +
                "WHERE category_id = :categoryId " +
                "AND title LIKE '%' || :enteredTitle || '%' " +
                "GROUP BY title " +
                "ORDER BY COUNT(title) DESC " +
                "LIMIT :numberOfSuggestions"
    )
    public suspend fun getTitleSuggestions(
        categoryId: Int,
        numberOfSuggestions: Int,
        enteredTitle: String,
    ): List<String>

    @Query(
        value = "SELECT EXISTS(SELECT * FROM transaction_table " +
                "WHERE category_id = :categoryId)"
    )
    public suspend fun checkIfCategoryIsUsedInTransactions(
        categoryId: Int,
    ): Boolean

    @Query(
        value = "SELECT EXISTS(SELECT * FROM transaction_table " +
                "WHERE account_from_id = :accountId OR account_to_id = :accountId)"
    )
    public suspend fun checkIfAccountIsUsedInTransactions(
        accountId: Int,
    ): Boolean

    @Query(
        value = "SELECT EXISTS(SELECT * FROM transaction_table " +
                "WHERE transaction_for_id = :transactionForId)"
    )
    public suspend fun checkIfTransactionForIsUsedInTransactions(
        transactionForId: Int,
    ): Boolean

    @Query(
        value = "SELECT * FROM transaction_table " +
                "WHERE id = :id"
    )
    public suspend fun getTransaction(
        id: Int,
    ): TransactionEntity?

    @androidx.room.Transaction
    @Query(
        value = "SELECT * FROM transaction_table " +
                "WHERE id = :id"
    )
    public suspend fun getTransactionData(
        id: Int,
    ): TransactionDataEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public suspend fun insertTransactions(
        vararg transactions: TransactionEntity,
    ): List<Long>

    @Query(value = "DELETE FROM transaction_table")
    public suspend fun deleteAllTransactions(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public suspend fun insertTransaction(
        transaction: TransactionEntity,
    ): Long

    @Update
    public suspend fun updateTransaction(
        transaction: TransactionEntity,
    ): Int

    @Update
    public suspend fun updateTransactions(
        vararg transactions: TransactionEntity,
    ): Int

    @Query(
        value = "DELETE FROM transaction_table " +
                "WHERE id = :id"
    )
    public suspend fun deleteTransaction(
        id: Int,
    ): Int
}
